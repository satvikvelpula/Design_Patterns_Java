package Strategy;

public class AlgorithmSetter {

    private final int[] arr;
    private AlgorithmStrategy strategy;

    public AlgorithmSetter(int[] arr_arg) {
        this.arr = arr_arg;
    }

    public int[] array_feed() {
        return arr;
    }

    public void setStrategy(AlgorithmStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort() {
        strategy.sorter();
    }
}