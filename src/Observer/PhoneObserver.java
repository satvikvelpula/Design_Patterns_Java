package Observer;

public class PhoneObserver implements Observer {
    @Override
    public void update(int temperature) {
        System.out.println("Phone Notification: " + "Temperature is now " + temperature);
    }
}
