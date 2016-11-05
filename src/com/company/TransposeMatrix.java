package com.company;

public class TransposeMatrix extends CoefficientMatrix {
    int transposeRows;
    int transposeCols;

    public TransposeMatrix(double[][] data, int r, int c) {
        super(data, r, c);
        transposeRows = Cols;
        transposeCols = Rows;
    }

    //********************************************************************

    @Override
    public void matrixDisplay() {
        System.out.println("---Transpose Data--------");
        for (int i = 0; i < transposeRows ; ++i) {
            System.out.print("| ");
            for (int j = 0; j < transposeCols ; ++j) {
                System.out.printf("%6.2f ", this.data[i][j]);
            }
            System.out.println(" |");
        }
        System.out.println("-------------------------");
    }

    //********************************************************************

    public void transpose() {
        double[][] temp = deepCopy(data);
        this.data = new double[transposeRows][transposeCols];
        for (int i = 0; i < transposeRows; i++) {
            for (int j = 0; j < transposeCols; j++) {
                data[i][j] = temp[j][i];
            }
        }
    }
}
