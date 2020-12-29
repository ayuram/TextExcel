
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public  class ExtensionCircularReferenceErrors
{
    // Tests for circular reference errors extension
    private Grid grid;
    private final String expectedError = "#ERROR    ";
    private final String expectedOne = "1         ";

    @Before
    public void initializeGrid()
    {
        grid = new Spreadsheet();
    }

    private void assertEvalError(int row, int col, String formula, String description)
    {
        Cell cell = grid.getCell(new TestsALL.TestLocation(row, col));
        assertEquals(description, expectedError, cell.abbreviatedCellText());
        assertEquals("formula", formula, cell.fullCellText());
    }

    private void assertEvalOK(int row, int col, String expected, String formula, String description)
    {
        Cell cell = grid.getCell(new TestsALL.TestLocation(row, col));
        assertEquals(description, expected, cell.abbreviatedCellText());
        assertEquals("formula", formula, cell.fullCellText());
    }

    @Test
    public void testCircularReference()
    {
        TestsALL.Helper thErrors = new TestsALL.Helper();
        TestsALL.Helper thOnes = new TestsALL.Helper();
        for (int col = 0; col < 3; col++)
        {
            thErrors.setItem(0,  col, expectedError);
            thOnes.setItem(0,  col,  expectedOne);
        }
        grid.processCommand("A1 = ( B1 )");
        grid.processCommand("b1 = ( c1 )");
        try
        {
            String gridErrors = grid.processCommand("C1 = ( a1 )");
            assertEquals("grid with circular reference errors", thErrors.getText(), gridErrors);
            String gridOnes = grid.processCommand("B1 = 1");
            assertEquals("grid with ones", thOnes.getText(), gridOnes);
            assertEvalOK(0, 2, expectedOne, "( a1 )", "noncircular");
            gridErrors = grid.processCommand("b1 = ( a1 )");
            assertEquals("second grid with circular reference errors", thErrors.getText(), gridErrors);
            assertEvalError(0, 2, "( a1 )", "circular");
        }
        catch (StackOverflowError e)
        {
            fail("Circular reference not handled, caught stack overflow error");
        }
    }
}