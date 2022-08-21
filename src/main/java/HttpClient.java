import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Map;

public class HttpClient {
    HttpResponse response;

    public HttpResponse get(String url, Map<String, String> headers) {
        try{
            Request request = Request.Get(url);
            if(headers != null){
                for(String key: headers.keySet()){
                    request.addHeader(key, headers.get(key));
                }
            }
            response = request.execute().returnResponse();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return response;
    }

}



