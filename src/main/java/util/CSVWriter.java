package util;

import entity.GitRepoIssue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    public void writeGitRepoIssuesToCSV(List<GitRepoIssue> issues, String path){
        StringBuilder sb = new StringBuilder();
        sb.append("Org,Repo,Owner,Issues");
        for(int i = 0; i < issues.size(); i++){
            GitRepoIssue issue = issues.get(i);
            if(sb.length() > 0){
                sb.append("\n");
                sb.append("Capital One," + issue.getHtml_url() + "," + issue.getUrl() + "," + issue.getState());
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
