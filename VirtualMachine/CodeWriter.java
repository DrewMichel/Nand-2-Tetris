// Translates VM commands into Hack assembly code.

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class CodeWriter
{
    // Constants
    public static final String OUTPUT_FILE_EXTENSION = ".asm", LINE_COMMENT = "//", ADDRESS_SYMBOL = "@";
    
    // Instance variables
    private PointerTable pointerTable;
    private PrintWriter fileWriter;
    private String fileName;
    
    private int stackPointer, programCounter;
    
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
            pointerTable = new PointerTable();
            fileName = path.getAbsolutePath();
            stackPointer = PointerTable.STACK_MIN_VALUE;
            programCounter = 1;
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
    
    public void incrementProgramCounter()
    {
        ++programCounter;
    }
    
    public void setProgramCounter(int counter)
    {
        programCounter = counter;
    }
    
    public void writeComment(String comment)
    {
        fileWriter.println(LINE_COMMENT + " " + programCounter + ": " + comment);
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
    
    public void writeArithmetic()
    {
        
    }
    
    // Writes the assembly code that is the translation
    // of the given command C_PUSH.
    // Pushes the value from the segment's pointer + index onto the stack
    public void writePush(String segment, int index)
    {
        if(segment.equals(PointerTable.CONSTANT_SEGMENT))
        {
            writePushConstant(index);
        }
        else if(pointerTable.contains(segment))
        {
            writePushGeneral(pointerTable.getGeneralSymbol(segment), index);
        }
    }
    
    // Constant
    public void writePushConstant(int index)
    {
        System.out.println(ADDRESS_SYMBOL + index); // @ constant value of index
        System.out.println("D=A"); // Sets D register = index
        System.out.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL); // @SP
        System.out.println("M=M+1"); // Increments the value SP points
        System.out.println("A=M-1"); // Goes to the previous value SP pointed to
        System.out.println("M=D"); // Sets value ontop of stack to D (index)
    }
    
    // Local, Argument, This, and That, ... (Temp?), (Pointer?)
    public void writePushGeneral(String symbol, int index)
    {
        // Acquire value at symbol's base address + index
        System.out.println(ADDRESS_SYMBOL + index);
        System.out.println("D=A");
        System.out.println(ADDRESS_SYMBOL + symbol);
        System.out.println("A=D+M");
        System.out.println("D=M");
        
        // Push value onto stack
        System.out.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        System.out.println("M=M+1");
        System.out.println("A=M-1");
        System.out.println("M=D");
    }
    
    public void writePushStatic(String segment, int index)
    {
        
    }
    
    // Writes the assembly code that is the translation
    // of the given command C_POP.
    // Pops value off the stack onto the segment's pointer + index.
    public void writePop(String segment, int index)
    {
        
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