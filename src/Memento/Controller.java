package Memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Caretaker: undo / redo stacks of {@link IMemento}, plus history list access.
 */
public class Controller {
    private final Model model;
    private Gui gui;

    /** Undo list: states saved before each new user change. */
    private final List<IMemento> undoHistory = new ArrayList<>();
    /** Redo list: states popped while undoing; cleared on a fresh edit. */
    private final List<IMemento> redoHistory = new ArrayList<>();

    public Controller(Model model) {
        this.model = model;
    }

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public int getOption(int index) {
        return model.getOption(index);
    }

    public boolean getIsSelected() {
        return model.getIsSelected();
    }

    public void setOption(int optionNumber, int choice) {
        pushCurrentToUndo();
        redoHistory.clear();
        model.setOption(optionNumber, choice);
        gui.updateGui();
        gui.refreshHistoryView();
    }

    public void setIsSelected(boolean selected) {
        pushCurrentToUndo();
        redoHistory.clear();
        model.setIsSelected(selected);
        gui.updateGui();
        gui.refreshHistoryView();
    }

    public void undo() {
        if (undoHistory.isEmpty()) {
            return;
        }
        // Current state → redo list, then restore previous from undo list
        redoHistory.add(model.createMemento());
        IMemento previous = undoHistory.remove(undoHistory.size() - 1);
        model.restoreState(previous);
        gui.refreshHistoryView();
    }

    public void redo() {
        if (redoHistory.isEmpty()) {
            return;
        }
        // Current state → undo list, then restore from redo list
        undoHistory.add(model.createMemento());
        IMemento next = redoHistory.remove(redoHistory.size() - 1);
        model.restoreState(next);
        gui.refreshHistoryView();
    }

    /** Restore a state chosen from the history window. */
    public void restoreFromHistory(IMemento selected) {
        if (selected == null) {
            return;
        }
        pushCurrentToUndo();
        redoHistory.clear();
        model.restoreState(selected);
        gui.refreshHistoryView();
    }

    public List<IMemento> getUndoHistoryView() {
        return Collections.unmodifiableList(undoHistory);
    }

    private void pushCurrentToUndo() {
        undoHistory.add(model.createMemento());
    }
}
