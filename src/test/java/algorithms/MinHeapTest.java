package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests MinHeap correctness and edge cases:
 * empty, single element, duplicates, sorted, reversed, random, decreaseKey, merge.
 */
public class MinHeapTest {

    @Test
    public void testEmptyHeap() {
        MinHeap<Integer> heap = new MinHeap<>(new PerformanceTracker());
        assertTrue(heap.isEmpty());
        assertThrows(IllegalStateException.class, heap::peek);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    public void testSingleElement() {
        MinHeap<Integer> heap = new MinHeap<>(new PerformanceTracker());
        heap.insert(42);
        assertEquals(42, heap.peek());
        assertEquals(42, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testDuplicates() {
        MinHeap<Integer> heap = new MinHeap<>(new PerformanceTracker());
        heap.insert(5); heap.insert(5); heap.insert(5);
        assertEquals(List.of(5,5,5), heap.drainToList());
    }

    @Test
    public void testSortedInput() {
        MinHeap<Integer> heap = new MinHeap<>(new PerformanceTracker());
        for (int i = 0; i < 10; i++) heap.insert(i);
        for (int i = 0; i < 10; i++) assertEquals(i, heap.extractMin());
    }

    @Test
    public void testReverseSortedInput() {
        MinHeap<Integer> heap = new MinHeap<>(new PerformanceTracker());
        for (int i = 9; i >= 0; i--) heap.insert(i);
        for (int i = 0; i < 10; i++) assertEquals(i, heap.extractMin());
    }

    @Test
    public void testDecreaseKey() {
        MinHeap<Integer> heap = new MinHeap<>(new PerformanceTracker());
        MinHeap.HeapNode<Integer> n1 = heap.insert(50);
        MinHeap.HeapNode<Integer> n2 = heap.insert(100);
        heap.decreaseKey(n2, 25);
        assertEquals(25, heap.peek());
    }

    @Test
    public void testMergeHeaps() {
        MinHeap<Integer> a = new MinHeap<>(new PerformanceTracker());
        MinHeap<Integer> b = new MinHeap<>(new PerformanceTracker());
        a.insert(1); a.insert(5); b.insert(2); b.insert(4);
        MinHeap<Integer> merged = MinHeap.merge(a, b);
        assertEquals(List.of(1,2,4,5), merged.drainToList());
    }

    @Test
    public void testRandomProperty() {
        Random rnd = new Random(123);
        Integer[] arr = rnd.ints(100, 0, 1000).boxed().toArray(Integer[]::new);
        MinHeap<Integer> heap = new MinHeap<>(new PerformanceTracker());
        for (int v : arr) heap.insert(v);
        int prev = Integer.MIN_VALUE;
        while (!heap.isEmpty()) {
            int cur = heap.extractMin();
            assertTrue(cur >= prev);
            prev = cur;
        }
    }
}
