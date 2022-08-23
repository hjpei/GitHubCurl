import entity.GitRepo;
import entity.GitRepoIssue;
import util.AppConfig;
import util.CSVWriter;
import util.DateUtil;

import java.util.*;

public class MainTest {
    private static GitClient git;
    private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static void main(String[] args){
        initGit();
        String org = "aws";
        List<GitRepo> repos = listAllRepos(org);
        for(GitRepo repo : repos){
            List<GitRepoIssue> issues = listAllIssues(repo, org);
            //remove pull request
            repo.setIssues(new ArrayList<>());
            for(GitRepoIssue issue : issues){
                if(issue.getPull_request() == null){
                    repo.getIssues().add(issue);
                }
            }
        }
        new CSVWriter().writeGitRepoIssuesToCSV(repos, org + "issues.csv");
    }

    // list last 5 modified repos from one org
    private static List<GitRepo> listAllRepos(String org){
        Map<String , String > filters = new HashMap<>();
//        filters.put("per_page", "5");
        filters.put("sort", "updated");
        DateUtil dateUtil = new DateUtil();
        filters.put("since", dateUtil.convertDate(dateUtil.getLastNMonthsDate(3), dateFormat));
        return git.listAllRepos(org, filters);
    }

    private static List<GitRepoIssue> listAllIssues(GitRepo repo, String org){
        List<GitRepoIssue> issues = new ArrayList<>();
        Map<String , String > filters = new HashMap<>();
//        filters.put("per_page", "2");
        filters.put("sort", "updated");
        filters.put("state", "open");
        DateUtil dateUtil = new DateUtil();
        filters.put("since", dateUtil.convertDate(dateUtil.getLastNMonthsDate(3), dateFormat));
        issues = git.getRepoIssues(repo.getName(), org, filters);
        return issues;
    }

    private static void initGit(){
        AppConfig config = new AppConfig();
        config.getUserAndToken();
        git = new GitClient(config.getToken(), config.getUser());
    }
}
