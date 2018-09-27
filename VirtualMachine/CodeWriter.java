// Translates VM commands into Hack assembly code.

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class CodeWriter
{
    // Constants
    public static final String OUTPUT_FILE_EXTENSION = ".asm";
    
    // Instance variables
    private PrintWriter fileWriter;
    private String fileName;
    
    // Constructor
    public CodeWriter(File path)
    {
        initialize(path);
    }
    
    public CodeWriter(String path)
    {
        initialize(new File(path));
    }
    
    private void initialize(File path)
    {
        try
        {
            fileWriter = new PrintWriter(path);
            fileName = path.getAbsolutePath();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    
    // Returns String fileName instance variable
    public String getFileName()
    {
        return fileName;
    }
    
    // Writes the assembly code that is the translation
    // of the given arithmetic command.
    // Should be called after determining that command is C_ARITHMETIC
    public void writeArithmetic(String command)
    {
        if(command.equals(CommandTable.C_ADDITION))
        {
            //writeAddition();
        }
        else if(command.equals(CommandTable.C_SUBTRACT))
        {
            //writeSubtract();
        }
        else if(command.equals(CommandTable.C_NEGATIVE))
        {
            //writeNegative();
        }
        else if(command.equals(CommandTable.C_EQUAL))
        {
            //writeEqual();
        }
        else if(command.equals(CommandTable.C_GREATER))
        {
            //writeGreater();
        }
        else if(command.equals(CommandTable.C_LESSER))
        {
            //writeLesser();
        }
        else if(command.equals(CommandTable.C_AND))
        {
            //writeAnd();
        }
        else if(command.equals(CommandTable.C_OR))
        {
            //writeOr();
        }
        else if(command.equals(CommandTable.C_NOT))
        {
            //writeNot();
        }
        else
        {
            // error
        }
    }
    
    // Writes the assembly code that is the translation
    // of the given command, where command is either
    // C_PUSH or C_POP.
    public void writePushPop(String command, String segment, int index)
    {
        if(command.equals(CommandTable.C_POP))
        {
            //writePop();
        }
        else if(command.equals(CommandTable.C_PUSH))
        {
            //writePush();
        }
        else
        {
            // error
        }
    }
    
    
    // Closes the output file.
    public void close()
    {
        if(fileWriter != null)
        {
            fileWriter.close();
        }
    }
    
    public static String adjustExtension(File path)
    {
        int index = -1;
        
        String out = null;
        
        if(path != null)
        {
            out = path.getAbsolutePath();
            
            index = out.lastIndexOf(".");
            
            if(index < 0)
            {
                out += OUTPUT_FILE_EXTENSION;
            }
            else
            {
                out = out.substring(0, index) + OUTPUT_FILE_EXTENSION;
            }
        }
        
        return out;
    }
    
    public static String adjustExtension(String path)
    {
        return adjustExtension(new File(path));
    }
}