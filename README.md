[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/J_c8sizy)
# MassiveMotion
Project 2 – Massive Motion (CS 245)
This project creates a small animation in Java using Swing.
The program reads settings from a configuration file and draws a star and comets that move across the screen.
We were required to use our own List implementations instead of Java’s built-in lists.
How to Run
1. Compile all files
javac *.java
2. Run with the config file
java MassiveMotion MassiveMotion.txt
The constructor can't take parameters (because of the template),
so the program uses a system property to tell the constructor which config file to load.
Example:
System.setProperty("cfg", args[0]);
What the Program Does
Loads animation settings (window size, star position, spawn probability, velocities, etc.)
Creates one star (red)
Randomly creates comets from the edges of the window
Comets move in random non-zero directions
Comets get removed if they leave the screen
Everything is drawn using Graphics.fillOval()
Animation runs using a Swing Timer
List Implementations Used
The project requires four custom List implementations:
ArrayList – array-based
LinkedList – singly linked
DoublyLinkedList – doubly linked
DummyHeadLinkedList – with a dummy head node
No Java library lists are used.
The config file chooses the list type:
list = arraylist
list = single
list = double
list = dummyhead
Files Included
MassiveMotion.java
List.java
ArrayList.java
LinkedList.java
DoublyLinkedList.java
DummyHeadLinkedList.java
ListFactory.java
MassiveMotion.txt
Notes
The “sun moving to the right” problem was fixed by setting the star velocity to 0 in the config file.
Javadoc comments were added to the public methods to meet the project’s documentation requirement.
The animation should run smoothly on the default config settings.
