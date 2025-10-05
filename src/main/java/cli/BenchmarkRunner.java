package cli;

import algorithms.MinHeap;
import metrics.PerformanceTracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Executes benchmark experiments on the MinHeap implementation.
 * Generates performance metrics across multiple input sizes and distributions,
 * and exports results to a CSV file for empirical analysis.
 *
 * Distributions tested:
 * - random
 * - sorted
 * - reverse
 * - nearly-sorted
 *
 * Each benchmark records:
 * execution time, comparisons, swaps, array accesses, and allocations.
 */
public class BenchmarkRunner {

    /**
     * Main entry point for the benchmark runner.
     * Runs benchmarks for all supported input distributions and writes
     * results to a CSV file in the project root.
     *
     * @param args optional command-line arguments specifying array sizes (e.g., 100 1000 10000)
     * @throws IOException if the CSV output file cannot be created
     */
    public static void main(String[] args) throws IOException {
        List<Integer> sizes = args.length == 0
                ? List.of(100, 1000, 10000, 100000)
                : parseSizes(args);

        Path out = Path.of("benchmarks.csv");
        if (out.getParent() != null) {
            Files.createDirectories(out.getParent());
        }

        List<String> lines = new ArrayList<>();
        lines.add("n,distribution,time(ms),comparisons,swaps,arrayAccesses,allocations,recursiveCalls");

        for (int n : sizes) {
            for (String dist : List.of("random", "sorted", "reverse", "nearly-sorted")) {
                List<Integer> data = generateData(n, dist);
                PerformanceTracker tracker = new PerformanceTracker();
                MinHeap<Integer> heap = new MinHeap<>(tracker);

                long start = System.currentTimeMillis();
                heap.buildHeap(data);
                heap.drainToList();
                long elapsed = System.currentTimeMillis() - start;

                lines.add(String.format(Locale.US,
                        "%d,%s,%d,%d,%d,%d,%d,%d",
                        n, dist, elapsed,
                        tracker.getComparisons(),
                        tracker.getSwaps(),
                        tracker.getArrayAccesses(),
                        tracker.getAllocations(),
                        tracker.getRecursiveCalls()));

                System.out.printf("n=%d dist=%s time=%dms metrics=%s%n",
                        n, dist, elapsed, tracker);
            }
        }

        Files.write(out, lines);
        System.out.println("Benchmark results saved to " + out.toAbsolutePath());
    }

    /**
     * Generates an integer dataset according to the given distribution type.
     *
     * @param n    the number of elements to generate
     * @param type the distribution type ("random", "sorted", "reverse", "nearly-sorted")
     * @return a list of integers with the specified distribution
     */
    private static List<Integer> generateData(int n, String type) {
        List<Integer> data = new ArrayList<>(n);
        for (int i = 0; i < n; i++) data.add(i);
        switch (type) {
            case "random" -> Collections.shuffle(data);
            case "reverse" -> Collections.reverse(data);
            case "nearly-sorted" -> {
                for (int i = 0; i < n / 20; i++) {
                    int a = i, b = Math.min(n - 1, i + 5);
                    Collections.swap(data, a, b);
                }
            }
        }
        return data;
    }

    /**
     * Parses array size arguments from the command line.
     *
     * @param args command-line arguments
     * @return a list of integer sizes
     */
    private static List<Integer> parseSizes(String[] args) {
        List<Integer> sizes = new ArrayList<>();
        for (String s : args) sizes.add(Integer.parseInt(s));
        return sizes;
    }
}
