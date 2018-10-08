// Removes all comments and white space from the input stream and breaks
// it into Jack-language tokens, as specified by the Jack grammar

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class JackTokenizer
{
    // Instance variabless
    private Scanner fileScanner;
    private String fileName, currentToken, currentKeyWord;
    private int programCounter;
    
    // Constructors
    
    // Opens the input file/stream and gets ready to tokenize it
    public JackTokenizer(File path)
    {
        initialize(path);
    }
    
    // Opens the input file/stream and gets ready to tokenize it
    public JackTokenizer(String path)
    {
        initialize(new File(path));
    }
    
    // Methods
    
    private void initialize(File path)
    {
        try
        {
            fileScanner = new Scanner(path);
            fileName = path.getAbsolutePath();
            programCounter = 0;
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    // Returns true if the file has more tokens
    // Otherwise, returns false
    public boolean hasMoreTokens()
    {
        return fileScanner.hasNext();
    }
    
    // Gets the next token from the input and makes it the current token.
    // This method should only be called if hasMoreTokens() is true.
    // Initially, there is no current token set.
    public void advance()
    {
        currentToken = fileScanner.next();
    }
    
    // TODO: Decide on token type
    /*
    // Returns the type of the current token.
    public KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST tokenType()
    {
        
    }
    */
    
    // TODO: Decide on token keyword type
    /*
    // Returns the keyword which is the current token.
    // Should be called only when tokenType() is KEYWORD.
    public CLASS, METHOD, FUNCTION, CONSTRUCTOR, INT, BOOLEAN, CHAR, VOID, VAR, STATIC, FIELD, LET, DO, IF, ELSE, WHILE, RETURN, TRUE, FALSE, NULL, THIS keyWord()
    {
        
    }
    */
    
    // Returns the character which is the current token.
    // Should be called only when tokenType() is SYMBOL.
    public char symbol()
    {
        return '\0';
    }
    
    // Returns the identifier which is the current token.
    // Should be called only when tokenType() is IDENTIFIER.
    public String identifier()
    {
        return null;
    }
    
    // Returns the integer value of the current token.
    // Should be called only when tokenType() is INT_CONST
    public int intVal()
    {
        return -1;
    }
    
    // Returns the String value of the current token, without the double quotes.
    // Should be called only when tokenType() is STRING_CONST
    public String stringVal()
    {
        return null;
    }
}