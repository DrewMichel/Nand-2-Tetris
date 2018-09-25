// Handles the parsing of a single .vm file, and encapsulates access
// to the input code. It reads VM commands, parses them, and provides
// convenient access to their components. In addition, it removes all
// white space and comments.

public class Parser
{
    // hashmap, enums, or array for commandTypes
    
    // Instance variables
    
    // Constructor
    public Parser()
    {
        
    }
    
    // Returns true if the file contains additional lines, else false
    public boolean hasMoreCommands()
    {
        return false;
    }
    
    // Reads the next command from the input and makes it the current command.
    // Should be called only if hasMoreCommands is true.
    // Initially there is no current command.
    public void advance()
    {
        
    }
    
    // Returns the type of the current VM command.
    // C_ARITHMETIC is returned for all the arithmetic commands.
    /*
    public COMMANDTYPE commandType()
    {
        return something yo;
    }
    */
    
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
}