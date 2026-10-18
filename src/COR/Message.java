package COR;

public class Message {
    private MessageType message_type;
    private String message_content;
    private String sender_email;

    public Message(MessageType mt, String mc, String se) {
        this.message_type = mt;
        this.message_content = mc;
        this.sender_email = se;
    }

    public MessageType getMessageType() {
        return message_type;
    }

    public String getMessageContent() {
        return message_content;
    }

    public String getSenderEmail() {
        return sender_email;
    }
}
