# Project 2: NonDeterministic Finite Automata

* Author: Brian Wu ,Max Ma
* Class: CS361 Section 1
* Semester: Fall 2024 

## Overview
This Java application displays an fully functioning NonDeterministicDeterminstic Finite Automata that implements fa.dfa.NFAInterface which extends fa.dfa.FAInterface, then we apply test-based development using JUnit test cases to apply 18 given test cases.


## Reflection

- What worked well and what was a struggle? 
  spliting code and reusing code from P1 worked well and dubugging and tracing through different tests was a struggle.

- What concepts still aren't quite clear?
understanding tracing maxState in accepts and logic itself overall
  
- What techniques did you use to make your code easy to debug and modify?
writing down problems and tracing through test cases and using intelliJ and commenting code undersatnding how smaller part of code work.
  
- What would you change about your design process?
one of ways that's helping is to set up static variable maxState in the beginning and by using accept method does processing and to store in that value.

- If you could go back in time, what would you tell yourself about doing this project?
  I would tell myself to start early for this project, and we would have enough time to debugged what's most time consuming for us in maxcopies method 

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java
```
Run the compiled class with the command:
```
$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar
org.junit.runner.JUnitCore test.nfa.NFATest
```

## Sources used

- https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html 
