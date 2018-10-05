// Removes all comments and white space from the input stream and breaks
// it into Jack-language tokens, as specified by the Jack grammar

public class JackTokenizer
{
    
    // Constructors
    
    // Opens the input filem/stream and gets ready to tokenize it
    public JackTokenizer()
    {
        
    }
    
    // Methods
    
    // Returns true if the file has more tokens
    // Otherwise, returns false
    public boolean hasMoreTokens()
    {
        return false;
    }
    
    // Gets the next token from the input and makes it the current token.
    // This method should only be called if hasMoreTokens() is true.
    // Initially, there is no current token set.
    public void advance()
    {
        
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