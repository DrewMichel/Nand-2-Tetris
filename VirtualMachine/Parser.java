// Handles the parsing of a single .vm file, and encapsulates access
// to the input code. It reads VM commands, parses them, and provides
// convenient access to their components. In addition, it removes all
// white space and comments.

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;

public class Parser
{
    // hashmap, enums, or array for commandTypes
    
    // Instance variables
    private Scanner fileScanner;
    private String fileName, currentCommand;
    
    // Constructor
    public Parser(File path)
    {
        initialize(path);
    }
    
    public Parser(String path)
    {
        initialize(new File(path));
    }
    
    private void initialize(File path)
    {
        try
        {
            fileScanner = new Scanner(path);
            fileName = path.getAbsolutePath();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    
    // Returns true if the file contains additional lines, else false
    public boolean hasMoreCommands()
    {
        return fileScanner.hasNextLine();
    }
    
    // Reads the next command from the input and makes it the current command.
    // Should be called only if hasMoreCommands is true.
    // Initially there is no current command.
    public void advance()
    {
        currentCommand = fileScanner.nextLine();
    }
    
    // Returns the type of the current VM command.
    // C_ARITHMETIC is returned for all the arithmetic commands.
    public String commandType()
    {
        return null;
    }
    
    // Returns the first arg. of the current command.
    // In the case of C_ARITHMETIC, the command itself
    // (add, sub, etc.) is returned. Should not be called
    // if the current command is C_RETURN.
    public String arg1()
    {
        return null;
    }
    
    // Returns the second argument of the current command.
    // Should be called only if the current command
    // is C_PUSH, C_POP, C_FUNCTION, or C_CALL.
    public int arg2()
    {
        return -1;
    }
    
    public static String clearCommentation(String original)
    {
        String out = original;
        int index = -1;
        
        if(original != null && ((index = original.indexOf("//")) >= 0))
        {
            out = original.substring(0, index);
        }
        
        return out;
    }
    
    public static String clearWhitespace(String original)
    {
        StringBuilder builder = new StringBuilder();
        
        if(original != null)
        {
            int size = original.length();
            char current;
        
            for(int i = 0; i < size; ++i)
            {
                current = original.charAt(i);
                
                if(!Character.isSpace(current))
                {
                    builder.append(current);
                }
            }
        }
        
        return builder.toString();
    }
}