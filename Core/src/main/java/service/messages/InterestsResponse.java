package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.Interests;

public class InterestsResponse implements MySerializable{
    @Getter
    @Setter
    private long RequestId;
    @Getter
    @Setter
    private Interests _Interest;

    public InterestsResponse(){};
    public InterestsResponse(long requestId, Interests interest) {
        this.RequestId = requestId;
        this._Interest = interest;
    }

    public long getRequestId() {
        return RequestId;
    }

    public Interests getInterests() {
        return _Interest;
    }
}
