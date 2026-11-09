package Adaptor;

import java.util.Calendar;

/**
 * Adapter: exposes {@link NewDateInterface} while delegating to {@link Calendar}.
 * Months are 1–12 for the client; Calendar uses 0–11 internally.
 */
public class CalendarToNewDateAdapter implements NewDateInterface {
    private final Calendar calendar;

    public CalendarToNewDateAdapter() {
        this.calendar = Calendar.getInstance();
    }

    public CalendarToNewDateAdapter(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void setDay(int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    @Override
    public void setMonth(int month) {
        // Client uses 1–12; Calendar expects 0–11
        calendar.set(Calendar.MONTH, month - 1);
    }

    @Override
    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
    }

    @Override
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    @Override
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public void advanceDays(int days) {
        calendar.add(Calendar.DAY_OF_MONTH, days);
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", getYear(), getMonth(), getDay());
    }
}
