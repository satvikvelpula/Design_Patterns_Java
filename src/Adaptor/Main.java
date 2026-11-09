package Adaptor;

/**
 * Client code: talks only to {@link NewDateInterface}, never to Calendar directly.
 */
public class Main {
    public static void main(String[] args) {
        NewDateInterface date = new CalendarToNewDateAdapter();

        date.setYear(2026);
        date.setMonth(11);
        date.setDay(9);
        System.out.println("Initial date : " + format(date));

        date.advanceDays(10);
        System.out.println("After +10 days: " + format(date));

        date.advanceDays(-3);
        System.out.println("After -3 days : " + format(date));

        date.setDay(1);
        date.setMonth(1);
        date.setYear(2027);
        System.out.println("Reset to      : " + format(date));
        date.advanceDays(40);
        System.out.println("After +40 days: " + format(date));
    }

    private static String format(NewDateInterface date) {
        return String.format(
                "%04d-%02d-%02d (y=%d m=%d d=%d)",
                date.getYear(),
                date.getMonth(),
                date.getDay(),
                date.getYear(),
                date.getMonth(),
                date.getDay()
        );
    }
}
