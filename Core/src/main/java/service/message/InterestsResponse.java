package service.message;

import service.core.Interests;

public class InterestsResponse {
    private long RequestId;
    private Interests _Interest;

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
