package com.jatrix.core;

import static com.jatrix.core.Matrices.add;
import static com.jatrix.core.Matrices.sub;

/**
 * This class implements the Strassen's algorithm.
 * It is designed to quickly multiply matrices larger than 32 x 32
 */
public final class StrassenProduct {

    private StrassenProduct() {}


    /**
     * Multiplies two matrices by Strassen's algorithm.
     * @param m1 the first Matrix operand to multiplying operation.
     * @param m2 the second Matrix operand to multiplying operation.
     * @return Matrix object - result of matrices product.
     */
    public static Matrix mul(Matrix m1, Matrix m2) {
        if (m1.getRowDimension() <= 32) {
            return Matrices.mul(m1, m2);
        }

        Matrix[] A = split(m1);
        Matrix[] B = split(m2);

        Matrix p1 = mul(add(A[0], A[3]), add(B[0], B[3]));
        Matrix p2 = mul(add(A[2], A[3]), B[0]);
        Matrix p3 = mul(A[0], sub(B[1], B[3]));
        Matrix p4 = mul(A[3], sub(B[2], B[0]));
        Matrix p5 = mul(add(A[0], A[1]), B[3]);
        Matrix p6 = mul(sub(A[2], A[0]), add(B[0], B[1]));
        Matrix p7 = mul(sub(A[1], A[3]), add(B[2], B[3]));

        Matrix c11 = add(sub(add(p1,p4), p5), p7);
        Matrix c12 = add(p3, p5);
        Matrix c21 = add(p2, p4);
        Matrix c22 = add(sub(add(p1, p3), p2), p6);

        return join(c11, c12, c21, c22);
    }


    /**
     * Splits parent matrix into four child matrices.
     * @param A parent Matrix object, which is divided by 4 child Matrix objects.
     * @return Matrix array of four child matrices.
     */
    public static Matrix[] split(Matrix A) {
        int size = A.getRowDimension() >> 1;
        Matrix a11 = new Matrix(size);
        Matrix a12 = new Matrix(size);
        Matrix a21 = new Matrix(size);
        Matrix a22 = new Matrix(size);

        for (int i = 0; i < size; i++) {
            System.arraycopy(A.getColumn(i), 0, a11.getColumn(i), 0, size);
            System.arraycopy(A.getColumn(i), size, a12.getColumn(i), 0, size);
            System.arraycopy(A.getColumn(size + i), 0, a21.getColumn(i), 0, size);
            System.arraycopy(A.getColumn(size + i), size, a22.getColumn(i), 0, size);
        }

        return new Matrix[]{a11, a12, a21, a22};
    }


    /**
     * Joins four child matrices into one parent Matrix object.
     * @param a11 the first child matrix
     * @param a12 the second child matrix
     * @param a21 the third child matrix
     * @param a22 the fourth child matrix
     * @return joinedMatrix parent Matrix object, joined of <code>a11</code>,<code>a12</code>,<code>a21</code>,
     * <code>a22</code>,
     */
    public static Matrix join(Matrix a11, Matrix a12, Matrix a21, Matrix a22) {
        int size = a11.getRowDimension();
        Matrix joinedMatrix = new Matrix(size << 1);

        for (int i = 0; i < size; i++) {
            System.arraycopy(a11.getColumn(i), 0, joinedMatrix.getColumn(i), 0, size);
            System.arraycopy(a12.getColumn(i), 0, joinedMatrix.getColumn(i), size, size);
            System.arraycopy(a21.getColumn(i), 0, joinedMatrix.getColumn(size+i), 0, size);
            System.arraycopy(a22.getColumn(i), 0, joinedMatrix.getColumn(size+i), size, size);
        }

        return joinedMatrix;
    }
}
