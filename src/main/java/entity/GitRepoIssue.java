package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitRepoIssue {
    private String url;
    private String repository_url;
    private String html_url;
    private long id;
    private String nodeId;
    private String createdAt;
    private String updatedAt;
    private String title;
    private String state;
    private GitUser assignee;
    private List<GitUser> assignees;
    private GitUser closed_by;
    private boolean locked;
    private GitPullRequest pull_request;
    private List<GitLabel> labels;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepository_url() {
        return repository_url;
    }

    public void setRepository_url(String repository_url) {
        this.repository_url = repository_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public GitUser getAssignee() {
        return assignee;
    }

    public void setAssignee(GitUser assignee) {
        this.assignee = assignee;
    }

    public List<GitUser> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<GitUser> assignees) {
        this.assignees = assignees;
    }

    public GitUser getClosed_by() {
        return closed_by;
    }

    public void setClosed_by(GitUser closed_by) {
        this.closed_by = closed_by;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<GitLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<GitLabel> labels) {
        this.labels = labels;
    }

    public GitPullRequest getPull_request() {
        return pull_request;
    }

    public void setPull_request(GitPullRequest pull_request) {
        this.pull_request = pull_request;
    }
}
