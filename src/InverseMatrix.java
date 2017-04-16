public class InverseMatrix extends CoefficientMatrix {
    boolean invertible;

    public InverseMatrix (double[][] data, int r, int c) {
        super(data, r ,c);
    }

    //*******************************************************************

    @Override
    public void matrixDisplay() {
        if (invertible == false) {
            System.out.println("---Data------------------");
            System.out.println("Matrix is not invertible");
            System.out.println("-------------------------");
        } else {
            super.matrixDisplay();
        }
    }

    //*******************************************************************
    // bIdentity

    public boolean checkIdentity() {
        for (int i = 0; i < Rows; ++i) {
            for (int j = 0; j < Cols; ++j) {
                if (i == j) {
                    if (data[i][j] != 1) {
                        return false;
                    }
                } else {
                    if (data[i][j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //*******************************************************************
    // Calculate inverse matrix

    public void inverse() {
        if (Rows != Cols) {
            invertible = false;
            return;
        }

        double[][] bIdentity = new double[Rows][Cols];
        for (int i = 0; i < Rows; ++i) {
            for (int j = 0; j < Cols; ++j) {
                if (i == j) {
                    bIdentity[i][j] = 1;
                } else {
                    bIdentity[i][j] = 0;
                }
            }
        }

        AugmentedMatrix b = new AugmentedMatrix(data, bIdentity, Rows, Cols);
        b.rrefGaussJordanElimination();
        invertible = checkIdentity();
        data = b.bMatrix;
        checkNegativeZero();
        return;
    }
}
