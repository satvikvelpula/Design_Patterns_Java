package Memento;

/**
 * Originator: holds rectangle color indices (0/1/2) and checkbox state.
 */
public class Model {
    private final int[] options = new int[3];
    private boolean isSelected;
    private Gui gui;

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public int getOption(int index) {
        return options[index];
    }

    public void setOption(int index, int value) {
        options[index] = value;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        this.isSelected = selected;
    }

    public IMemento createMemento() {
        return new Memento(options, isSelected);
    }

    public void restoreState(IMemento memento) {
        Memento m = (Memento) memento;
        int[] restored = m.getOptions();
        System.arraycopy(restored, 0, options, 0, options.length);
        isSelected = m.isSelected();
        if (gui != null) {
            gui.updateGui();
        }
    }
}
