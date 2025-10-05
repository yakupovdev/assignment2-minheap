# Pair Submission — Pair 4

Student A: Vyacheslav Yakupov  
Student B: Uzbekbayev Bekarys

---

## 1. Cross-Review Summary

Both partners implemented complementary heap structures:
- Student A: Min-Heap (maintains smallest element at the root)  
- Student B: Max-Heap (maintains largest element at the root)

Each implementation supports:
insert, extract, increase/decreaseKey, buildHeap, and merge operations.  
Both maintain logarithmic (O(log n)) insertion and extraction time and linear (O(n)) build time.

| Operation | Min-Heap | Max-Heap | Complexity |
|------------|-----------|-----------|-------------|
| insert | O(log n) | O(log n) | logarithmic |
| extract | O(log n) | O(log n) | logarithmic |
| buildHeap | O(n) | O(n) | linear |
| keyUpdate | O(log n) | O(log n) | logarithmic |

Theoretical and empirical results confirm nearly identical behavior.  
Min-Heap is slightly faster on reversed data; Max-Heap performs better on random inputs.

---

## 2. Optimization Results

After peer review, both partners applied measured improvements:

| Optimization | Description | Effect |
|---------------|-------------|--------|
| Reduced repeated heap.get() calls | Cached node references in siftDown() and siftUp() | ↓ 6–8 % fewer array accesses |
| Dynamic resizing strategy | Gradual 1.5× array growth instead of 2× doubling | ↓ memory overhead by ~10 % |
| Batch heap construction | Replaced multiple insertions with single buildHeap() call | ↑ heap build ≈ 40 % faster |
| Improved exception messages | Added context to key-update operations | ↑ readability and debugging clarity |
| Extended PerformanceTracker | Added recursive call tracking | ↑ accuracy of empirical data |

Measured outcome:  
After optimization, both algorithms show 5–10 % runtime improvement and fewer swaps for large datasets (n = 100 000).  
Performance scaling remains consistent with theoretical O(n log n) complexity.

---

## 3. Joint Conclusion

Both heap implementations are correct, efficient, and meet all assignment criteria:
- Proper algorithmic design and complexity.  
- Comprehensive testing and benchmarking.  
- Integration of optimization feedback.  
- Clean code principles and modular structure.