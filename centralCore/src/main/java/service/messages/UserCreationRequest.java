package service.messages;

import service.centralCore.UserInfo;

public class UserCreationRequest implements MySerializable{
    private long RequestId;
    private UserInfo NewUser;

    public UserCreationRequest(long requestId, UserInfo newUser) {
        this.RequestId = requestId;
        this.NewUser = newUser;
    }

    public long getRequestId() {
        return RequestId;
    }

    public UserInfo getNewUser() {
        return NewUser;
    }
}
