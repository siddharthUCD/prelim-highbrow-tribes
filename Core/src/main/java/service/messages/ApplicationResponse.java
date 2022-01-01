package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.ClientInfo;
import service.core.Quotation;

import java.util.List;

public class ApplicationResponse implements MySerializable{
    @Getter
    @Setter
    private ClientInfo clientInfo;

    @Getter
    @Setter
    private List<Quotation> quotationsList;

    public ApplicationResponse(ClientInfo clientInfo, List<Quotation> quotationsList) {
        this.clientInfo = clientInfo;
        this.quotationsList = quotationsList;
    }
}
