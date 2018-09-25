// Translates VM commands into Hack assembly code.

public class CodeWriter
{
    private String fileName;
    
    // Constructor
    public CodeWriter()
    {
        
    }
    
    // Informs the code writer that the translation of a
    // new VM file is started.
    public void setFileName(String name)
    {
        fileName = name;
    }
    
    // Writes the assembly code that is the translation
    // of the given arithmetic command.
    public void writeArithmetic(String command)
    {
        return null;
    }
    
    // Writes the assembly code that is the translation
    // of the given command, where command is either
    // C_PUSH or C_POP.
    /*
    public void writePushPop(COMMANDTYPE something, String segment, int index)
    {
        
    }
    */
    
    // Closes the output file.
    public void close()
    {
        
    }
}