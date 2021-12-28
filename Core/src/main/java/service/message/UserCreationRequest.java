package service.message;

import service.core.Interests;
import service.core.UserInfo;

public class UserCreationRequest {
    private long RequestId;
    private UserInfo NewUser;
    private Interests _Interests;

    public UserCreationRequest(long requestId, UserInfo newUser, Interests interests) {
        this.RequestId = requestId;
        this.NewUser = newUser;
        this._Interests = interests;
    }

    public long getRequestId() {
        return RequestId;
    }

    public UserInfo getNewUser() {
        return NewUser;
    }
}