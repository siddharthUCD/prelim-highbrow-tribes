package service.message;

import service.core.Interests;
import service.core.Tribe;

import java.util.List;

public class UserCreationResponse {
    private long RequestId;
    private List<Tribe> SuggestedTribes;

    public UserCreationResponse(long responseId) {
        RequestId = responseId;
    }

    public long getRequestId() {
        return RequestId;
    }

    public List<Tribe> getSuggestedTribes() {
        return SuggestedTribes;
    }

    public void setSuggestedTribes(List<Tribe> suggestedTribes) {
        SuggestedTribes = suggestedTribes;
    }

    public void addSuggestedTribes(Tribe suggestedTribe) {
        SuggestedTribes.add(suggestedTribe);
    }
}
