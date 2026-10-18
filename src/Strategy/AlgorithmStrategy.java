package Strategy;

/* Each sorting algorithm belongs to each concrete strategy. This means that each concrete strategy algorithm should implement from a single strategy interface.  */
/* Let's first walk through what each sorting algorithm class has in common: Normally it's the actual sorting method, then it's usually the printing method that shows the proof of sorting. */
/* Normally, each strategy should have some sort of context to be able to pass on. In thi assignment, we are supposed to compare an array through all of the algorithms. The best way to do this is to give each sorting algorithm concrete class the array to work with */ 
public interface AlgorithmStrategy {

    void sorter();
    void printer();

}
