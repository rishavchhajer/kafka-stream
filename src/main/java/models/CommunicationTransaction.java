package models;

import lombok.Getter;
import lombok.Setter;

public class CommunicationTransaction {
    @Setter @Getter
    private Long logId;

    @Setter @Getter
    private String siteId;

    @Setter @Getter
    private String mobileNumber;

    @Setter @Getter
    private String notikoEvent;

    @Setter @Getter
    private String comboxEvent;

    @Setter @Getter
    private String requestId;

    @Setter @Getter
    private int communicationNumber;
}
