package util;

import entity.GitLabel;
import entity.GitRepo;
import entity.GitRepoIssue;
import entity.GitUser;
import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    public void writeGitRepoIssuesToCSV(List<GitRepo> repos, String path){
        StringBuilder sb = new StringBuilder();
        sb.append("Language,Repository,URL,Title,Labels,Assignee,Difficulty");
        for(GitRepo repo : repos){
            List<GitRepoIssue> issues = repo.getIssues();
            if(issues == null || issues.size() == 0){
                continue;
            }
            for(GitRepoIssue issue : issues){
                if(sb.length() > 0){
                    sb.append("\n");
                    String labels = "";
                    if(issue.getLabels() != null && issue.getLabels().size() > 0){
                        for(GitLabel label : issue.getLabels()){
                            if(labels.length() > 0){
                                labels += ",";
                            }
                            labels += label.getName();
                        }
                    }
                    String admins = "";
                    if(issue.getAssignees() != null && issue.getAssignees().size() > 0){
                        for(GitUser assignee : issue.getAssignees()){
                            if(admins.length() > 0){
                                admins += ",";
                            }
                            admins += assignee.getLogin();
                        }
                    }
                    //Language,Repository,URL,Title,Labels,Assignee,Difficulty
                    sb.append(repo.getLanguage() + "," + repo.getName() + "," + issue.getHtml_url()
                            + "," + StringEscapeUtils.escapeCsv(issue.getTitle())
                            + "," + StringEscapeUtils.escapeCsv(labels) + ","
                            + StringEscapeUtils.escapeCsv(admins)
                    );
                }
            }
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));
            writer.append(sb);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
