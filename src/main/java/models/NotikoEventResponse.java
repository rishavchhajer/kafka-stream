package models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class NotikoEventResponse {
    @Setter @Getter
    private int nextCommunicationNumber;

    @Setter @Getter
    private int totalCommunicationNumber;

    @Setter @Getter
    private List<NotikoCommunication> communications;

}
