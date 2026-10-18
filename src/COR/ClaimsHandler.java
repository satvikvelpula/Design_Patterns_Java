package COR;

public class ClaimsHandler implements Handler {
    private Handler nextHandler;

    public void setNextHandler(Handler nextMessageHandler) {
        this.nextHandler = nextMessageHandler;
    }

    public void handle(Message messageToHandle) {
        if (messageToHandle.getMessageType() == MessageType.COMPENSATION_CLAIMS) {
            System.out.println("The feedback regarding: " + messageToHandle.getMessageType() + " " + messageToHandle.getMessageContent() + " is handled by Claims.");
        } else {
            nextHandler.handle(messageToHandle); // This invites the chaining question. When we chain handlers, do we depict the chain in the concrete handler classes inside the handle methods? Here we can either directly define the class we want to use, or we can be vague and go with polymorphism and select Handler, this way when we actually call the objects we can specify in the params what handler will handle next?
        }
    }
}
