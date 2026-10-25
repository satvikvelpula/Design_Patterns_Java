package Memento;

import java.time.LocalDateTime;

/**
 * Narrow interface exposed to the caretaker (Controller).
 * Metadata for the history window lives here; the full snapshot stays in {@link Memento}.
 */
public interface IMemento {
    LocalDateTime getSavedAt();

    /** Human-readable summary for the history ListView. */
    String getDescription();
}
