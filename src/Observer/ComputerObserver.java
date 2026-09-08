package Observer;

public class ComputerObserver implements Observer {
    @Override
    public void update(int temperature) {
        System.out.println("Computer Notification: " + "Temperature is now " + temperature);
    }
}

