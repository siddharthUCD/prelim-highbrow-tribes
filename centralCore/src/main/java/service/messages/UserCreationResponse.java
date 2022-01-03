package service.messages;

public class UserCreationResponse implements MySerializable{
    private long uniqueId;

    public UserCreationResponse(long responseId) {
        uniqueId = responseId;
    }

    public long getUniqueId() {
        return uniqueId;
    }
}
