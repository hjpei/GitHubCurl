import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.GitRepo;
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

    public GitRepo getRepo(String repo, String org){
        GitRepo result;
        String url = host + "repos/";
        if(org != null && !org.equals("")){
            url += org + "/";
        }
        url += repo;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);
        headers.put("Accept", "application/vnd.github+json");
        HttpResponse response = new HttpClient().get(url, headers);
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
            result = mapper.readValue(sb.toString(), GitRepo.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<GitRepoIssue> getRepoIssues(String repo, String org, Map<String, String> filter){
        List<GitRepoIssue> result = new ArrayList<>();
        String url = host + "repos/";
        if(org != null && !org.equals("")){
            url += org + "/";
        }
        url += repo + "/issues";
        String queryString = formQueryString(filter);
        if(queryString.length() > 0){
            url += "?" + queryString;
        }
//        System.out.println(url);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);
        headers.put("Accept", "application/vnd.github+json");
        HttpResponse response = new HttpClient().get(url, headers);
        if(logException(response.getStatusLine().getStatusCode())){
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

    public List<GitRepo> listAllRepos(String org, Map<String, String> filter){
        List<GitRepo> result = new ArrayList<>();
        String url = host + "orgs/" + org + "/repos";
        String queryString = formQueryString(filter);
        if(queryString.length() > 0){
            url += "?" + queryString;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);
        headers.put("Accept", "application/vnd.github+json");
        HttpResponse response = new HttpClient().get(url, headers);
        if(logException(response.getStatusLine().getStatusCode())){
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
            result = mapper.readValue(sb.toString(), new TypeReference<List<GitRepo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }

    private String formQueryString(Map<String, String> filter){
        StringBuilder queryString = new StringBuilder();
        if(filter != null){
            for(String key : filter.keySet()){
                if(queryString.length() > 0){
                    queryString.append("&");
                }
                queryString.append(key + "=" + filter.get(key));
            }
        }
        return queryString.toString();
    }

    private boolean logException(int status){
        switch(status){
            case 200:
                return false;
            case 301:
                Exception e = new ClientProtocolException("Status Code: " + status + ", Resource Moved Permanently");
                e.printStackTrace();
                break;
            case 404:
                e = new ClientProtocolException("Status Code: " + status + ", Resource not found");
                e.printStackTrace();
                break;
            case 422:
                e = new ClientProtocolException("Status Code: " + status + ", Validation failed");
                e.printStackTrace();
                break;
            default:
                e = new ClientProtocolException("Status Code: " + status + ", Unknown error");
                e.printStackTrace();
                break;
        }
        return true;
    }
}
