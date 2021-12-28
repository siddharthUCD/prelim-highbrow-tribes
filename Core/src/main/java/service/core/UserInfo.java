package service.core;

import java.util.List;

public class UserInfo {
    private String name;
    private String gitHubId;
    private Interests interests;

    public UserInfo(String name, String gitHubId, Interests interests){
        this.name = name;
        this.gitHubId = gitHubId;
        this.interests = interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGitHubId() {
        return gitHubId;
    }

    public void setGitHubId(String gitHubId) {
        this.gitHubId = gitHubId;
    }

    public Interests getInterests() {
        return interests;
    }

    public void setInterests(Interests interests) {
        this.interests = interests;
    }

    public List<String> getProgrammingLanguages(){
        return interests.GetProgrammingLanguages();
    }
}
