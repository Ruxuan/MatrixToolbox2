public class AugmentedMatrix extends CoefficientMatrix{
    int bCol;
    double[][] bMatrix;

    public AugmentedMatrix(double[][] array, double[][] b, int r, int c) {
        super(array, r, c);
        this.bMatrix = b;
        bCol = b[0].length;
    }

    //**********************************************************************

    @Override
    public void matrixDisplay() {
        System.out.print("---Data---");
        for (int i = 0; i < Cols; ++i) {
            System.out.print("--------");
        }
        System.out.println();
        //*******************************************************
        for (int i = 0; i < this.Rows ; ++i) {
            System.out.print("| ");
            for (int j = 0; j < this.Cols ; ++j) {
                System.out.printf("%6.2f ", this.data[i][j]);
            }
            System.out.printf(" | %6.2f |\n", this.bMatrix[i][0]);
        }
        //*******************************************************
        System.out.print("----------");
        for (int i = 0; i < Cols; ++i) {
            System.out.print("--------");
        }
        System.out.println();
    }

    //**********************************************************************
    // Matrix Operations

    @Override
    public void roSwitch(int r1, int r2) {
        double[] temp = this.data[r1];
        this.data[r1] = this.data[r2];
        this.data[r2] = temp;

        double[] bTemp = this.bMatrix[r1];
        this.bMatrix[r1] = this.bMatrix[r2];
        this.bMatrix[r2] = bTemp;
    }

    @Override
    public void roScale(int r1, double scalar) {
        for (int i = 0; i < this.Cols; ++i) {
            this.data[r1][i] *= scalar;
        }
        for (int k = 0; k < bCol; ++k) {
            bMatrix[r1][k] *= scalar;
        }
    }

    @Override
    public void roAdd(int r1, int r2, double scalar) {
        for (int i = 0; i < this.Cols; ++i) {
            this.data[r1][i] += scalar * this.data[r2][i];
        }
        for (int k = 0; k < bCol; ++k) {
            bMatrix[r1][k] += bMatrix[r2][k] * scalar;
        }
    }

    @Override
    public void checkNegativeZero() {
        for (int i = 0; i < this.Rows; ++i) {
            for (int j = 0; j < this.Cols; ++j) {
                this.data[i][j] += 0.0;
            }
        }
        for (int i = 0; i < Rows; ++i) {
            for (int j = 0; j < bCol; ++j) {
                this.bMatrix[i][j] += 0.0;
            }
        }
    }

    // End of Matrix Operations
    //**********************************************************************
    //**********************************************************************
    // Row Reducing Algorithms

    public RowReducedMatrix getRREF() {
        double[][] rrefData = deepCopy(data);
        double[][] bData    = deepCopy(bMatrix);

        RowReducedMatrix rref = new RowReducedMatrix(rrefData, bData, Rows, Cols);
        rref.findSolutions();
        return rref;
    }
    //****************************************************************
}
