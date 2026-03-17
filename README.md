# ♟️ Java Chess Game

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Design-1572B6?style=flat-square)
![Algorithms](https://img.shields.io/badge/Algorithms-F7DF1E?style=flat-square&logoColor=black)
> A backend chess engine allowing users to play a complete game with piece movement logic, rule validation, and persistent game state.
---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
- [Run Locally](#run-locally)
- [Architecture](#architecture)

---

## Overview

The **Java Chess Game** is a Java application that simulates a full chess game through a structured backend system.

Users can play a complete match where all moves are validated according to chess rules, with full game state management and persistence.

This project demonstrates strong understanding of **object-oriented programming, algorithmic logic, and system design.**


---

## Features

- Complete chessboard implementation
- Movement logic for all pieces (King, Queen, Rook, Bishop, Knight, Pawn)
- Rule validation (legal moves, captures, constraints)
- Turn-based gameplay
- Game state saving and loading (.ser)
- Modular and extensible architecture

---

## Project Structure

```
java-chess-game/
├── src/
│ ├── modele/
│ │ ├── jeu/ # Core game logic
│ │ │ ├── Board.java
│ │ │ ├── Game.java
│ │ │ └── Move.java
│ │ ├── pieces/ # Chess pieces implementation
│ │ │ ├── Piece.java
│ │ │ ├── King.java
│ │ │ ├── Queen.java
│ │ │ ├── Rook.java
│ │ │ ├── Bishop.java
│ │ │ ├── Knight.java
│ │ │ └── Pawn.java
│ │ └── utils/ # Helper / utility classes
│ │
│ └── Main.java # Entry point
│
├── exportToHTML/ # Generated/export files
├── .idea/ # IntelliJ configuration
├── partie.ser # Serialized game state
├── ChessGame.iml # Project configuration
└── README.md
```

---

## Technologies

| Technology | Role |
|------------|------|
| Java| Core programming languagee |
| OOP | System design and abstraction |
| Algorithms | Game logic and rule validation |
| Serialization (.ser) | Game state persistence |
|IntelliJ IDEA | Development environment |

---

## Run Locally


```bash
# Clone the repository

git clone https://github.com/anastasia638/java-chess-game.git
cd java-chess-game

# Open in **IntelliJ IDEA**(or any Java IDE)
# Run the application
Main.java
```

---


## Skills Demonstrated

- **Object-Oriented Programming:** abstraction, inheritance, encapsulation
- **Algorithms:** rule validation and movement logic
- **Software Design:** modular architecture
- **State Management:** serialization of objects
- **Debugging & Testing:** ensuring consistent game behavior

---

## Author

**Meriem Silmi** — Computer Science Student, France

[![GitHub](https://img.shields.io/badge/GitHub-anastasia638-black?style=flat-square&logo=github)](https://github.com/anastasia638)
[![Live Demo](https://img.shields.io/badge/Demo-View%20Site-green?style=flat-square)](https://anastasia638.github.io/java-chess-game)
