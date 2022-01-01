package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.Tribe;

import java.util.List;

public class NewUserResponse implements MySerializable{
    @Getter
    @Setter
    private long RequestId;
    @Getter
    @Setter
    private List<Tribe> SuggestedTribes;

    public NewUserResponse(){};
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
