package service.message;

public class InterestsRequest {
    private long RequestId;

    public InterestsRequest(long requestId) {
        RequestId = requestId;
    }

    public long getRequestId() {
        return RequestId;
    }
}
