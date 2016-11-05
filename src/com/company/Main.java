// Matrix Toolbox Ver 2
// By Ru Li
// ruxuan.li@gmail.com
// CS student @ UWaterloo

package com.company;

import java.util.Scanner;

public class Main {
    static int count = 0;
    static int inRows = 0;
    static int inCols = 0;
    public static CoefficientMatrix[] matrixList = new CoefficientMatrix[10];
    static double [][] input_data;
    static double[][] input_aug;
    static Scanner in = new Scanner (System.in);

    // ****************************************************************************************************************
    // ****************************************************************************************************************

    public static void addMatrix() {
        if (count == 10) {
            System.out.println("No more room in the matrix list");
            return;
        }
        System.out.println("Add a Matrix to the list");
        System.out.print("Enter (Row Column) E.g. 3 3: ");
        String[] dimensionInput = (in.nextLine()).split("\\s+");
        inRows = Integer.parseInt(dimensionInput[0]);
        inCols = Integer.parseInt(dimensionInput[1]);

        System.out.println("Enter matrix data (separate data with a space): ");
        String[] dataInput = (in.nextLine()).split("\\s+");
        input_data = new double[inRows][inCols];
        for (int i = 0; i < inRows; ++i) {
            try {
                for (int j = 0; j < inCols; ++j) {
                    input_data[i][j] = Double.parseDouble(dataInput[i * inCols + j]);
                }
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error in reading information. Please try again.");
                addMatrix();
            }
        }

        System.out.print("Include b Column? (y/n) ");
        String command = in.nextLine();
        if (command.equals("y")) {
            input_aug = new double[inRows][1];
            System.out.println("Enter b Column data (separate data with a space):");
            String[] bInput = (in.nextLine()).split("\\s+");
            try {
                for (int i = 0; i < inRows; ++i) {
                    input_aug[i][0] = Double.parseDouble(bInput[i]);
                }
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error in reading information. Please try again.");
                addMatrix();
            }
        }

        if (command.equals("y")) matrixList[count] = new AugmentedMatrix(input_data, input_aug, inRows, inCols);
        else matrixList[count] = new CoefficientMatrix(input_data, inRows, inCols);
        ++count;
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Matrix successfully added to index "+(count-1));
        matrixMenu(count - 1);
    }

    public static void removeMatrix() {
        if (count == 0) {
            System.out.println("Error: Matrix list is empty");
            return;
        }
        System.out.println("Select index of desired matrix");
        System.out.print("Input: ");
        int select = Integer.parseInt(in.nextLine());
        if (matrixList[select] == null) {
            System.out.println("Selection is out of array bounds");
            removeMatrix();
        }
        matrixList[select] = null;
        --count;
        int len = matrixList.length;
        for (int i = 0; i < len; ++i) {
            if (matrixList[i] == null) {
                for (int j = i; j < len; ++j) {
                    if (j == len-1) {
                        matrixList[j] = null;
                    } else {
                        matrixList[j] = matrixList[j + 1];
                    }
                }
            }
        }
    }

    public static void exitMenu() {
        System.out.println("Now exiting program");
        System.out.println("----------------------------------------------------------------------");
    }

    public static void viewMatrix() {
        System.out.println("Matrix List:");
        int c = 0;
        for (int i = 0; i < 10; ++i) {
            if (matrixList[i] == null) {
                System.out.print(i + ". EMPTY ");
            } else {
                System.out.print(i + ". Matrix " + i + " ");
            }
            ++c;
            if (c == 2) {
                c = 0;
                System.out.println();
            }
        }
    }

    public static int selectMatrix(int ver) {
        if (count == 0) {
            System.out.println("Error: No matrix created yet");
            return -1;
        }
        System.out.println("Select index of desired matrix");
        System.out.print("Input: ");
        int select = Integer.parseInt(in.nextLine());
        if (matrixList[select] == null) {
            System.out.println("Selection is out of array bounds");
            selectMatrix(ver);
        }
        if (ver == 1) {
            matrixMenu(select);
        }
        return select;
    }

