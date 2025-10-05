package algorithms;

import metrics.PerformanceTracker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A binary min-heap implementation that maintains the smallest element at the root.
 * The heap is backed by an ArrayList and supports fundamental operations:
 * insert, extractMin, peek, decreaseKey, and merge.
 *
 * This class also provides:
 * - Bottom-up heap construction (buildHeap)
 * - Performance tracking of comparisons, swaps, and memory accesses
 * - Support for decreaseKey through a stable HeapNode handle
 *
 * The MinHeap is a generic structure parameterized by a Comparable type.
 * All operations have standard heap complexities:
 * - insert: O(log n)
 * - extractMin: O(log n)
 * - peek: O(1)
 * - buildHeap: O(n)
 * - merge: O(n + m)
 * - decreaseKey: O(log n)
 *
 * @param <T> the type of elements held in this heap
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * A handle representing a node in the heap.
     * Returned by insert and buildHeap to allow efficient key decrease.
     */
    public static final class HeapNode<E extends Comparable<? super E>> {
        private E key;
        private int index;
        private final MinHeap<E> owner;

        private HeapNode(E key, int index, MinHeap<E> owner) {
            this.key = Objects.requireNonNull(key);
            this.index = index;
            this.owner = owner;
            owner.tracker.addAllocation();
        }

        /**
         * Returns the current key associated with this node.
         *
         * @return the key value
         */
        public E getKey() {
            owner.tracker.addArrayAccess();
            return key;
        }

        @Override
        public String toString() {
            return "HeapNode[key=" + key + ", index=" + index + "]";
        }
    }

    private final ArrayList<HeapNode<T>> heap;
    private final PerformanceTracker tracker;

    /**
     * Constructs an empty MinHeap with its own performance tracker.
     */
    public MinHeap() {
        this(new PerformanceTracker());
    }

    /**
     * Constructs an empty MinHeap using the specified PerformanceTracker.
     *
     * @param tracker the tracker to record performance metrics
     * @throws NullPointerException if tracker is null
     */
    public MinHeap(PerformanceTracker tracker) {
        this.heap = new ArrayList<>();
        this.tracker = Objects.requireNonNull(tracker);
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return the current heap size
     */
    public int size() {
        tracker.addArrayAccess();
        return heap.size();
    }

    /**
     * Returns true if the heap contains no elements.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        tracker.addArrayAccess();
        return heap.isEmpty();
    }

    /**
     * Inserts a new element into the heap.
     *
     * @param key the element to insert
     * @return a handle to the inserted node for decreaseKey use
     * @throws NullPointerException if key is null
     */
    public HeapNode<T> insert(T key) {
        Objects.requireNonNull(key, "key must not be null");
        HeapNode<T> node = new HeapNode<>(key, heap.size(), this);
        heap.add(node);
        tracker.addArrayAccess();
        siftUp(heap.size() - 1);
        return node;
    }

    /**
     * Builds a heap from a collection of keys using bottom-up heapify.
     *
     * @param keys the elements to insert
     * @return handles for each element in insertion order
     * @throws NullPointerException if keys or any element is null
     */
    public List<HeapNode<T>> buildHeap(Collection<T> keys) {
        Objects.requireNonNull(keys);
        heap.clear();
        tracker.addArrayAccess();
        List<HeapNode<T>> handles = new ArrayList<>();
        for (T k : keys) {
            HeapNode<T> node = new HeapNode<>(Objects.requireNonNull(k), heap.size(), this);
            heap.add(node);
            handles.add(node);
            tracker.addArrayAccess();
        }
        for (int i = parent(heap.size() - 1); i >= 0; i--) {
            siftDown(i);
        }
        return handles;
    }

    /**
     * Returns the minimum element without removing it.
     *
     * @return the smallest element
     * @throws IllegalStateException if the heap is empty
     */
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        tracker.addArrayAccess();
        return heap.get(0).key;
    }

    /**
     * Removes and returns the minimum element.
     *
     * @return the smallest element
     * @throws IllegalStateException if the heap is empty
     */
    public T extractMin() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        tracker.addArrayAccess();
        HeapNode<T> minNode = heap.get(0);
        T result = minNode.key;
        int last = heap.size() - 1;
        if (last == 0) {
            heap.remove(last);
            tracker.addArrayAccess();
            tracker.addSwap();
            return result;
        }
        swapNodes(0, last);
        heap.remove(last);
        tracker.addArrayAccess();
        siftDown(0);
        return result;
    }

    /**
     * Decreases the key of the given node to a smaller value.
     * The new key must be strictly less than the current key.
     *
     * @param node the node whose key to decrease
     * @param newKey the new key value
     * @throws IllegalArgumentException if newKey is greater than current key
     * @throws NullPointerException if node or newKey is null
     */
    public void decreaseKey(HeapNode<T> node, T newKey) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(newKey);
        if (node.owner != this) throw new IllegalArgumentException("Node does not belong to this heap");
        tracker.addComparison();
        if (newKey.compareTo(node.key) > 0) {
            throw new IllegalArgumentException("New key is greater than current key");
        }
        node.key = newKey;
        tracker.addArrayAccess();
        siftUp(node.index);
    }

    /**
     * Combines two heaps into a new one, without modifying the originals.
     *
     * @param a the first heap
     * @param b the second heap
     * @param <E> the element type
     * @return a new heap containing all elements of both heaps
     * @throws NullPointerException if either heap is null
     */
    public static <E extends Comparable<? super E>> MinHeap<E> merge(MinHeap<E> a, MinHeap<E> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        PerformanceTracker newTracker = new PerformanceTracker();
        MinHeap<E> result = new MinHeap<>(newTracker);
        List<E> keys = new ArrayList<>(a.size() + b.size());
        for (HeapNode<E> n : a.heap) {
            keys.add(n.key);
            newTracker.addArrayAccess();
        }
        for (HeapNode<E> n : b.heap) {
            keys.add(n.key);
            newTracker.addArrayAccess();
        }
        result.buildHeap(keys);
        return result;
    }

    private void siftUp(int idx) {
        int i = idx;
        while (i > 0) {
            int p = parent(i);
            tracker.addArrayAccess();
            tracker.addArrayAccess();
            tracker.addComparison();
            if (heap.get(i).key.compareTo(heap.get(p).key) < 0) {
                swapNodes(i, p);
                i = p;
            } else break;
        }
    }

    private void siftDown(int idx) {
        int i = idx;
        int n = heap.size();
        while (true) {
            int l = left(i);
            int r = right(i);
            int smallest = i;
            if (l < n) {
                tracker.addArrayAccess();
                tracker.addArrayAccess();
                tracker.addComparison();
                if (heap.get(l).key.compareTo(heap.get(smallest).key) < 0) smallest = l;
            }
            if (r < n) {
                tracker.addArrayAccess();
                tracker.addArrayAccess();
                tracker.addComparison();
                if (heap.get(r).key.compareTo(heap.get(smallest).key) < 0) smallest = r;
            }
            if (smallest != i) {
                swapNodes(i, smallest);
                i = smallest;
            } else break;
        }
    }

    private void swapNodes(int i, int j) {
        if (i == j) return;
        HeapNode<T> tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
        heap.get(i).index = i;
        heap.get(j).index = j;
        tracker.addSwap();
        tracker.addArrayAccess();
        tracker.addArrayAccess();
    }

    private static int parent(int i) { return (i - 1) >>> 1; }
    private static int left(int i) { return (i << 1) + 1; }
    private static int right(int i) { return (i << 1) + 2; }

    /**
     * Removes all elements and returns them in ascending order.
     * Useful for validation and benchmarking.
     *
     * @return a sorted list of all heap elements
     */
    public List<T> drainToList() {
        List<T> out = new ArrayList<>();
        while (!isEmpty()) {
            out.add(extractMin());
        }
        return out;
    }

    /**
     * Returns the performance tracker used by this heap.
     *
     * @return the associated PerformanceTracker
     */
    public PerformanceTracker getTracker() {
        return tracker;
    }
}
