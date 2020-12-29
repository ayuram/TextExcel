
/**
 * Write a description of class PercentCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PercentCell extends RealCell
{
    // instance variables - replace the example below with your own
    private double percent;
    /**
     * Constructor for objects of class PercentCell
     */
    public PercentCell(double percent)
    {
        // initialise instance variables
        super(percent/100 +"");
        this.percent = percent;
    }
    public PercentCell(String percent)
    {
        // initialise instance variables
        super(Double.parseDouble(percent)/100 +"");
        this.percent = Double.parseDouble(percent);
    }
    @Override
    public String abbreviatedCellText(){
        String spaces = "";
        String abbrev = percent + "";
        int index = abbrev.length() - 1;
        for(int i = 0; i < abbrev.length(); i++){
            if(abbrev.charAt(i) == '.'){
                index = i;
            }
        }
        abbrev = abbrev.substring(0, index) + "%";
        for(int i = abbrev.length(); i < 10; i++){
            spaces = spaces + " ";
        }
        
        
        return abbrev + spaces;
    }

    @Override
    public double getDoubleVal(){
        return percent/100;
    }
   
}
