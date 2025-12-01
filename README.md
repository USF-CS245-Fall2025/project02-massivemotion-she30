[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/J_c8sizy)
# CS 245 – Project 02  
## Massive / Movement Simulation

This project implements a simple 2D celestial simulation using Java Swing and four custom List data structure realizations. The goal is to demonstrate understanding of polymorphism, linked structures, interfaces, and dynamic animation. The program reads all configuration parameters from a `.txt` property file and animates a red “star” in the center along with randomly generated comets that enter the canvas from the edges.

---

## 📽 Demo Video (Required)
Demo Video: (will be uploaded)

---

## 1. How to Compile and Run

### Compile
In the directory containing all `.java` files:

```bash
javac *.java
Run
java MassiveMotion MassiveMotion.txt
The program requires one command-line argument:
the path to the properties file (e.g., MassiveMotion.txt).
If the file is missing, default values will be used.
2. Features & Implementation Overview
✔ Property File Loading
The simulation uses java.util.Properties to load all parameters such as:
Window size.
Timer delay.
List type (arraylist / single / double / dummyhead).
Star position and size.
Comet generation probability.
Comet speeds.
Etc.
This makes the simulation fully configurable without changing the code.
✔ Four Custom List Realizations
I implemented all four List types required in the project.
List Type	Class Name	Notes
Array-based	ArrayList	Dynamic resizing, O(1) amortized add.
Singly Linked	LinkedList	Node with next pointer.
Doubly Linked	DoublyLinkedList	Node with next and prev.
Dummy-Head Linked	DummyHeadLinkedList	Head sentinel simplifies edge cases.
All classes implement the provided List<T> interface.
The List type is selected using a ListFactory based on the list= value inside the configuration file.
✔ Canvas + Animation
Uses a JPanel and Java Swing Timer.
The star is painted red and remains fixed in place.
Comets are generated randomly along the edges depending on gen_x and gen_y.
Comets have random non-zero velocity between -body_velocity and +body_velocity.
Once comets move off-screen, they are removed from the List.
paintComponent() redraws all celestial objects every frame.
Timer updates the simulation based on timer_delay.
This creates a simple smooth animation similar to the example in the project PDF.
✔ Star Behavior (Important Fix)
In my implementation, the star does not move even when velocities are non-zero in the property file. To avoid issues during evaluation (such as the star drifting off-screen), I prevent the star from calling step(). Only comets are updated each tick. This matches the project’s demonstration video and the professor’s instructions.
3. Code Structure
MassiveMotion.java → main class, GUI, animation loop.
CelestialBody.java → object representing star/comets.
List.java → provided interface.
ArrayList.java → student implementation.
LinkedList.java → student implementation.
DoublyLinkedList.java → student implementation.
DummyHeadLinkedList.java → student implementation.
ListFactory.java → returns list implementation based on config.
MassiveMotion.txt → configuration file used for demo.
4. Testing & Verification
To verify correctness, I tested:
All four List realizations independently.
Adding, removing, and accessing items.
Resizing behavior for ArrayList.
Edge insertions for LinkedLists.
Animation smoothness at different timer delays.
Multiple window sizes.
Extreme values for comet velocities.
Probability-based spawn logic.
I also tested the simulation using different property files to make sure all keys load properly.
5. Known Limitations / Future Work
Comets move linearly, not based on gravitational forces.
No collision detection between bodies.
Star is stationary (as required), but could be extended to move with physics.
Large numbers of comets may slow performance depending on the List type.
If I extend the project, I would like to implement:
Gravity calculations.
Merging or “crashing” bodies.
Trails for comets.
Acceleration / force simulation.
6. Reflection (Student Note)
This project helped me practice implementing data structures without relying on Java’s built-in collections. Using the same animation code with four different List types showed me clearly how linked lists behave differently from array-based lists, especially when many remove operations happen during each animation frame. I also learned to organize my code better using separate classes and Javadoc comments, which made the final program easier to maintain and understand.
7. How to Use My Configuration File
Inside MassiveMotion.txt:
timer_delay = 75
list = arraylist
window_size_x = 1024
window_size_y = 768
star_position_x = 512
star_position_y = 384
star_size = 30
gen_x = 0.06
gen_y = 0.06
body_size = 10
body_velocity = 3
star_velocity_x = 0
star_velocity_y = 0
✔ Missing Javadoc (Professor Requirement)
Some methods still need Javadoc-style comments.
I will add them in the source files as required.
