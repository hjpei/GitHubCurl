import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.GitRepoIssue;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitClient {
    String token;
    String user;
    String host;

    public GitClient(String token, String user){
        this.host = "https://api.github.com/";
        this.token = token;
        this.user = user;
    }

    public List<GitRepoIssue> getRepoIssues(String repo, String org, Map<String, String> filter){
        List<GitRepoIssue> result = new ArrayList<>();
        String url = host + "repos/";
        if(org != null && !org.equals("")){
            url += org + "/";
        }
        url += repo + "/issues";
        if(filter != null){
            StringBuilder queryString = new StringBuilder();
            for(String key : filter.keySet()){
                if(queryString.length() > 0){
                    queryString.append("&");
                }
                queryString.append(key + "=" + filter.get(key));
            }
            if(queryString.length() > 0){
                url += "?" + queryString;
            }
        }
//        System.out.println(url);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);
        headers.put("Accept", "application/vnd.github+json");
        HttpResponse response = new HttpClient().get(url, headers);
        int status = response.getStatusLine().getStatusCode();
        if(status == 301){
            Exception e = new ClientProtocolException("Status Code: " + status + ", Resource Moved Permanently");
            e.printStackTrace();
            return result;
        }else if(status == 404){
            Exception e = new ClientProtocolException("Status Code: " + status + ", Resource not found");
            e.printStackTrace();
            return result;
        }else if(status == 422){
            Exception e = new ClientProtocolException("Status Code: " + status + ", Validation failed");
            e.printStackTrace();
            return result;
        }else if(status != 200){
            Exception e = new ClientProtocolException("Status Code: " + status + ", Unknown error");
            e.printStackTrace();
            return result;
        }

        try{
            HttpEntity entity = response.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null){
                if(sb.length() > 0){
                    sb.append("\n");
                }
                sb.append(line);
            }
            reader.close();
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(sb.toString(), new TypeReference<List<GitRepoIssue>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }
}
