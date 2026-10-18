package Strategy;

public class InsertionSort implements AlgorithmStrategy {

    private int[] arr;

    public InsertionSort(AlgorithmSetter context) {
        this.arr = context.array_feed();
    }

    public void sorter() {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public void printer() {
        for (int n : arr) {
            System.out.println(n);
        }
    }
}
