package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.ClientInfo;

public class QuotationRequest implements MySerializable {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private ClientInfo clientInfo;
    public QuotationRequest(int id, ClientInfo clientInfo) {
        this.id = id;
        this.clientInfo = clientInfo;
    }
    // add get and set methods for each field
}
