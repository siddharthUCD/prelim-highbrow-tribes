package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.centralCore.Interests;

public class InterestsResponse implements MySerializable {
    @Getter
    @Setter
    private long requestId;
    @Getter
    @Setter
    private Interests interest;

    public InterestsResponse(){};
    public InterestsResponse(long requestId, Interests interest) {
        this.requestId = requestId;
        this.interest = interest;
    }
}
