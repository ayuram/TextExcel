 

//Update this file with your own code.

public class SpreadsheetLocation implements Location
{
    private int col;
    private  int row;
    @Override
    public int getRow()
    {
        // TODO Auto-generated method stub
        return row;
    }

    @Override
    public int getCol()
    {
        // TODO Auto-generated method stub
        return col;
    }
    
    public SpreadsheetLocation(String cellName)
    {
        cellName = cellName.trim();
        col = Character.toUpperCase(cellName.charAt(0)) - 'A'; 
        row = -1 + Integer.parseInt(cellName.substring(1));
        // TODO: Fill this out with your own code
    }

}
