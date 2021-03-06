import com.jatrix.conversion.MatrixConversion;
import com.jatrix.core.Matrix;
import org.junit.Assert;
import org.junit.Test;

public class MatrixConversionTest {
    @Test
    public void swapColumnsTest_swapColumnsOfMatrix_assertMustBeEqual() {
        // Arrange
        double[][] n = {
                {1, 2, 3},
                {3, 4, 5},
                {6, 7, 8}
        };
        Matrix m1 = new Matrix(n);
        Matrix expected = new Matrix(new double[][]{{2, 1, 3}, {4, 3, 5}, {7, 6, 8}});

        // Act
        Matrix actual = m1.clone();
        MatrixConversion.swapColumns(actual, 0, 1);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void swapRowsTest_swapRowsOfMatrix_assertMustBeEqual() {
        // Arrange
        double[][] n = {
                {1, 2, 3},
                {3, 4, 5},
                {6, 7, 8}
        };
        Matrix m1 = new Matrix(n);
        Matrix expected = new Matrix(new double[][]{{3, 4, 5}, {1, 2, 3}, {6, 7, 8}});

        // Act
        Matrix actual = m1.clone();
        MatrixConversion.swapRows(actual, 0, 1);

        // Assert
        Assert.assertEquals(expected, actual);
    }
}
