package service.messages;

import lombok.Getter;
import lombok.Setter;

public class TribeDetailRequest implements MySerializable{
    @Getter
    @Setter
    private long UniqueId;

    public TribeDetailRequest(){};
    public TribeDetailRequest(long uniqueId) {
        UniqueId = uniqueId;
    }

    public long getUniqueId() {
        return UniqueId;
    }
}
