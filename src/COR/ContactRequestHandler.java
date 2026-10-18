package COR;

public class ContactRequestHandler implements Handler {
    private Handler nextHandler;

    public void setNextHandler(Handler nextMessageHandler) {
        this.nextHandler = nextMessageHandler;
    }

    public void handle(Message messageToHandle) {
        if (messageToHandle.getMessageType() == MessageType.CONTACT_REQUESTS) {
            System.out.println("The feedback regarding: " + messageToHandle.getMessageType() + " " + messageToHandle.getMessageContent() + " is handled by Contact Requests.");
        } else {
            nextHandler.handle(messageToHandle);
        }
    }
}
