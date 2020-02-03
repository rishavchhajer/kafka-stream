package models;


import lombok.Getter;
import lombok.Setter;

public class RequestData {
    @Getter @Setter
    private String mobileNumber = null;

    @Getter @Setter
    private Long nextCommunicationTimestamp = 0L;

    @Getter @Setter
    private String eventName = "";

    @Getter @Setter
    private String siteId = "";

    @Getter @Setter
    private int nextCommunicationNumber = 1;

    @Getter @Setter
    private int totalCommunicationNumber = 0;

    @Getter @Setter
    private UserData userData = null;

    private String payLoad = null;

    @Getter @Setter
    private String messageId = "";

    public String getPayload(){
        return this.payLoad;
    }

    public void setPayload(String payload){
        this.payLoad = payload;
    }
}
