package handlers;

import models.ComBoxRequest;
import models.Notiko;
import models.Response;
import org.apache.log4j.Logger;
import util.ConfigBundle;
import util.HttpRequest;

import java.util.HashMap;

public class ComBoxHandler extends Base {

    private String DOMAIN = ConfigBundle.getValue("COMBOX_DOMAIN");
    final private HttpRequest request = new HttpRequest();

    /**
     * request to CommBox
     * @param params
     */
    public Response sendCommunication(ComBoxRequest request){
        String uri = this.DOMAIN + "/v1/communication/notify";
        //To Do , add headers and prepare data needed to commBox
        Response rs = this.post(uri, null, request);
        return rs;
    }
}
