package Strategy;

public class SelectionSort implements AlgorithmStrategy {

    private AlgorithmSetter context;
    private int[] res;

    public SelectionSort(AlgorithmSetter context_arg) {
        this.context = context_arg;
    }

    public void sorter() {
        int[] arr = context.array_feed();
        
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
          
            // Assume the current position holds
            // the minimum element
            int min_idx = i;

            // Iterate through the unsorted portion
            // to find the actual minimum
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_idx]) {
                  
                    // Update min_idx if a smaller element
                    // is found
                    min_idx = j;
                }
            }

            // Move minimum element to its
            // correct position
            int temp = arr[i];
            arr[i] = arr[min_idx];
            arr[min_idx] = temp;           
        }

        res = arr;
    }

    public void printer() {
        for(int n : res) {
            System.out.println(n);
        }
    }
}
