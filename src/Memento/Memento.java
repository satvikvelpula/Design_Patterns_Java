package Memento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Snapshot of the model (originator state). Only Model reads the full payload;
 * Controller sees metadata via {@link IMemento}.
 */
public class Memento implements IMemento {
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final int[] options;
    private final boolean isSelected;
    private final LocalDateTime savedAt;

    public Memento(int[] options, boolean isSelected) {
        this.options = options.clone();
        this.isSelected = isSelected;
        this.savedAt = LocalDateTime.now();
    }

    int[] getOptions() {
        return options.clone();
    }

    boolean isSelected() {
        return isSelected;
    }

    @Override
    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    @Override
    public String getDescription() {
        return String.format(
                "%s  |  boxes=[%d,%d,%d]  check=%s",
                savedAt.format(TIME_FMT),
                options[0],
                options[1],
                options[2],
                isSelected
        );
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
