import models.CommunicationTransaction;
import repository.Repository;
import service.StreamsService;

/**
 * Project: kafkastreams
 * Contributed By: Tushar Mudgal
 * On: 27/01/20 | 3:30 PM
 */
public class StreamsApplication {
    public static void main(String[] args) {
        StreamsService streamService = new StreamsService();
        streamService.run();
    }
}
