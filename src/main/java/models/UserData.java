package models;

import lombok.Getter;
import lombok.Setter;

public class UserData {
    @Getter  @Setter
    private String mobileNo = null;

    @Getter @Setter
    private String customerName = null;

    @Getter @Setter
    private String url = null;

    @Getter @Setter
    private int productId;

    @Getter @Setter
    private String dateTime = null;

    @Getter @Setter
    private String dataSource = null;

}
