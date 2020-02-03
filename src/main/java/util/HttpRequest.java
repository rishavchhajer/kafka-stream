package util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {

    private final OkHttpClient httpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json");

    private models.Response execute (Request request){
        try{
            Response response = httpClient.newCall(request).execute();
            if(!response.isSuccessful()) throw new IOException("Unexpected Code"+ response);
            ObjectMapper ob = new ObjectMapper();
            models.Response rs = ob.readValue(response.body().string(), models.Response.class);
            return rs;
        }
        catch (IOException e){
            return null;
        }
    }

    /**
     * Get Request
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public models.Response get(String url, Map<String,String> headers){
        Request request = new Request.Builder().url(url).build();
        return execute(request);
    }

    /**
     * Post Request
     * @param url
     * @param headers
     * @param bodyMap
     * @return
     * @throws IOException
     */
    public models.Response post(String url, Map<String,String> headers, Object bodyMap){
        Gson gson = new Gson();
        String json = gson.toJson(bodyMap);
        RequestBody requestBody = RequestBody.create(json.getBytes(StandardCharsets.UTF_8), JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();
        return execute(request);
    }
}
