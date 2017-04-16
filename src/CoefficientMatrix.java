import java.util.Arrays;

public class CoefficientMatrix {
    int Rows = 0;
    int Cols = 0;
    double[][] data;

    public CoefficientMatrix(double[][] array, int r, int c){
        this.Rows = r;
        this.Cols = c;
        this.data = array;
    }

    //*************************************************************
    // General Matrix functions

    public double[][] deepCopy(double[][] src) {
        if (src == null) return null;

        double[][] newCopy = new double[Rows][Cols];

        for (int i = 0; i < Rows; ++i) {
            newCopy[i] = Arrays.copyOf(src[i], Cols);
        }
        return newCopy;
    }

    public boolean checkZeroRow(int r1) {
        for (int j = 0; j < Cols; ++j) {
            if (data[r1][j] != 0) return false;
        }
        return true;
    }

    public boolean checkZeroCol(int r1) {
        for (int j = 0; j < Rows; ++j) {
            if (data[j][r1] != 0) return false;
        }
        return true;
    }

    public boolean doubleCompare(double a, double b) {
        if (Math.abs(a - b) <= 0.0001) return true;
        else return false;
    }

    //*************************************************************
    // Coefficient Matrix Display

    public void matrixDisplay() {
        System.out.print("---Data---");
        for (int i = 1; i < Cols; ++i) {
            System.out.print("-------");
        }
        System.out.println();
        //*******************************************************
        for (int i = 0; i < this.Rows ; ++i) {
            System.out.print("| ");
            for (int j = 0; j < this.Cols ; ++j) {
                System.out.printf("%6.2f ", this.data[i][j]);
            }
            System.out.println(" |");
        }
        //*******************************************************
        for (int i = 0; i < Cols; ++i) {
            System.out.print("--------");
        }
        System.out.println();
    }

    // End of Matrix Display methods
    //*************************************************************
    //*************************************************************
    // Matrix Operations

    public CoefficientMatrix matrixMultiplication(CoefficientMatrix other) {
        if (this.Cols != other.Rows) {
            System.out.println("Unable to perform multiplication of matrices");
            System.out.println("Dimension incompatible");
            return null;
        }

        double[][] product = new double[this.Rows][other.Cols];

        int n_r = this.Rows;
        int n_c = other.Cols;

        for (int i = 0; i < n_r; ++i) {
            for (int j = 0; j < n_c; ++j) {
                for (int k = 0; k < this.Cols; ++k) {
                    product[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }
        return new CoefficientMatrix(product, n_r, n_c);
    }

    public CoefficientMatrix matrixAddition(CoefficientMatrix other) {
        if (this.Cols != other.Cols || this.Rows != other.Rows) {
            System.out.println("Unable to add matrices");
            System.out.println("Dimension incompatible");
            return null;
        }

        double [][] sum = new double[this.Rows][this.Cols];

        int n_r = this.Rows;
        int n_c = this.Cols;

        for (int i = 0; i < n_r; ++i) {
            for (int j = 0; j < n_c; ++j) {
                sum[i][j] = this.data[i][j] + other.data[i][j];
            }
        }
        return new CoefficientMatrix(sum, n_r, n_c);
    }

    public void roSwitch(int r1, int r2) {
        double[] temp = this.data[r1];
        this.data[r1] = this.data[r2];
        this.data[r2] = temp;
    }

    public void roScale(int r1, double scalar) {
        for (int i = 0; i < this.Cols; ++i) {
            this.data[r1][i] *= scalar;
        }
    }

    public void roAdd(int r1, int r2, double scalar) {
        for (int i = 0; i < this.Cols; ++i) {
            this.data[r1][i] += scalar * this.data[r2][i];
        }
    }

    public void checkNegativeZero() {
        for (int i = 0; i < this.Rows; ++i) {
            for (int j = 0; j < this.Cols; ++j) {
                this.data[i][j] += 0.0;
            }
        }
    }

    public void endRREF() {
        checkNegativeZero();
    }

    // End of Matrix Operations
    //******************************************************************
    //******************************************************************
    // Row Reducing Algorithms

    public int gaussJordanElimination() {
        int detScale = 1;
        int min = Math.min(Rows, Cols);
        for (int k = 0; k < min; ++k) {
            int i_max = k;
            for (int i = k + 1; i < Rows; ++i) {
                double eh = Math.abs(data[i][k]);
                double be = Math.abs(data[i_max][k]);
                if (eh > be) i_max = i;
            }

            if (data[i_max][k] == 0) {
                return detScale;
            }

            if (k != i_max) detScale *= -1;
            roSwitch(k, i_max);

            for (int i = k + 1; i < Rows; ++i) {
                double temp = data[i][k] / data[k][k];
                for (int j = k + 1; j < Cols; ++j) {
                    data[i][j] -= data[k][j] * temp;
                }
                data[i][k] = 0;
            }
        }
        return detScale;
    }

    public void rrefGaussJordanElimination() {
        int rowCount = this.Rows;
        int colCount = this.Cols;

        int lead = 0;

        for (int r = 0; r < rowCount; ++r) {
            if (lead >= colCount) {
                endRREF();
                return;
            }
            int i = r;
            while (data[i][lead] == 0) {
                ++i;
                if (i == rowCount) {
                    i = r;
                    ++lead;
                    if (colCount == lead) {
                        endRREF();
                        return;
                    }
                }
            }
            roSwitch(i, r);
            if (data[r][lead] != 0) roScale(r, 1 / data[r][lead]);

            for (int j = 0; j < rowCount; ++j) {
                if (j == r) continue;
                double val = -data[j][lead];
                roAdd(j, r, val);
            }
            ++lead;
        }
        endRREF();
        return;
    }

    //
    //******************************************************************
    //

    public TransposeMatrix getTranspose() {
        double[][] tData = deepCopy(data);

        TransposeMatrix t = new TransposeMatrix(tData, Rows, Cols);
        t.transpose();
        return t;
    }

    public InverseMatrix getInverse() {
        double[][] iData = deepCopy(data);

        InverseMatrix i = new InverseMatrix(iData, Rows, Cols);
        i.inverse();
        return i;
    }

    public Determinant getDeterminant() {
        double[][] dData = deepCopy(data);

        Determinant d = new Determinant(dData, Rows, Cols);
        d.findDeterminant();
        return d;
    }

    public Rank getRank() {
        double[][] rData = deepCopy(data);

        Rank r = new Rank(rData, Rows, Cols);
        r.findRank();
        return r;
    }
}
