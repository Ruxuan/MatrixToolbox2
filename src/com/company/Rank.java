package com.company;

public class Rank extends CoefficientMatrix {
    int rank;

    public Rank(double[][] data, int r, int c) {
        super(data, r, c);
    }

    //********************************************************

    public void rankDisplay() {
        System.out.println("---Rank------------------------");
        System.out.println("Rank: " + rank);
        System.out.println("-------------------------------");
    }

    //********************************************************

    public void findRank() {
        rrefGaussJordanElimination();
        rank = Rows;
        for (int i = 0; i < Rows; ++i) {
            if (checkZeroRow(i)) --rank;
        }
        return;
    }
}
