package Observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements Runnable {

    private int temperature;
    private List<Observer> observers;
    private static final int MIN_TEMPERATURE = -20;
    private static final int MAX_TEMPERATURE = 40;

    public WeatherStation() {
        observers = new ArrayList<>();
        this.temperature = randomTemperature();
    }

    public void addObservers(Observer observer) {
        observers.add(observer);
    }

    private int randomTemperature() {
        return (int) (Math.random() *
                (MAX_TEMPERATURE - MIN_TEMPERATURE + 1))
                + MIN_TEMPERATURE;
    }

    private void updateTemperature() {
        int change = Math.random() < 0.5 ? -1 : 1;

        if (temperature == MAX_TEMPERATURE && change == 1) {
            change = -1;
        }

        if (temperature == MIN_TEMPERATURE && change == -1) {
            change = 1;
        }

        temperature += change;
    }

    public void removeObservers(Observer observer) {
        observers.remove(observer);
    }


    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    @Override
    public void run() {
        randomTemperature();

        while (true) {
            updateTemperature();
            notifyObservers();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
