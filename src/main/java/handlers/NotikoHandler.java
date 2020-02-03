package handlers;

import entity.CommunicationLogs;
import models.NotikoEventResponse;
import models.Response;
import org.modelmapper.ModelMapper;
import util.ConfigBundle;
import java.util.HashMap;
import java.util.Map;

public class NotikoHandler extends Base {
    private String DOMAIN = ConfigBundle.getValue("NOTIFICATION_DOMAIN");

    /**
     * To get Communication Details by siteId and eventName
     * @param siteId
     * @param eventName
     */
    public NotikoEventResponse getEventCommunicationData(String siteId, String eventName){
        String uri = DOMAIN.concat("/api/v1/events/get-cached-events");
        Map<String, String> requestBody = new HashMap();
        requestBody.put("siteId",siteId);
        requestBody.put("eventName", eventName);
        Response response = this.post(uri, null, requestBody );
        ModelMapper mapper = new ModelMapper();
        NotikoEventResponse notikoComData = mapper.map(response.getResponse(), NotikoEventResponse.class);
        return notikoComData;
    }
}
