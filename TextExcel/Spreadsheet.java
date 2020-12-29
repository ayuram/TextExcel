import java.util.*;
// Update this file with your own code.
public class Spreadsheet implements Grid
{
    private Cell[][] sheet;
    private ArrayList<String> commands;
    private String lastOperation;
    public Spreadsheet(){
        sheet = new Cell[20][12];
        commands = new ArrayList<String>();
        clearAll();
    }

    private void clearAll(){
        for(int i = 0; i<sheet.length; i++){
            for(int j = 0; j < sheet[0].length; j++){
                sheet[i][j] = new EmptyCell();
            }
        }
    }


    private String createFormula(String[] val){
        String str = "";
        for(int i = 2; i < val.length; i++){
            str = str + val[i] + " ";
        }
        return str;
    }

    private void assignValue(String[] val, String original){
        SpreadsheetLocation loc = new SpreadsheetLocation(val[0]);
        if(val[2].equals("(")){
            sheet[loc.getRow()][loc.getCol()] = new FormulaCell(createFormula(val), this); 

        }
        else if(val[2].charAt(0) == '"'){
            sheet[loc.getRow()][loc.getCol()] = new TextCell(createFormula(val).substring(1, createFormula(val).length() - 2));
        }
        else{
            if(val[2].charAt(val[2].length() - 1) == '%'){
                sheet[loc.getRow()][loc.getCol()] = new PercentCell(val[2].substring(0, val[2].length() - 1));
            }
            else{
                sheet[loc.getRow()][loc.getCol()] = new ValueCell(val[2]);
            }
        }

        //return val[2];

    }

    public void ascendingSort(Location start, Location end, boolean a){
        //int covered = (end.getRow() - start.getRow() + 1)*(end.getCol() - start.getCol() + 1);
        ArrayList<Cell> list = new ArrayList<Cell>();

        for(int i = start.getRow(); i <= end.getRow(); i++){
            for(int j = start.getCol(); j <= end.getCol(); j++){
                if(sheet[i][j] instanceof RealCell){
                list.add(((RealCell)sheet[i][j]));
            }
            else{
                list.add(((TextCell)sheet[i][j]));
            }
            }
        }
        for(int i = 0; i < list.size(); i++){

            for(int j = i+1; j < list.size(); j++){
                
                if ((list.get(i).compareTo(list.get(j)) > 0 && a) || (list.get(i).compareTo(list.get(j)) < 0 && !a)) 
                {
                    Cell temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        int count = 0;
        for(int i = start.getRow(); i <= end.getRow(); i++){
            for(int j = start.getCol(); j <= end.getCol(); j++){
            
            sheet[i][j] = list.get(count);
            count ++;
            }
        }
    }

    

    @Override
    public String processCommand(String command)
    {
        // TODO Auto-generated method stub
        commands.add(command);
        String[] val = command.split(" ");
        if(command.trim().length() == 0){
            return "";
        }
        if(val[0].equalsIgnoreCase("clear")){
            if(val.length > 1){
                SpreadsheetLocation loc = new SpreadsheetLocation(val[1]);
                sheet[loc.getRow()][loc.getCol()] = new EmptyCell();
            }
            else{
                clearAll();
            }
            return getGridText();
        }
        else if(val[0].equalsIgnoreCase("sorta") || val[0].equalsIgnoreCase("sortd")){
            String[] newVal = val[1].split("-");
            SpreadsheetLocation start = new SpreadsheetLocation(newVal[0]);
            SpreadsheetLocation end = new SpreadsheetLocation(newVal[1]);

            if(val[0].equalsIgnoreCase("sorta")){
                ascendingSort(start, end, true);
            }

            else{
                ascendingSort(start, end, false);
            }
            return getGridText();
        }

        else if(val.length == 1){
            SpreadsheetLocation loc = new SpreadsheetLocation(val[0]);
            return sheet[loc.getRow()][loc.getCol()].fullCellText();
        }
        else if(val[1].equals("=")){
            assignValue(val, command);
            return getGridText();
        }

        return "";
    }

    @Override
    public int getRows()
    {
        // TODO Auto-generated method stub
        return sheet.length;
    }

    @Override
    public int getCols()
    {
        // TODO Auto-generated method stub
        return sheet[0].length;
    }

    @Override
    public Cell getCell(Location loc)
    {
        // TODO Auto-generated method stub
        return sheet[loc.getRow()][loc.getCol()];
    }

    public Cell getCell(int row, int col){
        return sheet[row][col];
    }

    private boolean isACell(String val){
        char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'};
        char init = val.charAt(0);
        for(int i = 0; i < alphabet.length; i++){
            if(init == (alphabet[i])){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getGridText()
    {
        String val = "";
        // TODO Auto-generated method stub
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        for(int i =0; i < alphabet.length; i++){
            if(i == 0){
                val += "   |";
            }
            val += alphabet[i] +"         |";

        }
        val += "\n";
        for(int i = 0; i < getRows(); i++){

            if(i+1 < 10){
                val += i+1+"  |";
            }
            else{
                val += i+1 +" |";
            }

            for(int j = 0; j < getCols(); j++){

                int l = 0;

                val += sheet[i][j].abbreviatedCellText();

                l = sheet[i][j].abbreviatedCellText().length();

                for(int k = 0; k < 10-l; k++){
                    val += " ";

                }
                val += "|";

            }
            val += "\n";
        }

        return val;

        //return sheet.toString();
    }

}
