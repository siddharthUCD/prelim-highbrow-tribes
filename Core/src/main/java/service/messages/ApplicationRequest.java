package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.ClientInfo;

public class ApplicationRequest implements MySerializable{
    @Getter
    @Setter
    private ClientInfo clientInfo;

    public ApplicationRequest(){

    }

    public ApplicationRequest(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

}
