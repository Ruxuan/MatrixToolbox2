public class Determinant extends CoefficientMatrix {
    double det;
    boolean detExist;

    public Determinant(double[][] data, int r, int c) {
        super(data, r ,c);
    }

    //*******************************************************************

    public void determinantDisplay() {
        System.out.println("---Determinant-----------------");
        if (detExist == false) {
            System.out.println("The determinant cannot be found");
        } else {
            System.out.println("Determinant: " + det);
        }
        System.out.println("-------------------------------");
    }

    //*******************************************************************

    public void findDeterminant() {
        if (Rows != Cols) {
            detExist = false;
            return;
        }
        detExist = true;
        det = gaussJordanElimination();;
        for (int i = 0; i < Rows; ++i) det *= data[i][i];
        det += 0.0;
        return;
    }
}
