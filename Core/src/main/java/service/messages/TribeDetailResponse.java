package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.core.Tribe;

public class TribeDetailResponse implements MySerializable{
    @Getter
    @Setter
    private long UniqueId;
    @Getter
    @Setter
    private Tribe tribe;

    public TribeDetailResponse(){};
    public TribeDetailResponse(long uniqueId, Tribe tribe) {
        UniqueId = uniqueId;
        this.tribe = tribe;
    }

    public long getUniqueId() {
        return UniqueId;
    }

    public Tribe getTribe() {
        return tribe;
    }
}
