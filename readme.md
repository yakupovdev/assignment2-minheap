# 🧮 Assignment 2 — MinHeap Algorithmic Analysis

**Pair 4 — Student A (Min-Heap Implementation)**  
**Author:** *Vyacheslav Yakupov*  
**Group:** *SE-2438*

---

## 🧠 Overview

This project implements a **binary MinHeap** in Java with advanced features and complete asymptotic and empirical analysis.

The MinHeap supports:

- `insert()`
- `extractMin()`
- `peek()`
- `decreaseKey()` using `HeapNode` handles
- `merge()` of two heaps
- `buildHeap()` from a collection

Performance metrics — **comparisons**, **swaps**, **array accesses**, and **memory allocations** — are recorded using a dedicated `PerformanceTracker`.

A CLI benchmark runner generates CSV data and performance plots for empirical validation.

---

## 📁 Repository Structure
```
assignment2-minheap/
├── src/main/java/
│ ├── algorithms/
│ │ └── MinHeap.java
│ ├── metrics/
│ │ └── PerformanceTracker.java
│ └── cli/
│ └── BenchmarkRunner.java
├── src/test/java/
│ └── algorithms/
│ └── MinHeapTest.java
├── docs/
│ ├── analysis-report.pdf
│ ├── summary.md
│ └── performance-plots/
│   └── analysis-tests.csv
├── benchmarks.csv
├── .gitignore
├── README.md
└── pom.xml
```

---

## ⚙️ Features

### 🧩 MinHeap Core

- **Array-backed** structure for efficient memory layout  
- Supports **`decreaseKey`** in *O(log n)* via handle references  
- **Merge** operation runs in *O(n + m)*  
- **Bottom-up heapify** allows linear-time heap construction  

### 📊 Performance Tracking

- Monitors:
  - Comparisons  
  - Swaps  
  - Array accesses  
  - Memory allocations  
- Results exported to CSV for performance analysis  

### 💻 CLI Benchmark Tool
- Run with `java -jar target/assignment2-minheap-1.0.0.jar [sizes...]`
- Supports multiple input distributions:
    - Random
    - Sorted
    - Reverse sorted
    - Nearly sorted
- Generates `benchmarks.csv` with detailed operation metrics.

---

## ✅Unit Testing
Unit tests cover:

- Empty heap
- Single element heap
- Duplicates
- Sorted and reverse-sorted inputs
- Random input property-based testing
- Decrease key
- Merge operation

Tests ensure correctness, stability, and edge-case handling.

### ▶️Run tests with Maven
```
mvn clean test
```

---

## 📈Performance and Complexity

### ⏱️Time Complexity
| Operation    | Best Case | Average Case | Worst Case |
|--------------|-----------|--------------|------------|
| Insert       | Ω(1)      | Θ(log n)     | O(log n)   |
| Extract Min  | Ω(1)      | Θ(log n)     | O(log n)   |
| Decrease Key | Ω(1)      | Θ(log n)     | O(log n)   |
| Merge        | Ω(n + m)  | Θ(n + m)     | O(n + m)   |
| Build Heap   | Ω(n)      | Θ(n)         | O(n)       |

### 💾Space Complexity
- In-place **array-backed storage** for heap nodes.
- Extra references for `HeapNode` handles to support `decreaseKey`.
- Overall space complexity: **O(n)**.

### 📐Empirical Validation
- Benchmarks conducted for **n = 100, 1000, 10000, 100000**.
- Metrics recorded:
    - **Elapsed time**
    - **Comparisons**
    - **Swaps**
    - **Array accesses**
    - **Memory allocations**
    - **Recursive calls**
- Results output to `benchmarks.csv`.
- Graphical plots in `docs/performance-plots/` compare measured performance with theoretical expectations.

### 🔍Key Observations
- **Random input** shows expected O(log n) behavior for inserts and extractMin.
- **Sorted input** requires fewer swaps due to initial heap order.
- **Reverse-sorted input** triggers maximum swaps and comparisons.
- **Nearly-sorted input** confirms that minor disorder does not significantly impact performance.
- `decreaseKey` efficiently updates heap without full reheapification.
- `merge` correctly combines heaps in linear time, validated by tests and benchmarks.

---

## 💡Usage Example
### Java CLI
```
# Run default benchmark
java -jar target/assignment2-minheap-1.0.0.jar

# Run custom sizes
java -jar target/assignment2-minheap-1.0.0.jar 100 1000 10000
```

### Example Output (benchmarks.csv)
```
n,distribution,time(ms),comparisons,swaps,arrayAccesses,allocations,recursiveCalls
100,random,1,1031,584,3730,100,633
100,sorted,0,944,517,3422,100,566
100,reverse,1,1081,641,3944,100,690
100,nearly-sorted,1,958,524,3464,100,573
1000,random,1,16863,9081,56888,1000,9580
1000,sorted,1,15965,8317,53564,1000,8816
1000,reverse,1,17583,9709,59584,1000,10208
1000,nearly-sorted,1,16003,8339,53684,1000,8838
10000,random,9,235282,124147,768858,10000,129146
10000,sorted,4,226682,116697,736758,10000,121696
10000,reverse,4,244460,131957,802834,10000,136956
10000,nearly-sorted,4,226764,116747,737022,10000,121746
100000,random,78,3019663,1575124,9689574,100000,1625123
100000,sorted,52,2926640,1497435,9348150,100000,1547434
100000,reverse,30,3112517,1650855,10026744,100000,1700854
100000,nearly-sorted,34,2926890,1497507,9348794,100000,1547506
```

---

## 📄 Reports

### 🧾 Individual Analysis Report
**File:** [docs/analysis-report.pdf](./docs/analysis-report.pdf)  
This document contains a detailed **peer analysis** of the partner’s algorithmic implementation.  

---

### 🤝 Joint Summary Report
**File:** [docs/summary.md](./docs/summary.md)  
This file summarizes the **joint cross-review** between both partners’ implementations.  

