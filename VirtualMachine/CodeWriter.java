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
    
    // Constructor
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
    
    public boolean isInitialized()
    {
        return initialized;
    }
    
    // Returns String fileName instance variable
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String name)
    {
        fileName = name;
    }
    
    public void setDirectoryName(String name)
    {
        directoryName = name;
    }
    
    public String getDirectoryName()
    {
        return directoryName;
    }
    
    public void incrementProgramCounter()
    {
        ++programCounter;
    }
    
    public void setProgramCounter(int counter)
    {
        programCounter = counter;
    }
    
    public void resetCallCounter()
    {
        callCounter = 1;
    }
    
    public void incrementCallCounter()
    {
        ++callCounter;
    }
    
    public void writeFileHeader()
    {
        // Initialize segments
        //writeSegmentHeader();
        
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_MIN_VALUE);
        fileWriter.println("D=A");
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("M=D");
        
        writeGoto(SYSTEM_INITIALIZATION_LABEL);
    }
    
    private void writeSegmentHeader()
    {
        // Initializes stack, local, argument, this, and that pointers
        for(int i = 0; i < PointerTable.SEGMENT_POINTERS.length; ++i)
        {
            fileWriter.println(ADDRESS_SYMBOL + PointerTable.DEFAULT_SEGMENT_VALUES[i]);
            fileWriter.println("D=A");
            fileWriter.println(ADDRESS_SYMBOL + PointerTable.SEGMENT_POINTERS[i]);
            fileWriter.println("M=D");
        }
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
    
    public void writeArithmeticAddition()
    {
        writeOperationHeader();
        
        fileWriter.println("M=D+M");
    }
    
    public void writeArithmeticSubtract()
    {
        writeOperationHeader();
        
        fileWriter.println("M=M-D");
    }
    
    public void writeArithmeticNegate()
    {
        // Negate value at the top of the stack
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("A=M-1"); // leaving stack pointer at original position
        fileWriter.println("M=-M"); // Sets memory ontop of stack to its negative
    }
    
    public void writeArithmeticEqual(String labelName)
    {
        writeLogicalHeader(labelName);
        
        fileWriter.println("D;JEQ");
        
        writeLogicalBody(labelName);
    }
    
    public void writeArithmeticGreater(String labelName)
    {
        writeLogicalHeader(labelName);
        
        fileWriter.println("D;JGT");
        
        writeLogicalBody(labelName);
    }
    
    public void writeArithmeticLesser(String labelName)
    {
        writeLogicalHeader(labelName);
        
        fileWriter.println("D;JLT");
        
        writeLogicalBody(labelName);
    }
    
    public void writeArithmeticAnd()
    {
        writeOperationHeader();
        
        fileWriter.println("M=D&M");
    }
    
    public void writeArithmeticOr()
    {
        writeOperationHeader();
        
        fileWriter.println("M=D|M");
    }
    
    public void writeArithmeticNot()
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("A=M-1");
        fileWriter.println("M=!M");
    }
    
    public void writeOperationHeader()
    {
        // Acquire value ontop of the stack
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M");
        
        fileWriter.println("A=A-1");
    }
    
    public void writeLogicalHeader(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        
        fileWriter.println("D=M");
        
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M-D");
        
        fileWriter.println(ADDRESS_SYMBOL + generateLabelSymbol(labelName + labelCounter));
    }
    
    public void writeLogicalBody(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("A=M"); 
        fileWriter.println("M=" + PointerTable.FALSE_VALUE);// false, not equal
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
    
    // Constant
    public void writePushConstant(int index)
    {
        fileWriter.println(ADDRESS_SYMBOL + index); // @ constant value of index
        fileWriter.println("D=A"); // Sets D register = index
        
        writePushBody();
    }
    
    // Push Local, Argument, This, or That's base address + index value
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
    
    public void writePushTemp(int index)
    {
        // Acquire value at temp base address + index
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.TEMP_REGISTERS[index]);
        fileWriter.println("D=M");
        
        writePushBody();
    }
    
    public void writePushStatic(int index)
    {
        // Acquire value at static index
        fileWriter.println(ADDRESS_SYMBOL + adjustExtension(getRealName(fileName), PERIOD + index));
        fileWriter.println("D=M");
        
        writePushBody();
    }
    
    public void writePushPointer(int index)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.POINTER_REGISTERS[index]);
        fileWriter.println("D=M");
        
        writePushBody();
    }
    
    public void writePushBody()
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
    
    public void writePopTemp(String symbol, int index)
    {
        writePopHeader();
        
        // Move value to temp segment's base address + index
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.TEMP_REGISTERS[index]);
        fileWriter.println("M=D");
    }
    
    public void writePopStatic(int index)
    {
        writePopHeader();
        
        // Move value to static index
        fileWriter.println(ADDRESS_SYMBOL + adjustExtension(getRealName(fileName), PERIOD + index));
        fileWriter.println("M=D");
    }
    
    public void writePopPointer(int index)
    {
        writePopHeader();
        
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.POINTER_REGISTERS[index]);
        fileWriter.println("M=D");
        
    }
    
    public void writePopHeader()
    {
        // Acquire value ontop of the stack
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M");
    }
    
    public void writeLabel(String labelName)
    {
        fileWriter.println(generateLabel(labelName));
    }
    
    public void writeGoto(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + labelName);
        fileWriter.println("0;JMP");
    }
    
    public void writeIfGoto(String labelName)
    {
        fileWriter.println(ADDRESS_SYMBOL + PointerTable.STACK_SYMBOL);
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M");
        
        fileWriter.println(ADDRESS_SYMBOL + labelName);
        fileWriter.println("D;JNE");
    }
    
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
    
    public static String adjustExtension(File path, String extension)
    {
        return adjustExtension(path.getAbsolutePath(), extension);
    }
    
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
    
    public static String getRealName(File path)
    {
        return getRealName(path.getAbsolutePath());
    }
    
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
    
    public String generateLabel(String labelName)
    {
        return LABEL_START + labelName + LABEL_END;
    }
    
    public String generateLabelSymbol(String labelName)
    {
        return labelName;
    }
    
    public String generateEndLabel(String tag)
    {
        return LABEL_START + END_OF_OPERATION_LABEL + tag + LABEL_END;
    }
    
    public String generateEndLabelSymbol(String tag)
    {
        return END_OF_OPERATION_LABEL + tag;
    }
    
    public void incrementLabelCounter()
    {
        ++labelCounter;
    }
}