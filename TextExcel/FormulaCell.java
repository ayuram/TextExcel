
/**
 * Write a description of class FormulaCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class FormulaCell extends RealCell
{
    // instance variables - replace the example below with your own
    private String formula;
    private double value;
    private Spreadsheet sheet;
    //private boolean hasReferences = false;

    /**
     * Constructor for objects of class FormulaCell
     */
    public FormulaCell(String form, Spreadsheet sheet)
    {
        
        formula = form;
        this.sheet = sheet;
        compute();
    }

    @Override
    public String abbreviatedCellText(){
        
        compute();
        String returnVal = value+"";
        String abbrev = returnVal.substring(0,Math.min(returnVal.length(), 10));
        String spaces = "";
        for(int i = abbrev.length(); i < 10; i++){
            spaces = spaces + " ";
        }
        return abbrev + spaces;
    }
    @Override
    public String fullCellText(){
        
        compute();
        return formula.substring(0, formula.length()-1);
    }

    @Override
    public double getDoubleVal(){
        
        compute();
        return value;
    }
    
    public void compute(){
        String[] val = formula.substring(2, formula.length() - 2).split(" ");
        if(val[0].equalsIgnoreCase("sum") || val[0].equalsIgnoreCase("avg")){
            value = 0;
            int count = 0;
            String[] newStr = val[1].split("-");
            SpreadsheetLocation init = new SpreadsheetLocation(newStr[0]);
            SpreadsheetLocation end = new SpreadsheetLocation(newStr[1]);

            for(int i = init.getRow(); i <= end.getRow(); i++){
                for(int j = init.getCol(); j <= end.getCol(); j++){
                    value += ((RealCell)sheet.getCell(i,j)).getDoubleVal(); 
                    count++;
                }
            }
            if(val[0].equalsIgnoreCase("avg")){ 
                value /= count;    
            }
        }
        else{
            value = 0;
            ArrayList<String> ops = new ArrayList<String>();
            ops.add("+");
            //String[] form = formula.substring(2, formula.length() - 2).split("\\+|\\-");
            String[] form = parseString(formula.substring(2, formula.length() - 2));
            for(int i = 0; i < val.length; i++){
                if(isOperation(val[i])){
                    ops.add(val[i]);
                }
            }
            for(int i = 0; i < form.length; i++){
                boolean isSplittable = false;

                for(int j = 0; j < form[i].length(); j++){

                    if(form[i].charAt(j) == '*' || form[i].charAt(j) == '/'){
                        isSplittable = true;

                    }

                }
                if(isSplittable){
                    String[] tempForm = form[i].split(" ");
                    double tempVal = 0;
                    String lastOp = "+";
                    for(int j = 0; j < tempForm.length; j++){
                        if(tempForm[j].equals("*")){
                            lastOp = tempForm[j];
                        }
                        else if(tempForm[j].equals("/")){
                            lastOp = tempForm[j];
                        }
                        else if(isACell(tempForm[j])){
                            
                            SpreadsheetLocation newloc = new SpreadsheetLocation(tempForm[j]);
                            if(lastOp.equals("*")){
                                tempVal *= (((RealCell)sheet.getCell(newloc)).getDoubleVal());
                            }
                            else if(lastOp.equals("+")){
                                tempVal += (((RealCell)sheet.getCell(newloc)).getDoubleVal());
                            }
                            else{
                                tempVal /= (((RealCell)sheet.getCell(newloc)).getDoubleVal());
                            }
                        }

                        else{
                            // if((op.equals("+") || op.equals("-")) && (val[
                            if(lastOp.equals("*")){
                                tempVal *= Double.parseDouble(tempForm[j]);
                            }
                            else if(lastOp.equals("+")){
                                tempVal += Double.parseDouble(tempForm[j]);
                            }
                            else{
                                tempVal /= Double.parseDouble(tempForm[j]);
                            }

                        }
                    }
                    form[i] = tempVal+"";
                }
            }
            for(int i = 0; i < form.length; i++){
                if(ops.get(i).equals("+")){
                    if(isACell(form[i])){
                        
                        SpreadsheetLocation newloc = new SpreadsheetLocation(form[i]);

                        value += (((RealCell)sheet.getCell(newloc)).getDoubleVal());
                    }
                    else{

                        value += Double.parseDouble(form[i]);
                    }
                }
                else{
                    if(isACell(form[i])){
                        SpreadsheetLocation newloc = new SpreadsheetLocation(form[i]);

                        value -= (((RealCell)sheet.getCell(newloc)).getDoubleVal());
                    }
                    else{
                        value -= Double.parseDouble(form[i]);
                    }
                }
            }

        }
    }
    private String[] parseString(String val){
        ArrayList<String> list = new ArrayList<String>();
        int min = 0;
        boolean multFound = false;
        for(int i = 0; i < val.length(); i++){
            if((val.charAt(i) == '+' || val.charAt(i) == '-') && val.charAt(i+1) == ' '){
                list.add(val.substring(min, i-1));
                min = i+2;
            }
        }
        list.add(val.substring(min));
        String[] str = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            str[i] = list.get(i);
        }
        return str;
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

    private boolean isOperation(String val){
        String[] ops = {"+", "-"};
        for(int i = 0; i < ops.length; i ++){
            if(val.equals(ops[i])) return true;
        }
        return false;
    }
    /*
    public boolean isANumber(String[] val, int index){
    if(val[index].charAt(0) == "0" ||
    }*/

}
