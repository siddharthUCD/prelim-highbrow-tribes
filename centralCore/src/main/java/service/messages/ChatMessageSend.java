package service.messages;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

public class ChatMessageSend implements MySerializable {
    @Getter
    @Setter
    private String SenderName;
    @Getter
    @Setter
    private Timestamp SentTime;
    @Getter
    @Setter
    private long UniqueId;
    @Getter
    @Setter
    private String Message;

    public ChatMessageSend(){};
    public ChatMessageSend(String senderName, Timestamp sentTime, long uniqueId, String message) {
        SenderName = senderName;
        SentTime = sentTime;
        UniqueId = uniqueId;
        Message = message;
        Instant temp;
    }

    public long getUniqueId() {
        return UniqueId;
    }

    public String getMessage() {
        return Message;
    }

    public String getSenderName() {
        return SenderName;
    }

    public Timestamp getSentTime() {
        return SentTime;
    }
}
