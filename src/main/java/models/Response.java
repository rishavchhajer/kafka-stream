package models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Response {
    @Setter @Getter
    private Object response;

    @Setter @Getter
    private String statusCode;

    @Setter @Getter
    private String statusMessage;

    @Setter @Getter
    private Boolean status;
}
