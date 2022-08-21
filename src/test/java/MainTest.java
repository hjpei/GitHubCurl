import entity.GitRepoIssue;
import util.AppConfig;
import util.CSVWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {

    public static void main(String[] args){
//        String path = "https://api.github.com/repos/capitalone/dataprofiler/issues?per_page=2";
        AppConfig config = new AppConfig();
        config.getUserAndToken();

        GitClient git = new GitClient(config.getToken(), config.getUser());
        Map<String , String > filters = new HashMap<>();
        filters.put("per_page", "2");
        filters.put("sort", "created");
        filters.put("state", "open");
//        filters.put("since", "YYYY-MM-DDTHH:MM:SSZ"); //This is a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ

        List<GitRepoIssue> issues = git.getRepoIssues("dataprofiler", "capitalone", filters);
        new CSVWriter().writeGitRepoIssuesToCSV(issues, "issues.csv");//write to the root directory
    }
}
