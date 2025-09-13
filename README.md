# Simple Notepad

A simple notepad application built using Java Swing. It provides basic text editing functionalities such as New, Open, Save, Save As, Cut, Copy, Paste, Select All, and displays an About dialog.

## 👨‍💻 Author
Chamod Thejan  
Student ID: s16215 / 2021s18843

---

## 🖥️ Requirements

- **Java Version:** Java 8 or later
- No database required – this is a standalone desktop application.

---

## 🚀 Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/chamod774/simple-notepad-application.git
   cd simple-notepad
   ```
2. **Compile the Application**
Make sure Java is installed and added to your system’s PATH.
   ```bash
   javac SimpleNotepad.java
   ```
3. **Run the Application**
   ```bash
   java SimpleNotepad
   ```

## 📝 Features

 - Create, Open, Save, Save As for .txt files
 - Basic Edit operations: Cut, Copy, Paste, Select All
 - File change detection with "unsaved changes" warning
 - Simple About dialog with author information

## 📌 Assumptions & Notes

 - The application saves files as plain text only (.txt).
 - The GUI is built using Java Swing and does not require any external libraries.
 - File filters are applied to allow only .txt files when opening or saving.
 - No additional configuration or database is needed.
