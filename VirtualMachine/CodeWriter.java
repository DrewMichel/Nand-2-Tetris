// Translates VM commands into Hack assembly code.

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class CodeWriter
{
    // Constants
    public static final String OUTPUT_FILE_EXTENSION = ".asm",
                               LINE_COMMENT = "//", ADDRESS_SYMBOL = "@",
                               END_OF_OPERATION_LABEL = "END_OF_OPERATION",
                               LABEL_START = "(", LABEL_END = ")",
                               PERIOD = ".", CALLER_RETURN_LABEL = "lunchbreak",
                               SYSTEM_INITIALIZATION_LABEL = "Sys.init";
    
    public static final int NUMBER_OF_CALLER_VALUES = 5;
    
    // Instance variables
    private PointerTable pointerTable;
    private PrintWriter fileWriter;
    private String fileName, directoryName;
    private boolean initialized;
    private int stackPointer, programCounter, labelCounter, callCounter;
    
    // Constructors
    public CodeWriter()
    {
        
    }
    
    public CodeWriter(File path)
    {
        initialize(path);
    }
    
    public CodeWriter(String path)
    {
        initialize(new File(path));
    }
    
    // Methods
    
    // Initializes instance variables and opens fileWriter instance variable
    // to File parameter path
    public void initialize(File path)
    {
        try
        {
            fileWriter = new PrintWriter(path);
            pointerTable = new PointerTable();
            fileName = path.getAbsolutePath();
            stackPointer = PointerTable.STACK_MIN_VALUE;
            programCounter = 1;
            labelCounter = 1;
            callCounter = 1;
            initialized = true;
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    
    // Methods
    
    // Returns instance variable initialized
    public boolean isInitialized()
    {
        return initialized;
    }
    
    // Returns String fileName instance variable
    public String getFileName()
    {
        return fileName;
    }
    
    // Sets instance variable fileName to String parameter name
    public void setFileName(String name)
    {
        fileName = name;
    }
    
    // Sets instance variable directoryName to String parameter name
    public void setDirectoryName(String name)
    {
        directoryName = name;
    }
    
    // Returns instance variable directoryName
    public String getDirectoryName()
    {
        return directoryName;
    }
    
    // Increments instance variable programCounter by one
    public void incrementProgramCounter()
    {
        ++programCounter;
    }
    
    // Sets instance variable programCounter to int parameter counter
    public void setProgramCounter(int counter)
    {
        programCounter = counter;
    }
    
    // Resets instance variable programCounter to one
    public void resetCallCounter()
    {
        callCounter = 1;
    }
    
    // Increments instance variable callCounter by one
    public void incrementCallCounter()
    {
        ++callCounter;
    }
    
    // Writes file header to file.
    // Should only be used for StaticsTest and FibonacciElements
    public void writeFileHeader()
    {
        // Initializes stack pointer
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_MIN_VALUE);
        fileWriter.println("D=A");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("M=D");

        // Calls Sys.init
        writeCall(SYSTEM_INITIALIZATION_LABEL, 0);
    }
    
    // Writes String parameter comment as a comment
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
            writeArithmeticAddition();
        }
        else if(command.equals(CommandTable.C_SUBTRACT))
        {
            writeArithmeticSubtract();
        }
        else if(command.equals(CommandTable.C_NEGATE))
        {
            writeArithmeticNegate();
        }
        else if(command.equals(CommandTable.C_EQUAL))
        {
            writeArithmeticEqual(command);
        }
        else if(command.equals(CommandTable.C_GREATER))
        {
            writeArithmeticGreater(command);
        }
        else if(command.equals(CommandTable.C_LESSER))
        {
            writeArithmeticLesser(command);
        }
        else if(command.equals(CommandTable.C_AND))
        {
            writeArithmeticAnd();
        }
        else if(command.equals(CommandTable.C_OR))
        {
            writeArithmeticOr();
        }
        else if(command.equals(CommandTable.C_NOT))
        {
            writeArithmeticNot();
        }
        else
        {
            // error
        }
    }
    
    // Adds the top two elements ontop of the stack together
    // and places the sum at the lower address
    public void writeArithmeticAddition()
    {
        writeOperationHeader();
        
        fileWriter.println("M=D+M");
    }
    
    // Subtracts the highest element on the stack from the next element
    // and places the difference at the lower address
    public void writeArithmeticSubtract()
    {
        writeOperationHeader();
        
        fileWriter.println("M=M-D");
    }
    
    // Negates the value at the top of the stack
    // without moving the stack pointer
    public void writeArithmeticNegate()
    {
        
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("A=M-1"); // leaving stack pointer at original position
        fileWriter.println("M=-M"); // Sets memory ontop of stack to its negative
    }
    
    // Writes -1 if the two top elements are equal
    // Writes 0 if they are not equal
    // The value is written onto the lower address
    public void writeArithmeticEqual(String labelName)
    {
        writeLogicalHeader(labelName);
        
        fileWriter.println("D;JEQ");
        
        writeLogicalBody(labelName);
    }
    
    // Writes -1 if the the second element ontop of the stack
    // is greater than the top element on the stack
    // Writes 0 if they are equal or if the second is less than the top
    // The value is written onto the lower address
    public void writeArithmeticGreater(String labelName)
    {
        writeLogicalHeader(labelName);
        
        fileWriter.println("D;JGT");
        
        writeLogicalBody(labelName);
    }
    
    // Writes -1 if the the second element ontop of the stack
    // is less than the top element on the stack
    // Writes 0 if they are equal or if the second is greater than the top
    // The value is written onto the lower address
    public void writeArithmeticLesser(String labelName)
    {
        writeLogicalHeader(labelName);
        
        fileWriter.println("D;JLT");
        
        writeLogicalBody(labelName);
    }
    
    // Performs a bitwise 'and' operation on the top two elements on the stack
    // The value is written onto the lower address
    public void writeArithmeticAnd()
    {
        writeOperationHeader();
        
        fileWriter.println("M=D&M");
    }
    
    // Perfroms a bitwise 'or' operation on the top two elements on the stack
    // The value is written onto the lower address
    public void writeArithmeticOr()
    {
        writeOperationHeader();
        
        fileWriter.println("M=D|M");
    }
    
    // Performs a bitwise 'not' on the top element on the stack
    // Without moving the stack pointer
    public void writeArithmeticNot()
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("A=M-1");
        fileWriter.println("M=!M");
    }
    
    // Acquires the value ontop of the stack and reduces the stack pointer
    private void writeOperationHeader()
    {
        // Acquire value ontop of the stack
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M");
        
        fileWriter.println("A=A-1");
    }
    
    // Prepares labels, A, D, and stack pointer for logical writes
    private void writeLogicalHeader(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        
        fileWriter.println("D=M");
        
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M-D");
        
        fileWriter.println(ADDRESS_SYMBOL + generateLabelSymbol(labelName + labelCounter));
    }
    
    // Called after writeLogicalHeader with a jump command to put -1 (true)
    // or 0 (false) onto the stack
    private void writeLogicalBody(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("A=M"); 
        fileWriter.println("M=" + PointerTable.FALSE_VALUE);
        fileWriter.println(ADDRESS_SYMBOL + generateEndLabelSymbol(Integer.toString(labelCounter)));
        fileWriter.println("0;JMP");
        
        fileWriter.println(generateLabel(labelName + labelCounter));
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("A=M");
        fileWriter.println("M=" + PointerTable.TRUE_VALUE);
        
        fileWriter.println(generateEndLabel(Integer.toString(labelCounter)));
        
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("M=M+1");
        
        incrementLabelCounter();
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
        else if(pointerTable.containsGeneralSegment(segment))
        {
            writePushGeneral(pointerTable.getGeneralSymbol(segment), index);
        }
        else if(segment.equals(PointerTable.TEMP_SEGMENT))
        {
            writePushTemp(index);
        }
        else if(segment.equals(PointerTable.STATIC_SEGMENT))
        {
            writePushStatic(index);
        }
        else if(segment.equals(PointerTable.POINTER_SEGMENT))
        {
            writePushPointer(index);
        }
    }
    
    // Pushes int parameter index onto the stack and increases the stack pointer
    public void writePushConstant(int index)
    {
        fileWriter.println(ADDRESS_SYMBOL + index); // @ constant value of index
        fileWriter.println("D=A"); // Sets D register = index
        
        writePushBody();
    }
    
    // Pushes value from Local, Argument, This, or That's
    // base address + index value onto the stack and increases stack pointer
    public void writePushGeneral(String symbol, int index)
    {
        // Acquire value at symbol's base address + index
        fileWriter.println(ADDRESS_SYMBOL + index);
        fileWriter.println("D=A");
        fileWriter.println(ADDRESS_SYMBOL + symbol);
        fileWriter.println("A=D+M");
        fileWriter.println("D=M");
        
        writePushBody();
    }
    
    // Pushes value from temp register # index onto the stack
    // and increases stack pointer
    public void writePushTemp(int index)
    {
        // Acquire value at temp base address + index
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.TEMP_REGISTERS[index]);
        fileWriter.println("D=M");
        
        writePushBody();
    }
    
    // Pushes value from static base address + index onto the stack
    // and increases stack pointer
    public void writePushStatic(int index)
    {
        // Acquire value at static index
        fileWriter.println(ADDRESS_SYMBOL + adjustExtension(getRealName(fileName), PERIOD + index));
        fileWriter.println("D=M");
        
        writePushBody();
    }
    
    // Pushes this or that pointer's value onto the stack
    // Increases stack pointer
    public void writePushPointer(int index)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.POINTER_REGISTERS[index]);
        fileWriter.println("D=M");
        
        writePushBody();
    }
    
    // Pushes D's value onto the stack and increases stack pointer
    private void writePushBody()
    {
        // Push value ontop of the stack
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("M=M+1");
        fileWriter.println("A=M-1");
        fileWriter.println("M=D");
    }
    
    // Writes the assembly code that is the translation
    // of the given command C_POP.
    // Pops value off the stack onto the segment's pointer + index.
    public void writePop(String segment, int index)
    {
        if(pointerTable.containsGeneralSegment(segment))
        {
            writePopGeneral(pointerTable.getGeneralSymbol(segment), index);
        }
        else if(segment.equals(PointerTable.TEMP_SEGMENT))
        {
            writePopTemp(segment, index);
        }
        else if(segment.equals(PointerTable.STATIC_SEGMENT))
        {
            writePopStatic(index);
        }
        else if(segment.equals(PointerTable.POINTER_SEGMENT))
        {
            writePopPointer(index);
        }
    }
    
    // Writes value ontop of the stack onto local, argument, this, or that
    // base address + index and decreases stack pointer
    public void writePopGeneral(String symbol, int index)
    {
        // Set general register to symbol's base address + index
        fileWriter.println(ADDRESS_SYMBOL + index);
        fileWriter.println("D=A");
        fileWriter.println(ADDRESS_SYMBOL + symbol);
        fileWriter.println("A=D+M");
        fileWriter.println("D=A");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.GENERAL_PURPOSE_REGISTERS[0]);
        fileWriter.println("M=D");
        
        writePopHeader();
        
        // Move value to segment's base address + index
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.GENERAL_PURPOSE_REGISTERS[0]);
        fileWriter.println("A=M");
        fileWriter.println("M=D");
    }
    
    // Writes value ontop of the stack onto temp # index
    // and decreases stack pointer
    public void writePopTemp(String symbol, int index)
    {
        writePopHeader();
        
        // Move value to temp segment's base address + index
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.TEMP_REGISTERS[index]);
        fileWriter.println("M=D");
    }
    
    // Writes value ontop of the stack onto static
    // and decreases stack pointer
    public void writePopStatic(int index)
    {
        writePopHeader();
        
        // Move value to static index
        fileWriter.println(ADDRESS_SYMBOL + adjustExtension(getRealName(fileName), PERIOD + index));
        fileWriter.println("M=D");
    }
    
    // Writes value ontop of the stack onto this or that
    // and decreases stack pointer
    public void writePopPointer(int index)
    {
        writePopHeader();
        
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.POINTER_REGISTERS[index]);
        fileWriter.println("M=D");
        
    }
    
    // Decreases stack pointer and acquires value ontop of the stack
    private void writePopHeader()
    {
        // Acquire value ontop of the stack
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M");
    }
    
    // Writes label to file
    public void writeLabel(String labelName)
    {
        fileWriter.println(generateLabel(labelName));
    }
    
    // Writes goto String parameter labelName
    public void writeGoto(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + labelName);
        fileWriter.println("0;JMP");
    }
    
    // Write if-goto String parameter labelName
    public void writeIfGoto(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M");
        
        fileWriter.println(ADDRESS_SYMBOL + labelName);
        fileWriter.println("D;JNE");
    }
    
    // Writes function call
    public void writeCall(String functionName, int numArgs)
    {
        // Pushes label address onto stack
        fileWriter.println(ADDRESS_SYMBOL + generateLabelSymbol(functionName + CALLER_RETURN_LABEL + callCounter));
        fileWriter.println("D=A");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("M=M+1");
        fileWriter.println("A=M-1");
        fileWriter.println("M=D");
        
        // Push segments LCL, ARG, THIS, then THAT onto stack
        writePushSegmentPointers();
        
        // Reposition ARG = SP - 5 - numArgs
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("D=M");
        fileWriter.println(ADDRESS_SYMBOL + (NUMBER_OF_CALLER_VALUES + numArgs));
        fileWriter.println("D=D-A");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.ARGUMENT_SYMBOL);
        fileWriter.println("M=D");
        
        // Reposition LCL = SP
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("D=M");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.LOCAL_SYMBOL);
        fileWriter.println("M=D");
        
        // Goes to the function
        writeGoto(functionName);
        
        // Declares label for return purposes
        fileWriter.println(generateLabel(functionName + CALLER_RETURN_LABEL + callCounter));
        
        incrementCallCounter();
    }
    
    // Pushes local, argument, this, and that pointers onto stack
    private void writePushSegmentPointers()
    {
        for(int i = 1; i < PointerTable.SEGMENT_POINTERS.length; ++i)
        {
            // Get pointer value
            fileWriter.println(ADDRESS_SYMBOL + PointerTable.SEGMENT_POINTERS[i]);
            fileWriter.println("D=M");
            
            // Push pointer onto stack
            fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
            fileWriter.println("M=M+1");
            fileWriter.println("A=M-1");
            fileWriter.println("M=D");
        }
    }
    
    // Writes function return
    public void writeReturn()
    {
        // *GENERAL_PURPOSE_REGISTERS[1] = endframe
        // *GENERAL_PURPOSE_REGISTERS[2] = return address
        
        // endFrame = LCL, endframe is a temporary variable
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.LOCAL_SYMBOL);
        fileWriter.println("D=M");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.GENERAL_PURPOSE_REGISTERS[1]);
        fileWriter.println("M=D");
        
        // return address = *(endFrame - 5), gets the return address
        fileWriter.println(ADDRESS_SYMBOL + NUMBER_OF_CALLER_VALUES);
        fileWriter.println("A=D-A");
        
        fileWriter.println("D=M");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.GENERAL_PURPOSE_REGISTERS[2]);
        fileWriter.println("M=D");
        
        // *ARG = pop(), repositions the return value for the caller
        // Take value ontop of stack and push it to arg 0
        writePopGeneral(PointerTable.ARGUMENT_SYMBOL, 0);
        
        // SP = ARG + 1, repositions the SP of the caller
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.ARGUMENT_SYMBOL);
        fileWriter.println("D=M+1");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("M=D");
        
        // Pop segments off stack THAT, THIS, ARG, then LCL
        writePopSegmentPointers();
        
        // goto return address
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.GENERAL_PURPOSE_REGISTERS[2]);
        fileWriter.println("A=M");
        fileWriter.println("0;JMP");
    }
    
    // Pops that, this, argument, and local pointers off the stack
    private void writePopSegmentPointers()
    {
        int offset = 1;
        
        for(int i = PointerTable.SEGMENT_POINTERS.length - 1; i >= 1; --i)
        {
            // segment[i] = *(endframe - offset)
            fileWriter.println(ADDRESS_SYMBOL + offset);
            fileWriter.println("D=A");
            
            fileWriter.println(ADDRESS_SYMBOL + PointerTable.GENERAL_PURPOSE_REGISTERS[1]);
            fileWriter.println("A=M-D");
            fileWriter.println("D=M");
            
            fileWriter.println(ADDRESS_SYMBOL + PointerTable.SEGMENT_POINTERS[i]);
            fileWriter.println("M=D");
            
            ++offset;
        }
    }
    
    // Writes function label and pushes zeroes onto the stack based on
    // int parameter numLocals
    public void writeFunction(String functionName, int numLocals)
    {
        // Function entry label
        fileWriter.println(generateLabel(functionName));
        
        // Push 0 onto local segments parameter numLocals amount of times
        for(int i = 0; i < numLocals; ++i)
        {
            // Since LCL = SP during start of function, pushing zeroes onto
            // stack initializes local segment values to 0
            // and points stack pointer just above local segment
            writePushConstant(0);
        }
    }
    
    // Closes the output file.
    public void close()
    {
        if(fileWriter != null)
        {
            fileWriter.close();
        }
        
        initialized = false;
    }
    
    // Returns a String containing File parameter path's name with
    // String parameter extension
    public static String adjustExtension(File path, String extension)
    {
        return adjustExtension(path.getAbsolutePath(), extension);
    }
    
    // Returns a String containing String parameter path's name with
    // String parameter extension
    public static String adjustExtension(String path, String extension)
    {
        int index = -1;
        
        String out = null;
        
        if(path != null)
        {
            out = path;
            
            index = out.lastIndexOf(PERIOD);
            
            if(index < 0)
            {
                out += extension;
            }
            else
            {
                out = out.substring(0, index) + extension;
            }
        }
        
        return out;
    }
    
    // Returns a String containing File parameter path's name without
    // parent directories
    public static String getRealName(File path)
    {
        return getRealName(path.getAbsolutePath());
    }
    
    // Returns a String containing String parameter path's name without
    // parent directories
    public static String getRealName(String path)
    {
        String name = path;
        
        int index = -1, length = name.length();
        
        char current;
        
        for(int i = 0; i < length; ++i)
        {
            current = name.charAt(i);
            
            if(current == '/' || current == '\\')
            {
                index = i;
            }
        }
        
        if(index > -1)
        {
            name = name.substring(index + 1);
        }
        
        return name;
    }
    
    // Returns a String label with String parameter labelName
    public String generateLabel(String labelName)
    {
        return LABEL_START + labelName + LABEL_END;
    }
    
    // Useless method that doesn't do anything since labelCounter isn't
    // concatenated with String parameter labelName
    public String generateLabelSymbol(String labelName)
    {
        return labelName;
    }
    
    // Returns a String end label with String parameter tag
    public String generateEndLabel(String tag)
    {
        return LABEL_START + END_OF_OPERATION_LABEL + tag + LABEL_END;
    }
    
    // Returns a String end label symbol with String parameter tag
    public String generateEndLabelSymbol(String tag)
    {
        return END_OF_OPERATION_LABEL + tag;
    }
    
    // Increments instance variable labelCounter by one
    public void incrementLabelCounter()
    {
        ++labelCounter;
    }
}