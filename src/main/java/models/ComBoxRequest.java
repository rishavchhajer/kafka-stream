package models;

import lombok.Getter;
import lombok.Setter;

public class ComBoxRequest {
    @Getter @Setter
    private String event;

    @Getter @Setter
    private String priority;

    @Setter @Getter
    private ComboxPayload payload;
}
