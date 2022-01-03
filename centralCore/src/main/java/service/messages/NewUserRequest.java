package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.centralCore.UserInfo;

public class NewUserRequest implements MySerializable{
    @Getter
    @Setter
    private UserInfo newUser;

    public NewUserRequest(){};

    public NewUserRequest(UserInfo newUser) {
        this.newUser = newUser;
    }
}
