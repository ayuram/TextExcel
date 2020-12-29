
/**
 * Write a description of class ValueCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ValueCell extends RealCell
{
    // instance variables - replace the example below with your own
    private double val;
    /**
     * Constructor for objects of class ValueCell
     */
    public ValueCell(double val)
    {
        // initialise instance variables
        super(val +"");
        this.val = val;
    }
    public ValueCell(String val){
        super(val);
        this.val = Double.parseDouble(val);
    }
    @Override
    public double getDoubleVal(){
       return val;
    }
}
