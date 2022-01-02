package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.centralCore.UserInfo;

public class ChatRegisterRequest implements MySerializable {
    @Getter
    @Setter
    private UserInfo _UserInfo;

    public ChatRegisterRequest(){};
    public ChatRegisterRequest(UserInfo userInfo) {
        _UserInfo = userInfo;
    };

    public void set_UserInfo(UserInfo userInfo){
        this._UserInfo = userInfo;
    }
    public long getUniqueId() {
        return _UserInfo.getUniqueId();
    }

    public void setUniqueId(int uniqueId) {
        _UserInfo.setUniqueId(uniqueId);
    }

    public int getPortNumber() {
        return _UserInfo.getPortNumber();
    }
}
