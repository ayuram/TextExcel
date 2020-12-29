
/**
 * Write a description of class TextCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextCell implements Cell
{
    // instance variables - replace the example below with your own
    private String text;
    private String abbrev;

    /**
     * Constructor for objects of class TextCell
     */
    public TextCell(String text)
    {
        // initialise instance variables
        this.text = text;
        abbrev = text.substring(0,Math.min(10, text.length()));
    }
    
    @Override
    public String fullCellText(){
        
        return "\"" + text + "\"";
        
    
    }
    public String getText(){
        return text;
    }
    @Override
    public String abbreviatedCellText(){
        String spaces = "";
        for(int i = abbrev.length(); i < 10; i++){
            spaces = spaces + " ";
        }
        return abbrev + spaces;  
    }
    @Override
    public int compareTo(Object x) {
		if (x instanceof TextCell) {
			TextCell cell = (TextCell)x;
			return fullCellText().compareTo(cell.fullCellText());
		} else if (x instanceof EmptyCell) {
			return 1;
		} else if (x instanceof RealCell) {
			return -1;
		} else {
			return -1;
		}
	}
    
}
