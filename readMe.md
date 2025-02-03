# COBOL to Java Compiler 🚀

## Overview
This project is a **COBOL to Java Compiler**, capable of parsing COBOL source code, generating an **Abstract Syntax Tree (AST)**, and converting it into **Java source code**. It supports **embedded SQL (JDBC)** and includes a **Graphical User Interface (GUI)**.

## Features ✅
- **Lexical Analysis (Tokenization)**: Converts COBOL source into tokens.
- **Abstract Syntax Tree (AST) Parsing**: Analyzes COBOL syntax.
- **Java Code Generation**: Converts COBOL AST to Java source code.
- **Support for SQL Queries (`EXEC SQL`)**: Uses Java JDBC.
- **COBOL Statements Support**:
    - `DISPLAY` (Prints output)
    - `MOVE` (Assigns values to variables)
    - `STOP RUN` (Graceful exit)
    - `WORKING-STORAGE` (Variable declarations)
    - `PERFORM` (Loops execution)
    - `GOTO` (Jumping between labels)
    - `SEARCH` (Sequential and Binary Search)
    - `SORT` (Array Sorting)
    - `MERGE` (Combining Arrays)
    - `CALL` (Calling Subroutines)
- **Debugging Mode**: Enables tracing execution.
- **Graphical User Interface (GUI)**: Allows users to input COBOL code, compile, and view Java output.
- **Unit Testing for Validation**.

## How to Run 🏃‍♂️
### Requirements:
- **Java 11+**
- **Maven** (For dependency management)
- **MySQL Database** (For SQL feature testing)

### Steps to Run:
1. Clone the repository:
   ```sh
   git clone https://github.com/sekacorn/cobol-to-java-compiler.git
   ```
2. Navigate to the project directory:
   ```sh
   cd cobol-to-java-compiler
   ```
3. Compile and Run:
   ```sh
   mvn compile exec:java
   ```
4. Open the GUI and enter COBOL code to compile!

## Sample COBOL Code 📜
```cobol
DISPLAY "Hello, COBOL".
MOVE 100 TO VAR1.
STOP RUN.
```

### **Generated Java Code**
```java
public class Program {
    public static void main(String[] args) {
        System.out.println("Hello, COBOL");
        int VAR1 = 100;
        System.exit(0);
    }
}
```

## Limitations ❌
- **No Full COBOL Standard Support**:
    - Some COBOL **data types** are not fully mapped to Java.
    - No support for **COBOL Report Writer**.
- **No Dynamic Memory Management**:
    - COBOL’s `ALLOCATE` is not supported.
- **Limited File Handling**:
    - `OPEN`, `READ`, and `WRITE` statements have basic support but lack full COBOL file handling features.
- **Limited Error Handling**:
    - Exception handling is simplistic.
- **SQL Support Requires MySQL**:
    - Only supports **MySQL JDBC**; no support for other database engines.
- **No Multi-threading Support**:
    - Java code is single-threaded.

## Future Enhancements 🔥
- **Support for COBOL File Handling (`OPEN`, `READ`, `WRITE`)**
- **Improved Error Handling with Exception Management**
- **Extend SQL Compatibility (Support PostgreSQL, Oracle, etc.)**
- **Performance Optimizations using Multi-threading**
- **Deploy as a Web-Based Compiler**

---
📌 **Developed with ❤️ by COBOL & Java Enthusiasts** 📌

