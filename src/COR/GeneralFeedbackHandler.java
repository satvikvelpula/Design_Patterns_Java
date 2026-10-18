package COR;

public class GeneralFeedbackHandler implements Handler {

    public void setNextHandler(Handler nextHandler) {
        return;
    }

    public void handle(Message messageToHandle) {
        if (messageToHandle.getMessageType() == MessageType.GENERAL_FEEDBACK) {
            System.out.println("The feedback regarding: " + messageToHandle.getMessageType() + " " + messageToHandle.getMessageContent() + " is handled by General Feedback.");
        } else {
            System.out.println("End of chain. Could not process your request. ");
        }
    }
}
    