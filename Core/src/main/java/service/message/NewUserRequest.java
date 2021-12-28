package service.message;

import service.core.UserInfo;

public class NewUserRequest {
    private long RequestId;
    private UserInfo NewUser;

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
}
