package snakenet;
import java.util.ArrayList;
import java.util.Random;

public class Matrix {
Double[][] matrix;

public void RandomMatrix(int rows, int cols){
        matrix = new Double[rows][cols];

        for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                        matrix[i][j] = Matrix.RandNorm();
                }
        }
}
public void EmptyMatrix(int rows, int cols){

        matrix = new Double[rows][cols];

        for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                        matrix[i][j] = 0.0;
                }
        }
}
public Matrix DotProduct(Matrix mat){

        int aRows = matrix.length;
        int aColumns = matrix[0].length;
        int bRows = mat.matrix.length;
        int bColumns = mat.matrix[0].length;

        if (aColumns != bRows) {
                throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
                for (int j = 0; j < bColumns; j++) {
                        C[i][j] = 0.00000;
                }
        }

        for (int i = 0; i < aRows; i++) { // aRow
                for (int j = 0; j < bColumns; j++) { // bColumn
                        for (int k = 0; k < aColumns; k++) { // aColumn
                                C[i][j] += matrix[i][k] * mat.matrix[k][j];
                        }
                }
        }

        Matrix product = new Matrix();
        product.matrix = C;
        return product;
}
public Matrix Sigmoid(){
        for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                        matrix[i][j] = sigmoid(matrix[i][j]);
                }
        }
        return this;
}
private static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
}
public static double RandNorm(){
        Random rng = new Random();
        return rng.nextGaussian();
}

public int Rows(){

        return this.matrix.length;
}
public int Cols(){

        return this.matrix[0].length;
}
}
