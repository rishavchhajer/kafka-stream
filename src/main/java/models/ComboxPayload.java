package models;

import lombok.Getter;
import lombok.Setter;

public class ComboxPayload {

    @Getter @Setter
    private String mobileNo;

    @Getter @Setter
    private  String customerName;

    @Getter @Setter
    private  String bankName;

    @Getter @Setter
    private String partner;

    @Getter @Setter
    private String referenceNo;

    @Getter @Setter
    private  String cardName;

    @Getter @Setter
    private int productId;

    @Getter @Setter
    private String url;

    @Getter @Setter
    private String emailId;

    @Setter @Getter
    private String dateTime;
}
