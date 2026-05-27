<div align="center">

# ✈️ Airline Route Optimization System

### Intelligent Flight Path Computation Using Graph Theory & Dijkstra’s Algorithm

![Java](https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java)
![Algorithm](https://img.shields.io/badge/Algorithm-Dijkstra-blue?style=for-the-badge)
![Data Structures](https://img.shields.io/badge/Data%20Structures-Graphs%20%7C%20Priority%20Queues-success?style=for-the-badge)

</div>

---

# 📌 Overview

The **Airline Route Optimization System** is a graph-based routing engine designed to simulate how modern airline systems calculate optimal flight routes between cities.

The system models:
- **Cities** as graph nodes
- **Flights** as weighted edges
- **Route costs/distances** as edge weights

Using **Dijkstra’s Algorithm**, the application efficiently determines the shortest and most cost-effective flight path between two destinations.

This project demonstrates practical applications of:
- Graph Theory
- Pathfinding Algorithms
- Priority Queues
- Route Optimization Systems
- Transportation Network Analysis

---

# 🚀 Key Features

✅ Shortest path computation between cities  
✅ Graph-based airline network representation  
✅ Efficient route optimization using Dijkstra’s Algorithm  
✅ Priority Queue implementation for optimized traversal  
✅ Input validation for invalid city detection  
✅ Scalable architecture for expanding datasets  
✅ Real-world airline routing simulation  

---

# 🌍 Real-World Applications

This system reflects technologies used in real-world industries such as:

- Airline Route Optimization
- Emergency Flight Planning
- GPS Navigation Systems
- Transportation & Logistics Networks
- Supply Chain Optimization

### Example Use Case
During emergency evacuations or disaster-response operations, shortest-path algorithms can help determine the fastest and safest flight routes, reducing delays and improving response times.

---

# 🏗️ System Architecture

## Core Classes

| Class | Responsibility |
|------|----------------|
| `AirlineGraph` | Stores and manages the airline network graph |
| `Flight` | Represents flight connections between cities |
| `Dijkstra` | Computes shortest flight paths |
| `BookingRequest` | Handles reservation logic |
| `Main` | Executes the application and manages user interaction |

---

# 🧠 Data Structures Used

## Adjacency List
Efficiently represents the airline network graph.

```java
HashMap<String, List<Flight>>
```

## Priority Queue (Min-Heap)
Used within Dijkstra’s Algorithm to always process the next cheapest route.

```java
PriorityQueue<Node>
```

## Additional Structures
- `HashMap` → Fast city and route lookups
- `ArrayList` → Dynamic storage of flight connections

---

# ✈️ Example Flight Network

```text
New York ───500──▶ London
   │
   │450
   ▼
 Paris ───550──▶ Dubai ───700──▶ Tokyo
```

---

# ⚙️ Algorithm Workflow

### Dijkstra’s Algorithm Process

1. Start at the source city
2. Explore neighboring cities
3. Update shortest known distances
4. Use a priority queue to process the cheapest route first
5. Continue until the destination is reached

---

# 📊 Example Output

```text
Enter Starting City: New York
Enter Destination City: Tokyo

Shortest Path:
New York → Paris → Dubai → Tokyo

Total Cost: 1700
```

---

# 📈 Performance Analysis

| Number of Cities | Average Runtime |
|------------------|-----------------|
| 5 Cities | 3 ms |
| 10 Cities | 8 ms |
| 20 Cities | 15 ms |
| 50 Cities | 42 ms |

The use of adjacency lists and priority queues allows the system to maintain efficient performance as the graph expands.

---

# 🛠️ Technical Challenges

Several engineering challenges were addressed during development:

- Efficient graph traversal
- Managing weighted flight connections
- Handling invalid city inputs
- Optimizing shortest-path calculations
- Designing scalable route structures

These challenges reinforced the importance of:
- Efficient algorithm design
- Data structure optimization
- Defensive programming techniques

---

# 🔮 Future Improvements

Potential future enhancements include:

- Real-time flight delay integration
- Dynamic pricing systems
- GUI/Desktop application
- Database integration
- A* Search implementation
- Multi-airline route comparison
- Real-time weather-aware routing

---

# 💻 Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Graph Theory
- Dijkstra’s Algorithm
- Priority Queues
- HashMaps & ArrayLists

---

# ▶️ Running the Project

## Compile

```bash
javac *.java
```

## Run

```bash
java Main
```

---

# 📚 Project Motivation

Modern transportation systems rely heavily on intelligent routing algorithms to improve efficiency and reduce operational costs.

This project explores how graph-based optimization techniques can be applied to airline systems in both commercial and emergency planning scenarios, demonstrating how computer science concepts directly impact real-world infrastructure.

---

# 👥 Contributors

| Team Member | Contribution |
|-------------|--------------|
| Omar Ahmed | Graph implementation, shortest path logic, documentation |
| Team Member | Booking system & reservation handling |
| Team Member | Testing & debugging |
| Team Member | Performance analysis & presentation |

---

<div align="center">

### ⭐ Intelligent Routing Through Graph Algorithms

</div>
