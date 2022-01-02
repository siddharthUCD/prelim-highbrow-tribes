package service.messages;

public class UserCreationResponse {
    private long uniqueId;

    public UserCreationResponse(long responseId) {
        uniqueId = responseId;
    }

    public long getUniqueId() {
        return uniqueId;
    }
}
