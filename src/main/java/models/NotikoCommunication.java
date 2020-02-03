package models;

import lombok.Getter;
import lombok.Setter;

public class NotikoCommunication {

    @Setter @Getter
    private String comboxEvent;

    @Setter @Getter
    private String eventInterval;

    @Setter @Getter
    private String defaultQueryAppend;

}
