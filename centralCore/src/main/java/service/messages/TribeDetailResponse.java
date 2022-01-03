package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.centralCore.Tribe;

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
}