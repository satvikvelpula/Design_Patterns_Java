package Strategy;

public class Quicksort implements AlgorithmStrategy {

    private AlgorithmSetter context;

    protected int[] res;

    protected int[] arr;

    protected int low;

    protected int high;

    public Quicksort(AlgorithmSetter context_arg, int sorting_algo_low, int sorting_algo_high) {

        this.context = context_arg;

        this.arr = context.array_feed();

        this.low = sorting_algo_low;

        this.high = sorting_algo_high;

    }

    public int partition(int[] arr, int low, int high) {

        int pivot = arr[high];

        int i = (low - 1);

        for (int j = low; j < high; j++) {

            // If current element is smaller than or
            // equal to pivot

            if (arr[j] <= pivot) {

                i++;

                int temp = arr[i];

                arr[i] = arr[j];

                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];

        arr[i + 1] = arr[high];

        arr[high] = temp;

        return i + 1;
    }

    private void quicksort(int[] arr, int low, int high) {

        if (low < high) {

            int pivotIndex = partition(arr, low, high);

            quicksort(arr, low, pivotIndex - 1);

            quicksort(arr, pivotIndex + 1, high);
        }
    }

    @Override
    public void sorter() {

        quicksort(arr, low, high);
    }

    public void printer() {

        for (int n : arr) {

            System.out.println(n);
        }
    }
}