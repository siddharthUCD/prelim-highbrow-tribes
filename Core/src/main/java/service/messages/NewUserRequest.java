package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.UserInfo;

public class NewUserRequest implements MySerializable{
    @Getter
    @Setter
    private long RequestId;
    @Getter
    @Setter
    private UserInfo NewUser;

    public NewUserRequest(){};
    public NewUserRequest(long requestId, UserInfo newUser) {
        this.RequestId = requestId;
        this.NewUser = newUser;
    }

    public long getRequestId() {
        return RequestId;
    }

    public UserInfo getUserInfo() {
        return NewUser;
    }

    public String getName(){
        return NewUser.getName();
    }

    public long getUniqueId(){
        return NewUser.getUniqueId();
    }

    public String getGithubId(){
        return NewUser.getGitHubId();
    }
}
