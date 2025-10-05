package metrics;

/**
 * Tracks performance metrics for algorithmic operations.
 * This class is used to record counts of fundamental operations such as
 * comparisons, swaps, array accesses, and memory allocations.
 *
 * It is primarily intended for use in algorithm benchmarking and analysis
 * to empirically validate theoretical time and space complexity.
 */
public class PerformanceTracker {

    private long comparisons;
    private long swaps;
    private long arrayAccesses;
    private long allocations;
    private long recursiveCalls;
    /**
     * Creates a new tracker with all counters set to zero.
     */
    public PerformanceTracker() {
        reset();
    }

    /**
     * Increments the number of comparisons performed by one.
     */
    public void addComparison() {
        comparisons++;
    }

    /**
     * Increments the number of swap operations by one.
     */
    public void addSwap() {
        swaps++;
    }

    /**
     * Increments the number of array or memory access operations by one.
     */
    public void addArrayAccess() {
        arrayAccesses++;
    }

    /**
     * Increments the number of allocated elements or nodes by one.
     */
    public void addAllocation() {
        allocations++;
    }

    /**
     * Increments the number of recursiveCalls by one.
     */
    public void addRecursiveCall() {
        recursiveCalls++;
    }

    /**
     * Returns the total number of comparisons recorded.
     *
     * @return the comparison count
     */
    public long getComparisons() {
        return comparisons;
    }

    /**
     * Returns the total number of swaps recorded.
     *
     * @return the swap count
     */
    public long getSwaps() {
        return swaps;
    }

    /**
     * Returns the total number of array accesses recorded.
     *
     * @return the array access count
     */
    public long getArrayAccesses() {
        return arrayAccesses;
    }

    /**
     * Returns the total number of allocations recorded.
     *
     * @return the allocation count
     */
    public long getAllocations() {
        return allocations;
    }

    /**
     * Returns the total number of recursiveCalls recorded.
     *
     * @return the recursiveCalls
     */
    public long getRecursiveCalls() {
        return recursiveCalls;
    }
    /**
     * Resets all performance counters to zero.
     */
    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        allocations = 0;
        recursiveCalls = 0;
    }

    /**
     * Returns a string representation of all tracked metrics.
     *
     * @return a summary of metrics
     */
    @Override
    public String toString() {
        return "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", arrayAccesses=" + arrayAccesses +
                ", allocations=" + allocations +
                ", recursiveCalls=" + recursiveCalls;
    }
}
