package Observer;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        WeatherStation weatherStation = new WeatherStation();
        PhoneObserver phoneObserver = new PhoneObserver();
        ComputerObserver computerObserver = new ComputerObserver();
        weatherStation.addObservers(phoneObserver);
        weatherStation.addObservers(computerObserver);
        Thread weatherStationThread = new Thread(weatherStation);
        weatherStationThread.start();

        Thread.sleep(5000);
        weatherStation.removeObservers(phoneObserver);
    }

}
