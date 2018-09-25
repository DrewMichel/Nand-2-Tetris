// Encapsulates access to the input code. Reads an assembly language
// command, parses it, and provides convenient access to the
// command's components (fields and symbols). In addition, removes
// all white space and comments.

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Parser
{
    public static final char A_COMMAND = 'A', C_COMMAND = 'C', L_COMMAND = 'L', UNKNOWN_COMMAND = 'U';
    public static final String AT_ADDRESS = "@", LABEL_START = "(", LABEL_END = ")", PERIOD = ".", DEFAULT_EXTENSION = ".hack";
    
    private String fileName, outName, currentCommand, outCommand;
    private Scanner fileScanner;
    private Code singletonCode;
    private char currentType;
    private int programCounter, passCounter;
    private PrintWriter outputFile;
    
    public Parser(File inputFile)
    {
        try
        {
            fileName = inputFile.getAbsolutePath();
            fileScanner = new Scanner(new FileInputStream(fileName));
            outName = fileName.substring(0, fileName.lastIndexOf(PERIOD)) + DEFAULT_EXTENSION;
            outputFile = new PrintWriter(outName);
            
            singletonCode = Code.getCode();
            currentType = UNKNOWN_COMMAND;
            resetProgramCounter();
            passCounter = 0;
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Pass Counter: " + passCounter + "\n" + e.getMessage());
            System.exit(0);
        }
    }
    
    public Parser(String inputFile)
    {
        try
        {
            fileScanner = new Scanner(new FileInputStream(inputFile));
            fileName = inputFile;
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public void setOutCommand(String out)
    {
        outCommand = out;
    }
    
    public void write()
    {
        outputFile.println(outCommand);
    }
    
    public void resetScanner()
    {
        try
        {
            fileScanner = new Scanner(new FileInputStream(fileName));
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Pass Counter: " + passCounter + "\n" + e.getMessage());
            System.exit(0);
        }
    }
    
    public void closeStream()
    {
        if(outputFile != null)
        {
            outputFile.close();
        }
    }
    
    public void incrementPassCounter()
    {
        passCounter++;
    }
    
    public void resetProgramCounter()
    {
        programCounter = 0;
    }
    
    public String getCurrentCommand()
    {
        return currentCommand;
    }
    
    public int getProgramCounter()
    {
        return programCounter;
    }
    
    public void incrementProgramCounter()
    {
        ++programCounter;
    }
    
    // Returns true if the input contains more commands.
    public boolean hasMoreCommands()
    {
        return fileScanner.hasNextLine();
    }
    
    // Reads the next command from the input and makes it the
    // current command.
    // Should be called only if hasMoreCommands() is true.
    // Initially there is no current command.
    public void advance()
    {
        //programCounter++;
        
        currentCommand = fileScanner.nextLine();
        
        currentCommand = clearWhitespace(clearCommentation(currentCommand));
    }
    
    // Returns the type of the current command:
    //
    //      A_COMMAND for @Xxx where Xxx is either a
    //      symbol or a decimal number
    //
    //      C_COMMAND for dest = comp; jump
    //
    //      L_COMMAND (actually, pseudo-command) for (Xxx)
    //      where Xxx is a symbol
    public char commandType()
    {
        if(currentCommand.startsWith(AT_ADDRESS))
        {
            currentType = A_COMMAND;
        }
        else if(singletonCode.isCommand(currentCommand))
        {
            currentType = C_COMMAND;
        }
        else if(currentCommand.startsWith(LABEL_START) && currentCommand.endsWith(LABEL_END))
        {
            currentType = L_COMMAND;
        }
        else
        {
            currentType = UNKNOWN_COMMAND;
        }
        
        return currentType;
    }
    
    public char getCurrentType()
    {
        return currentType;
    }
    
    public int getPassCounter()
    {
        return passCounter;
    }
    
    // Returns the symbol or decimal Xxx of the current
    // command @Xxx (or Xxx).
    // Should be called only when commandType is
    // A_COMMAND or L_COMMAND.
    public String symbol(String input)
    {
        if(currentType == L_COMMAND)
        {
            return input.substring(1, input.length() - 1); // removes '(' and ')'
        }
        else // if(currentType == A_COMMAND)
        {
            return input.substring(1); // removes '@'
        }
    }
    
    // Returns the dest mnemonic in the current C_COMMAND
    // (8 possibilities).
    // Should be called only when commandType() is C_COMMAND.
    public String dest(String input)
    {
        String output = "000";
        
        int index = -1;
        
        if(input != null && ((index = input.indexOf(Code.EQUALS_SYMBOL)) > 0))
        {
            output = singletonCode.dest(input.substring(0, index));
        }
        
        return output;
    }
    
    // Returns the comp mnemonic in the current C_COMMAND
    // (28 possibilities).
    // Should be called only when commandType() is C_COMMAND.
    public String comp(String input)
    {
        String output = "FALCMP";
        
        int index = -1;
        
        if(input != null && input.length() > index + 1)
        {
            if(((index = input.indexOf(Code.EQUALS_SYMBOL)) > 0))
            {
                output = singletonCode.comp(input.substring(index + 1));
            }
            else if(((index = input.indexOf(Code.JUMP_SYMBOL)) > 0))
            {
                output = singletonCode.comp(input.substring(0, index));
            }
        }
        
        return output;
    }
    
    // Returns the jump mnemonic in the current C_COMMAND
    // (8 possibilities).
    // Should be called only when commandType() is C_COMMAND.
    public String jump(String input)
    {
        String output = "000";
        
        int index = -1;
        
        if(input != null && ((index = input.indexOf(Code.JUMP_SYMBOL)) > 0 ) && input.length() > index + 1)
        {
            output = singletonCode.jump(input.substring(index + 1));
        }
        
        return output;
    }
    
    public static String clearCommentation(String original)
    {
        String output = original;
        
        if(original != null && original.contains("//"))
        {
            output = original.substring(0, original.indexOf("//"));
        }
        
        return output;
    }
    
    public static String clearWhitespace(String original)
    {
        StringBuilder builder = new StringBuilder();
        
        if(original != null)
        {
            int length = original.length();
            char currentChar;
            
            for(int i = 0; i < length; ++i)
            {
                currentChar = original.charAt(i);
                
                if(!Character.isSpace(currentChar))
                {
                    builder.append(currentChar);
                }
            }
        }
        
        return builder.toString();
    }
    
    public static boolean isValidSymbol(String symbol)
    {
        return (symbol != null && Character.isAlphabetic(symbol.charAt(0)));
    }
    
    public static boolean onlyDigits(String symbol)
    {
        if(symbol != null)
        {
            for(int i = 0; i < symbol.length(); ++i)
            {
                if(!Character.isDigit(symbol.charAt(i)))
                {
                    return false;
                }
            }
        }
        
        return symbol.length() > 0;
    }
    
    public static String ensureLength(String original, boolean cCommand)
    {
        
        if(original.length() >= Code.BUS_WIDTH)
        {
            return original;
        }
        else
        {
            StringBuilder modified = new StringBuilder();
            
            if(cCommand)
            {
                modified.append(Code.MOST_SIGNIFICANT_BITS);
            }
            
            for(int i = modified.length(); i < Code.BUS_WIDTH - original.length(); i++)
            {
                modified.append("0");
            }
            
            return modified.toString() + original;
        }
    }
}