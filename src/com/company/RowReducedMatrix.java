package com.company;

public class RowReducedMatrix extends AugmentedMatrix {
    String[] soln;
    boolean consistent = true;

    public RowReducedMatrix(double[][] data, double[][] b, int r, int c) {
        super(data, b, r, c);
    }

    //******************************************************************

    public void solutionDisplay() {
        System.out.println("---Soln---------------------------");
        if (consistent == false) {
            System.out.println("Matrix is Inconsistent");
        } else {
            for (int i = 0; i < Cols; ++i) {
                if (soln[i] == null) continue;
                else System.out.println(soln[i]);
            }
        }
        System.out.println("----------------------------------");
    }

    //******************************************************************
    // Helper Functions

    public boolean checkConsistency() {
        for (int i = 0; i < Rows; ++i) {
            if (checkZeroRow(i)) {
                if (!(doubleCompare(bMatrix[i][0], 0))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int leadingZeroIndex(int r1) {
        for (int i = 0; i < Cols; ++i) {
            if (data[r1][i] == 1) return i;
        }
        return -1;
    }

    public String freeVarToString(double var, int pos) {
        String addon = "";
        if (var > 0) {
            addon += " - " + Math.abs(var);
        } else {
            addon += " + " + Math.abs(var);
        }
        addon += "(t" + pos + ")";
        return addon;
    }

    //******************************************************************
    // Find Solutions

    public void findSolutions() {
        rrefGaussJordanElimination();
        if (!checkConsistency()) {
            consistent = false;
            return;
        }

        soln = new String[Cols];

        int[] track = new int[Cols];
        for (int k = 0; k < Cols; ++k) {
            track[k] = 1;
        }

        for (int i = 0; i < Rows; ++i) {

            int lead = leadingZeroIndex(i);
            if (lead == -1) {
                continue;
            }
            track[lead] = 0;

            String solution = "x"+(lead+1)+" = "+bMatrix[i][0];

            for (int j = lead + 1; j < Cols; ++j) {
                if (!(doubleCompare(data[i][j], 0))) {
                    solution += freeVarToString(data[i][j], (j+1));
                }
            }
            soln[lead] = solution;
        }

        for (int l = 0; l < Cols; ++l) {
            if (track[l] == 1 && !checkZeroCol(l)) {
                soln[l] = "x"+(l+1)+" = t"+(l+1);
            }
        }
    }

    //******************************************************************
}
