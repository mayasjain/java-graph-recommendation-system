# Graph-Based Product Recommendation System

This project implements a graph-based product recommendation system in Java.

The system models products and categories as nodes in a directed graph and
uses relationships between them to generate product recommendations.

## Features

- Directed graph data structure
- Multiple recommendation strategies
- Recursive parser for recommendation queries
- Import database from text file
- Export graph in DOT format
- CLI interface

## Recommendation Strategies

S1 – Sibling products  
S2 – Successor products  
S3 – Predecessor products  

Strategies can also be combined using:

- UNION
- INTERSECTION

Example:

recommend UNION(S1 105,S3 107)

## Technologies

- Java 17
- Object-Oriented Design
- Graph algorithms
- Recursive descent parsing

## Example Command
