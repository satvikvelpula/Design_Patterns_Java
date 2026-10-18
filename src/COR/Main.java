package COR;

public class Main {
    public static void main(String[] args) {
        Handler claimsHandler = new ClaimsHandler();
        Handler contactHandler = new ContactRequestHandler();
        Handler devSuggestionHandler = new DevSuggestionHandler();
        Handler generalFeedbackHandler = new GeneralFeedbackHandler();

        // Chain: Claims → Contact → Dev Suggestions → General Feedback (end)
        claimsHandler.setNextHandler(contactHandler);
        contactHandler.setNextHandler(devSuggestionHandler);
        devSuggestionHandler.setNextHandler(generalFeedbackHandler);

        Message claim = new Message(
                MessageType.COMPENSATION_CLAIMS,
                "Flight delayed 6 hours, requesting voucher.",
                "alice@example.com"
        );
        Message contact = new Message(
                MessageType.CONTACT_REQUESTS,
                "Please call me about my booking.",
                "bob@example.com"
        );
        Message suggestion = new Message(
                MessageType.DEV_SUGGESTIONS,
                "Add dark mode to the mobile app.",
                "carol@example.com"
        );
        Message feedback = new Message(
                MessageType.GENERAL_FEEDBACK,
                "Great service overall, keep it up!",
                "dave@example.com"
        );

        System.out.println("--- Processing messages through the chain ---");
        claimsHandler.handle(claim);
        claimsHandler.handle(contact);
        claimsHandler.handle(suggestion);
        claimsHandler.handle(feedback);
    }
}
