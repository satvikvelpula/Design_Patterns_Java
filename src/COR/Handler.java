package COR;

public interface Handler {
    void handle(Message messageToHandle);
    void setNextHandler(Handler nextHandler);

}

// This interface is the backbone of every handler. Every handler that we make has to support each and every enumn type of the request/handlement that we have.

