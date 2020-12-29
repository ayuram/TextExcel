
/**
 * Write a description of class EmptyCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EmptyCell extends TextCell implements Cell
{
    // instance variables - replace the example below with your own
    public EmptyCell(){
        super("");
    }

    public String abbreviatedCellText(){
        return "          ";
    }// text for spreadsheet cell display, must be exactly length 10
    public String fullCellText(){
        return "";
    }

    public double getDoubleVal(){
        return 0;
    }

    public int compareTo(Object x) {
        if (x instanceof EmptyCell) {
            return 0;
        } else {
            return -1;
        }
    }
}
