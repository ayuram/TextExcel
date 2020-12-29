
/**
 * Write a description of class RealCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class RealCell implements Cell
{
    // instance variables - replace the example below with your own
    private String val;
    private String abbrev;

    /**
     * Constructor for objects of class RealCell
     */
    public RealCell(String val)
    {
        // initialise instance variables
        double x = Double.parseDouble(val);
        String str = x + "";
        abbrev = str.substring(0, Math.min(10, str.length()));
        this.val = val;
    }

    public RealCell(){
    }

    @Override
    public String fullCellText(){
        return val;
    }

    @Override
    public String abbreviatedCellText(){
        String spaces = "";
        for(int i = abbrev.length(); i < 10; i++){
            spaces = spaces + " ";
        }
        return abbrev + spaces;
    }

    public int compareTo(Object x){
        if (x instanceof RealCell) {
            RealCell cell = (RealCell)x;
            double curr = getDoubleVal();
            double pos = cell.getDoubleVal();
            if (curr < pos) {
                return -1;
            } else if (curr > pos) {
                return 1;
            } else {
                return 0;
            }
        } else if (x instanceof EmptyCell) {
            return 1;
        } else if (x instanceof TextCell) {
            return 1;
        } else {
            return -1;
        }
    }

    public abstract double getDoubleVal();
}
