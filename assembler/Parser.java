// Encapsulates access to the input code. Reads an assembly language
// command, parses it, and provides convenient access to the
// command's components (fields and symbols). In addition, removes
// all white space and comments.

import java.io.FileInputStream;
import java.util.Scanner;

public class Parser
{
    public static final char A_COMMAND = 'A', C_COMMAND = 'C', L_COMMAND = 'L';
    
    private String fileName;
    private Scanner fileScanner;
    
    public Parser(String inputFile)
    {
        fileName = inputFile;
        fileScanner = new Scanner(new FileInputStream(fileName));
    }
    
    // Returns true if the input contains more commands.
    public boolean hasMoreCommands()
    {
        return false;
    }
    
    // Reads the next command from the input and makes it the
    // current command.
    // Should be called only if hasMoreCommands() is true.
    // Initially there is no current command.
    public boolean advance()
    {
        return false;
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
        return '0';
    }
    
    // Returns the symbol or decimal Xxx of the current
    // command @Xxx (or Xxx).
    // Should be called only when commandType is
    // A_COMMAND or L_COMMAND.
    public String symbol(String input)
    {
        return null;
    }
    
    // Returns the dest mnemonic in the current C_COMMAND
    // (8 possibilities).
    // Should be called only when commandType() is C_COMMAND.
    public String dest()
    {
        return null;
    }
    
    // Returns the comp mnemonic in the current C_COMMAND
    // (28 possibilities).
    // Should be called only when commandType() is C_COMMAND.
    public String comp()
    {
        return null;
    }
    
    // Returns the jump mnemonic in the current C_COMMAND
    // (8 possibilities).
    // Should be called only when commandType() is C_COMMAND.
    public String jump()
    {
        return null;
    }
}