# Cyber Defense Duel - Gemini Context

## Project Overview
- **Goal:** 1v1 educational multiplayer game where players defend their servers against cyber attacks (DDoS, Malware, etc.).
- **Course:** CE-1103 Algorithms and Data Structures I (TEC).
- **Technologies:** Java, JavaFX, TCP Sockets, JSON (Gson/Jackson).

## Core Mandates (from CE1103-S1-2026-Proyecto1.pdf)
- **NO Java Collections:** Usage of `ArrayList`, `LinkedList` (from `java.util`), `HashMap`, `Stack`, `Queue`, etc., is strictly forbidden.
- **Custom Data Structures:** All data structures MUST be implemented manually and located in the `estruc_datos` package.
- **Persistence:** Use `database.json` for user data and statistics.
- **Client-Server:** The game must run on different machines (Server and 2 Clients).

## Coding Preferences & Style
- **Naming Conventions:**
  - **Methods:** A mix of `snake_case` (e.g., `get_instance()`, `get_colider()`) and `camelCase` (e.g., `getHealth()`, `mostrarPantallaLogin()`) is used. Adhere to the style of the file being edited.
  - **Variables:** Primarily `camelCase` (e.g., `avatarElegido`, `lastTime`), but some `snake_case` or mixed names may appear (e.g., `free_bullets`, `balaMala`).
- **Language:**
  - Code (Classes, Methods, Variables) is mostly in English.
  - Comments and UI labels/messages are a mix of Spanish and English.
- **UI Implementation:** Manual JavaFX construction (no FXML). Focus on modern, clean aesthetics as seen in `GameIU.java`.
- **Patterns:** Use the Singleton pattern for global entities like `Player` and `GameIU`.
- **Formatting:** Maintain existing indentation (check if the file uses Tabs or Spaces before editing).

## Consultant Workflow
- **Role:** Act as a consultant/peer programmer.
- **Directives:** Only suggest or perform code modifications when explicitly requested by the user.
- **Inquiries:** For general questions, provide analysis, advice, and strategy without modifying files.
- **Guidance:** Prioritize simplicity and adherence to the manual data structure constraint.

## Project Structure
- `src/Application/`: Main entry point, UI management, and high-level game flow.
- `src/estruc_datos/`: Manual implementations of List, Stack, Queue, etc.
- `src/Game/`: Game logic, entities (Player, Enemy, Bullet, GameObject).
- `src/network/`: Socket communication logic.
- `src/GameData/`: Persistence and shared data management.
