// LEXICAL ELEMENTS:
//
//    1. KEYWORDS:
//
//          class, constructor, function, method, field, static, var, int,
//          char, boolean, void, true, false, null, this, let, do, if,
//          else, while, return
//
//    2. SYMBOLS:
//
//          '{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-',
//          '*', '/', '&', '|', '<', '>', '=', '~'
//
//    3. INTEGER CONSTANTS:
//
//          A decimal number in the range 0 .. 23767.
//
//    4. STRING CONSTANTS:
//
//          '"' A sequence of Unicode characters not including
//          double quote or newline '"'
//
//    5. IDENTIFIERS:
//
//          A sequence of letters, digits, and underscore ('_') not
//          starting with a digit.
import java.util.HashMap;

public class TokenTable
{
    // Tokens
    public static final String KEYWORD = "keyword", SYMBOL = "symbol",
                               IDENTIFIER = "identifier",
                               INT_CONST = "integerConstant",
                               STRING_CONST = "stringConstant",
                               INVALID_TOKEN = "invalidToken";
                               
    // Key words
    public static final String CLASS = "class", METHOD = "method",
                               FUNCTION = "function",
                               CONSTRUCTOR = "constructor", INT = "int",
                               BOOLEAN = "boolean", CHAR = "char",
                               VOID = "void", VAR = "var", STATIC = "static",
                               FIELD = "field", LET = "let", DO = "do",
                               IF = "if", ELSE = "else", WHILE = "while",
                               RETURN = "return", TRUE = "true",
                               FALSE = "false", NULL = "null", THIS = "this";
                               
    // Symbols
    public static final String LEFT_BRACES = "{", RIGHT_BRACES = "}",
                               LEFT_PARENTHESIS = "(", RIGHT_PARENTHESIS = ")",
                               LEFT_BRACKET = "[", RIGHT_BRACKET = "]",
                               PERIOD = ".", COMMA = ",", SEMICOLON = ";",
                               PLUS = "+", DASH = "-", ASTERISK = "*",
                               SLASH = "/", AMPERSAND = "&", PIPE = "|",
                               LEFT_CHEVRON = "<", RIGHT_CHEVRON = ">",
                               EQUAL = "=", TILDE = "~";
    
    // Instance variables
    private HashMap<String, String> keyWordMap, symbolMap;
    
    // Constructors
    public TokenTable()
    {
        initializeKeyWordMap();
        initializeSymbolMap();
    }
    
    // Methods
    
    private void initializeSymbolMap()
    {
        symbolMap = new HashMap<String, String>();
        
        symbolMap.put(LEFT_BRACES, SYMBOL);
        symbolMap.put(RIGHT_BRACES, SYMBOL);
        symbolMap.put(LEFT_PARENTHESIS, SYMBOL);
        symbolMap.put(RIGHT_PARENTHESIS, SYMBOL);
        symbolMap.put(LEFT_BRACKET, SYMBOL);
        symbolMap.put(RIGHT_BRACKET, SYMBOL);
        symbolMap.put(PERIOD, SYMBOL);
        symbolMap.put(COMMA, SYMBOL);
        symbolMap.put(SEMICOLON, SYMBOL);
        symbolMap.put(PLUS, SYMBOL);
        symbolMap.put(DASH, SYMBOL);
        symbolMap.put(ASTERISK, SYMBOL);
        symbolMap.put(SLASH, SYMBOL);
        symbolMap.put(AMPERSAND, SYMBOL);
        symbolMap.put(PIPE, SYMBOL);
        symbolMap.put(LEFT_CHEVRON, SYMBOL);
        symbolMap.put(RIGHT_CHEVRON, SYMBOL);
        symbolMap.put(EQUAL, SYMBOL);
        symbolMap.put(TILDE, SYMBOL);
    }
    
    private void initializeKeyWordMap()
    {
        keyWordMap = new HashMap<String, String>();
        
        keyWordMap.put(CLASS, KEYWORD);
        keyWordMap.put(METHOD, KEYWORD);
        keyWordMap.put(FUNCTION, KEYWORD);
        keyWordMap.put(CONSTRUCTOR, KEYWORD);
        keyWordMap.put(INT, KEYWORD);
        keyWordMap.put(BOOLEAN, KEYWORD);
        keyWordMap.put(CHAR, KEYWORD);
        keyWordMap.put(VOID, KEYWORD);
        keyWordMap.put(VAR, KEYWORD);
        keyWordMap.put(STATIC, KEYWORD);
        keyWordMap.put(FIELD, KEYWORD);
        keyWordMap.put(LET, KEYWORD);
        keyWordMap.put(DO, KEYWORD);
        keyWordMap.put(IF, KEYWORD);
        keyWordMap.put(ELSE, KEYWORD);
        keyWordMap.put(WHILE, KEYWORD);
        keyWordMap.put(RETURN, KEYWORD);
        keyWordMap.put(TRUE, KEYWORD);
        keyWordMap.put(FALSE, KEYWORD);
        keyWordMap.put(NULL, KEYWORD);
        keyWordMap.put(THIS, KEYWORD);
    }
    
    public boolean contains(String key)
    {
        return isSymbol(key) | isKeyWord(key);
    }
    
    public String get(String key)
    {
        if(isSymbol(key))
        {
            return getSymbol(key);
        }
        else if(isKeyWord(key))
        {
            return getKeyWord(key);
        }
        else
        {
            return INVALID_TOKEN;
        }
    }
    
    public boolean isSymbol(String key)
    {
        return symbolMap.containsKey(key);
    }
    
    public String getSymbol(String key)
    {
        return symbolMap.get(key);
    }
    
    public boolean isKeyWord(String key)
    {
        return keyWordMap.containsKey(key);
    }
    
    public String getKeyWord(String key)
    {
        return keyWordMap.get(key);
    }
}