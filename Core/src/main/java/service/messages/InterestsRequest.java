package service.messages;

import lombok.Getter;
import lombok.Setter;

public class InterestsRequest implements MySerializable{
    @Getter
    @Setter
    private long RequestId;

    public InterestsRequest(){};
    public InterestsRequest(long requestId) {
        RequestId = requestId;
    }

    public long getRequestId() {
        return RequestId;
    }
}
