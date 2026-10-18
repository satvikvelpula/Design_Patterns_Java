package COR;

public class DevSuggestionHandler implements Handler {
    private Handler nextHandler;

    public void setNextHandler(Handler nextMessageHandler) {
        this.nextHandler = nextMessageHandler;
    }

    public void handle(Message messageToHandle) {
        if (messageToHandle.getMessageType() == MessageType.DEV_SUGGESTIONS) {
            System.out.println("The feedback regarding: " + messageToHandle.getMessageType() + " " + messageToHandle.getMessageContent() + " is handled by Dev Suggestions.");
        } else {
            nextHandler.handle(messageToHandle);
        }
    }
}
