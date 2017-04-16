# MatrixToolbox2
Matrix calculator version 2

# Starting the program
```sh
$ make
mkdir out
javac -classpath out/ -d out/ src/*
java -classpath out/ Main
-------------------------------------------------
Main menu:
<a> Add matrix
<r> Remove matrix
<s> Select matrix
<v> View matrix list
<e> Exit
Input:
```

# Adding a matrix
```sh
Input: a
-------------------------------------------------
Add a Matrix to the list
Enter (Row Column) E.g. 3 3: 2 4
Enter matrix data (separate data with a space):
1 2 3 4 5 6 7 8
Include b Column? (y/n) y
Enter b Column data (separate data with a space):
9 10
-------------------------------------------------
Matrix successfully added to index 1
```

# Matrix Menu
```sh
*************************************************
Matrix Menu: Action for Matrix 1
<m> Display the matrix
<s> Get RREF
<d> Get determinant
<i> Get inverse
<r> Get rank
<t> Get transpose
<mm> Multiply
<ma> Add
<e> Back to Main menu
Input:
*************************************************
```
