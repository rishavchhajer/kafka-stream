package models;

import java.util.HashMap;

public class KafkaPayload {

    private RequestData Payload ;

    private String MessageID = null;

    public String getMessageId(){
        return this.MessageID;
    }

    public void setMessageId( String messageId ){
        this.MessageID = messageId;
    }

    public RequestData getPayload(){
        return this.Payload;
    }

    public void setPayload( RequestData payload ){
        this.Payload = payload;
    }

}
