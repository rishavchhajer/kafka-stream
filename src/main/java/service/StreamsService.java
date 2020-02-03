package service;


import consumers.KtableConsumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class StreamsService {
    public void run(){
        KtableConsumer consumer = new KtableConsumer();
        consumer.run();
    }
}
