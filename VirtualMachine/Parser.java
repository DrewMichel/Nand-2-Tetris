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
    // Constants
    private static final String[] VALID_INPUT_FILE_EXTENSIONS = {".vm"};
    private static final int INVALID_ARGUMENT_VALUE = -1;
    
    // Instance variables
    private Scanner fileScanner;
    private String fileName, currentCommand, currentType;
    private CommandTable commandTable;
    
    // Constructor
    public Parser()
    {
        
    }
    
    public Parser(File path)
    {
        initialize(path);
    }
    
    public Parser(String path)
    {
        initialize(new File(path));
    }
    
    public void close()
    {
        if(fileScanner != null)
        {
            fileScanner.close();
        }
    }
    
    public void initialize(File path)
    {
        try
        {
            fileScanner = new Scanner(path);
            fileName = path.getAbsolutePath();
            commandTable = CommandTable.getCommandTable();
            currentType = CommandTable.C_INVALID;
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
        currentCommand = trimWhitespace(clearCommentation(fileScanner.nextLine()));
    }
    
    // Returns the type of the current VM command.
    // C_ARITHMETIC is returned for all the arithmetic commands.
    public String commandType()
    {
        if(currentCommand != null)
        {
            int index = currentCommand.indexOf(" ");
            
            if(index > -1)
            {
                currentType = commandTable.getCommandType(currentCommand.substring(0, index));
                return currentType;
            }
            else
            {
                currentType = commandTable.getCommandType(currentCommand);
                return currentType;
            }
        }
        else
        {
            currentType = CommandTable.C_INVALID;
            return currentType;
        }
    }
    
    public String getCurrentType()
    {
        return currentType;
    }
    
    public String getCurrentCommand()
    {
        return currentCommand;
    }
    
    // Returns the first arg. of the current command.
    // In the case of C_ARITHMETIC, the command itself
    // (add, sub, etc.) is returned. Should not be called
    // if the current command is C_RETURN.
    public String arg1()
    {
        if(currentCommand != null)
        {
            int index = currentCommand.indexOf(" ");
            
            if(index > -1)
            {
                return currentCommand.substring(0, index);
            }
            else
            {
                return currentCommand;
            }
        }
        
        return CommandTable.C_INVALID_TYPE;
    }
    
    // Returns the segment argument of the current command
    // Should be calledo nly if the current command
    // is C_PUSH, C_POP, ...
    public String segment()
    {
        if(currentCommand != null)
        {
            return currentCommand.split(" ")[1];
        }
        
        return PointerTable.INVALID_POINTER;
    }
    
    // Returns the second argument of the current command.
    // Should be called only if the current command
    // is C_PUSH, C_POP, C_FUNCTION, or C_CALL.
    public int arg2()
    {
        if(currentCommand != null)
        {
            return Integer.parseInt(currentCommand.split(" ")[2]);
        }
        
        return INVALID_ARGUMENT_VALUE;
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
    
    // Returns a String with the contents of String parameter original without
    // whitespace before and after all other characters within
    // the original String
    // Trims spaces between other characters down to a single space
    // I.E "   abc  def ghi    " becomes "abc def ghi"
    public static String trimWhitespace(String original)
    {
        StringBuilder builder = new StringBuilder();
        
        boolean noneSpaceDetected = false, previousSpace = true, isSpace = false;
        
        char current;
        
        if(original != null)
        {
            for(int i = 0; i < original.length(); ++i)
            {
                current = original.charAt(i);
                
                isSpace = Character.isSpace(current);
                
                if(!isSpace)
                {
                    previousSpace = false;
                    noneSpaceDetected = true;
                    builder.append(current);
                }
                else if(isSpace && !previousSpace && noneSpaceDetected)
                {
                    previousSpace = true;
                    builder.append(current);
                }
            }
            
            int builderLength = builder.length();
            
            if((builderLength > 0) && Character.isSpace(builder.charAt(builderLength - 1)))
            {
                builder.deleteCharAt(builderLength - 1);
            }
        }
        
        return builder.toString();
    }
    
    public static boolean isDigits(String original)
    {
        if(original == null || 1 > original.length())
        {
            return false;
        }
        
        for(int i = 0; i < original.length(); ++i)
        {
            if(!Character.isDigit(original.charAt(i)))
            {
                return false;
            }
        }
        
        return true;
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
    
    public static boolean hasValidFileExtension(File path)
    {
        if(path != null && path.exists())
        {
            String extension = getFileExtension(path);
            
            for(int i = 0; i < VALID_INPUT_FILE_EXTENSIONS.length; ++i)
            {
                if(extension.equalsIgnoreCase(VALID_INPUT_FILE_EXTENSIONS[i]))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static boolean hasValidFileExtension(String path)
    {
        return hasValidFileExtension(new File(path));
    }
    
    public static String getFileExtension(File path)
    {
        String out = null;
        
        if(path != null)
        {
            String name = path.getName();
            int index = name.lastIndexOf(".");
            
            if(index > -1)
            {
                out = name.substring(index);
            }
        }
        
        return out;
    }
    
    public static String getFileExtension(String path)
    {
        return getFileExtension(new File(path));
    }
}