# Project 2: NonDeterministic Finite Automata

* Author: Brian Wu ,Max Ma
* Class: CS361 Section 1
* Semester: Fall 2024 

## Overview

This Java application displays an fully functioning NonDeterminstic Finite Automata that implements fa.dfa.DFAInterface which extends fa.dfa.FAInterface, then we apply test-based development using JUnit test cases to apply 18 given test cases.

## Reflection



- What worked well and what was a struggle? 
  
- What concepts still aren't quite clear?
  
 
- What techniques did you use to make your code easy to debug and modify?
  
- What would you change about your design process?
  
- If you could go back in time, what would you tell yourself about doing this project?
  

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java
```

Run the compiled class with the command:
```
$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar
org.junit.runner.JUnitCore test.dfa.DFATest
```

You will be prompted for integer values representing distance to the fire,
fire intensity, marshmallow burn resistance, and marshmallow sugar density.

## Sources used

- https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html 