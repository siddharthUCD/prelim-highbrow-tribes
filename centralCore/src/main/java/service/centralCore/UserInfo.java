package service.centralCore;

import lombok.Getter;
import lombok.Setter;
import service.messages.MySerializable;

import java.util.HashSet;

public class UserInfo implements MySerializable {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String gitHubId;
    @Getter
    @Setter
    private Interests interests;
    @Getter
    @Setter
    private long UniqueId;
    @Getter
    @Setter
    private int PortNumber;

    public UserInfo(){};
    public UserInfo(String name, String gitHubId, long uniqueId){
        this.name = name;
        this.gitHubId = gitHubId;
        this.UniqueId = uniqueId;
        interests = null;
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

    public HashSet<String> getProgrammingLanguages(){
        return interests.getProgrammingLanguages();
    }

    public void setProgrammingLanguages(HashSet<String> programmingLanguages){
        interests = new Interests();
        interests.setProgrammingLanguages(programmingLanguages);
    }

    public long getUniqueId() {
        return UniqueId;
    }

    public int getPortNumber() {
        return PortNumber;
    }

    public void setPortNumber(int portNumber) {
        PortNumber = portNumber;
    }
}
