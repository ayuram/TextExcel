
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

// Update this file with your own code.

public class TextExcel
{

    public static void main(String[] args)
    {
        
        Grid sheet = new Spreadsheet();
        sheet.processCommand("A1 = \"branded\"");
        sheet.processCommand("B1 = \"acting\"");
        sheet.processCommand("C1 = \"branding\"");
        sheet.processCommand("D1 = \"acted\"");
        sheet.processCommand("E1 = 17.4");
        sheet.processCommand("A2 = 3.14159");
        sheet.processCommand("B2 = \"extras!\"");
        System.out.println(sheet.getGridText());
        
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        
        
        while (!inputLine.equalsIgnoreCase("quit"))
        {
            String outputLine = sheet.processCommand(inputLine);
            System.out.println(outputLine);
            inputLine = scanner.nextLine();
            
        }
        
        
        //TestsALL.Helper th = new TestsALL.Helper();         
        //System.out.println(th.getText());
       
       
       
    }
}
