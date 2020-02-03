package consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import handlers.ComBoxHandler;
import handlers.NotikoHandler;
import lombok.extern.log4j.Log4j;
import models.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.modelmapper.ModelMapper;
import repository.Repository;
import util.ConfigBundle;
import util.GSON;

import java.time.Duration;
import java.util.Properties;
import config.StreamsConfig;
import util.CustomDeserializer;
import util.CustomSerializer;

import static org.apache.kafka.streams.kstream.Suppressed.BufferConfig.unbounded;


/**
 * Project: kstreamsdemo
 * Contributed By: Tushar Mudgal
 * On: 29/01/20 | 4:59 PM
 */
@Log4j
public class KtableConsumer {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Gson gson = GSON.getGson();

    public void run() {
        Properties streamsProps = StreamsConfig.getStreamProps();
        // custom json serdes
        final Serde<String> stringSerde = Serdes.String();
        final CustomSerializer<JsonNode> jsonSerializer = new CustomSerializer<>();
        final CustomDeserializer<JsonNode> jsonDeserializer = new CustomDeserializer<>(JsonNode.class);
        final Serde<JsonNode> jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);

        final CustomSerializer<Windowed<String>> windowedCustomSerializer = new CustomSerializer<>();
        final CustomDeserializer<Windowed<String>> windowedCustomDeserializer =
                new CustomDeserializer<>(Windowed<String>.getClass());
//        final Serde<Windowed<String>> windowedSerde = ;

        final CustomSerializer<Notiko> notikoSerializer = new CustomSerializer<>();
        final CustomDeserializer<Notiko> notikoDeserializer = new CustomDeserializer<>(Notiko.class);
        final Serde<Notiko> notikoSerde = Serdes.serdeFrom(notikoSerializer, notikoDeserializer);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, JsonNode> messagePayload = builder
                .stream(ConfigBundle.getValue("KSTREAM_TOPIC_INSERT_TYPE"),
                        Consumed.with(stringSerde, jsonSerde));

        KTable<String, Notiko> ktable = builder.table(ConfigBundle.getValue("KTABLE_TOPIC_UPDATE_TYPE"),
                Materialized.with(stringSerde, notikoSerde));


//        ktable

        KStream<Windowed<String>, Notiko> stream_after = ktable.toStream()
                .selectKey((key, value) -> key)
                .groupByKey(Grouped.with(stringSerde, notikoSerde))
                .windowedBy(TimeWindows.of(Duration.ofSeconds(60)))
                .reduce((value1, value2) -> value2, Materialized.with(stringSerde, notikoSerde))
                .suppress(Suppressed.untilWindowCloses(unbounded()))
                .toStream()
                .peek((key, value) -> log.info("key=" + key + ", value=" + value))
                .to(ConfigBundle.getValue("KTABLE_TOPIC_AFTER_UPDATE"), Produced.with(stringSerde, notikoSerde));

        ktable.mapValues((ValueMapper<Notiko, Object>) value -> {
            System.out.printf(value.toString());
            NotikoHandler notikoHandler = new NotikoHandler();
            NotikoEventResponse rs = notikoHandler.getEventCommunicationData(value.getSiteId(), value.geteventName());
            int nextCommunicationNumber = value.getNextCommunicationNumber();
            NotikoCommunication communication = rs.getCommunications().get(nextCommunicationNumber-1);

            // send communication through combox
            Gson g = new Gson();
            ComboxPayload comboxPayload = g.fromJson(value.getPayLoad(), ComboxPayload.class);
            comboxPayload.setMobileNo(value.getMobileNumber());
            comboxPayload.setUrl(comboxPayload.getUrl()+communication.getDefaultQueryAppend());
            ComBoxRequest combRequest = new ComBoxRequest();
            combRequest.setEvent(communication.getComboxEvent());
            combRequest.setPriority("High");
            combRequest.setPayload(comboxPayload);
            ComBoxHandler comBoxHandler = new ComBoxHandler();
            comBoxHandler.sendCommunication(combRequest);
            // till here

            // insert data to mysql
            Repository repository = new Repository();
            CommunicationTransaction transaction = new CommunicationTransaction();
            transaction.setMobileNumber(value.getMobileNumber());
            transaction.setComboxEvent(communication.getComboxEvent());
            transaction.setNotikoEvent(value.geteventName());
            transaction.setCommunicationNumber(nextCommunicationNumber);
            transaction.setRequestId(value.getMessageID());
            transaction.setSiteId(value.getSiteId());
            repository.createCommunicationLogs(transaction);
            //till here                                                                                                                    cv



            return value;
        });

        // ## create new payload
//        KStream<String, Notiko> fMap = messagePayload
//                .mapValues(value -> {
//                    Notiko p = null;
//                    try {
//                         p = Notiko.createNotikoJsonNode(value);
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(gson.toJson(p));
//                    return p;
//                });
//
//        KTable<String, Notiko> table = fMap
//                .selectKey((key, value) -> value.getMobileNumber() + "+" + value.getSiteId())
//                // very important to mention custom serde in each and every step
//                .groupByKey(Grouped.with(stringSerde, notikoSerde))
//                // here also very important to mention custom serde in each and every step
//                // materialized with method parses using custom serde
//                .reduce((value1, value2) -> value2, Materialized.with(stringSerde, notikoSerde));
//
//        table
//                // convert table to stream
//                .toStream()
//                // and finally sends the records to topic with *correct serdes*
//                .to(ConfigBundle.getValue("KTABLE_TOPIC_UPDATE_TYPE"),
//                        Produced.with(stringSerde, notikoSerde));

        // finish topology
        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsProps);

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