    public static void mainMenu() {
        System.out.println("Main menu:");
        System.out.println("<a> Add matrix");
        System.out.println("<r> Remove matrix");
        System.out.println("<s> Select matrix");
        System.out.println("<v> View matrix list");
        System.out.println("<e> Exit");
    }

    // *****************************************************************************************************************
    // *****************************************************************************************************************

    public static void MMenu(int index) {
        System.out.println("Matrix Menu: Action for Matrix " + index);
        System.out.println("<m> Display the matrix");
        System.out.println("<s> Get RREF");
        System.out.println("<d> Get determinant");
        System.out.println("<i> Get inverse");
        System.out.println("<r> Get rank");
        System.out.println("<t> Get transpose");
        System.out.println("<mm> Multiply");
        System.out.println("<ma> Add");
        System.out.println("<e> Back to Main menu");
    }

    public static void matrixMenu(int index) {
        CoefficientMatrix selectM = matrixList[index];
        String command = null;
        while (true) {
            System.out.println("**********************************************************************");
            MMenu(index);

            System.out.print("Input: ");
            command = in.nextLine();

            System.out.println("**********************************************************************");

            if (command.equals("m")) {
                selectM.matrixDisplay();
            } else if (command.equals("s")) {
                if (selectM.getClass().getSimpleName().equals("AugmentedMatrix")) {
                    RowReducedMatrix a = ((AugmentedMatrix) selectM).getRREF();
                    a.matrixDisplay();
                    a.solutionDisplay();
                } else {
                    double[][] nDat = selectM.deepCopy(selectM.data);
                    CoefficientMatrix a = new CoefficientMatrix(nDat, selectM.Rows, selectM.Cols);
                    a.rrefGaussJordanElimination();
                    a.matrixDisplay();
                }
            } else if (command.equals("d")) {
                Determinant a = selectM.getDeterminant();
                a.determinantDisplay();
            } else if (command.equals("i")) {
                InverseMatrix a = selectM.getInverse();
                a.matrixDisplay();
            } else if (command.equals("r")) {
                Rank a = selectM.getRank();
                a.rankDisplay();
            } else if (command.equals("t")) {
                TransposeMatrix a = selectM.getTranspose();
                a.matrixDisplay();
            } else if (command.equals("e")) {
                System.out.println("Now returning to main menu");
                System.out.println("**********************************************************************");
                break;
            } else if (command.equals("mm")) {
                int select = selectMatrix(2);
                CoefficientMatrix a = selectM.matrixMultiplication(matrixList[select]);
                System.out.println("**********************************************************************");
                System.out.println("Product Matrix");
                a.matrixDisplay();
            } else if (command.equals("ma")) {
                int select = selectMatrix(2);
                CoefficientMatrix a = selectM.matrixAddition(matrixList[select]);
                System.out.println("**********************************************************************");
                System.out.println("Sum Matrix");
                a.matrixDisplay();
            } else {
                System.out.println("Command not recognized. Please try again.");
            }
        }
    }

    // *****************************************************************************************************************
    // *****************************************************************************************************************

    public static void main(String[] args) {
        String command = null;
        while (true) {
            System.out.println("----------------------------------------------------------------------");
            mainMenu();

            System.out.print("Input: ");
            command = in.nextLine();

            System.out.println("----------------------------------------------------------------------");

            if (command.equals("a")) {
                addMatrix();
            } else if (command.equals("e")) {
                exitMenu();
                break;
            } else if (command.equals("v")) {
                viewMatrix();
            } else if (command.equals("s")) {
                selectMatrix(1);
            } else if (command.equals("r")) {
                removeMatrix();
            } else {
                System.out.println("Command not recognized. Please try again.");
            }
            System.out.println("----------------------------------------------------------------------");
        }
    }
}
