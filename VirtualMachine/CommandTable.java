import java.util.HashMap;

public final class CommandTable
{
    // Constants
    
    // C_COMMANDS
    public static final String C_ADDITION = "add", C_SUBTRACT = "sub",
                               C_NEGATE = "neg", C_EQUAL = "eq",
                               C_GREATER = "gt", C_LESSER = "lt", C_AND = "and",
                               C_OR = "or", C_NOT = "not", C_POP = "pop",
                               C_PUSH = "push", C_LABEL = "label",
                               C_GOTO = "goto", C_IF_GOTO = "if-goto",
                               C_FUNCTION = "function", C_CALL = "call",
                               C_RETURN = "return", C_INVALID = "Invalid";
    // C_COMMAND types
    public static final String C_ARITHMETIC_TYPE = "C_ARITHMETIC",
                               C_POP_TYPE = "C_POP", C_PUSH_TYPE = "C_PUSH",
                               C_LABEL_TYPE = "C_LABEL", C_GOTO_TYPE = "C_GOTO",
                               C_IF_GOTO_TYPE = "C_IF_GOTO",
                               C_FUNCTION_TYPE = "C_FUNCTION",
                               C_CALL_TYPE = "C_CALL",
                               C_RETURN_TYPE = "C_RETURN",
                               C_INVALID_TYPE = "C_INVALID";
    
    // Singleton
    private static CommandTable commander;
    
    // Instance variables
    private HashMap<String, String> commandMap;
    
    // Constructor
    private CommandTable()
    {
        populateCommander();
    }
    
    // Returns singleton object
    public static CommandTable getCommandTable()
    {
        if(commander == null)
        {
            commander = new CommandTable();
        }
        
        return commander;
    }
    
    // Initializes commandMap instance variable with course defined presets
    // All commands: 
    // add, sub, neg, eq, gt, lt, and, or, not, pop, push, ...
    // All command types:
    // C_ARITHMETIC, C_PUSH, C_POP, C_LABEL, C_GOTO, C_IF, C_FUNCTION,
    // C_RETURN, C_CALL
    private void populateCommander()
    {
        commandMap = new HashMap<String, String>();
        
        // C_ARITHMETIC types
        commandMap.put(C_ADDITION, C_ARITHMETIC_TYPE);
        commandMap.put(C_SUBTRACT, C_ARITHMETIC_TYPE);
        commandMap.put(C_NEGATE  , C_ARITHMETIC_TYPE);
        commandMap.put(C_EQUAL   , C_ARITHMETIC_TYPE);
        commandMap.put(C_GREATER , C_ARITHMETIC_TYPE);
        commandMap.put(C_LESSER  , C_ARITHMETIC_TYPE);
        commandMap.put(C_AND     , C_ARITHMETIC_TYPE);
        commandMap.put(C_OR      , C_ARITHMETIC_TYPE);
        commandMap.put(C_NOT     , C_ARITHMETIC_TYPE);
        
        // C_POP type
        commandMap.put(C_POP, C_POP_TYPE);
        
        // C_PUSH type
        commandMap.put(C_PUSH, C_PUSH_TYPE);
        
        // Program flow commands
        commandMap.put(C_LABEL  , C_LABEL_TYPE);
        commandMap.put(C_GOTO   , C_GOTO_TYPE);
        commandMap.put(C_IF_GOTO, C_IF_GOTO_TYPE);
        
        // Function calling commands
        commandMap.put(C_FUNCTION, C_FUNCTION_TYPE);
        commandMap.put(C_CALL    , C_CALL_TYPE);
        commandMap.put(C_RETURN  , C_RETURN_TYPE);
    }
    
    // Returns the String value associated with String parameter command
    // within instance variable commandMap
    public String getCommandType(String command)
    {
        if(command != null && commandMap.containsKey(command))
        {
            return commandMap.get(command);
        }
        else
        {
            return C_INVALID;
        }
    }
}