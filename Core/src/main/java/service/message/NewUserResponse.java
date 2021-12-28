package service.message;

import service.core.Tribe;

import java.util.List;

public class NewUserResponse {
    private long RequestId;
    private List<Tribe> SuggestedTribes;

    public NewUserResponse(long requestId, List<Tribe> suggestedTribes) {
        RequestId = requestId;
        SuggestedTribes = suggestedTribes;
    }

    public long getRequestId() {
        return RequestId;
    }

    public List<Tribe> getSuggestedTribes() {
        return SuggestedTribes;
    }
}
