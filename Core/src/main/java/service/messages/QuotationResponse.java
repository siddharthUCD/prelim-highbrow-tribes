package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.Quotation;

public class QuotationResponse implements MySerializable{
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private Quotation quotation;
    public QuotationResponse(int id, Quotation quotation) {
        this.id = id;
        this.quotation = quotation;
    }
    // add get and set methods for each field
}
