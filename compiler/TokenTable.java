// LEXICAL ELEMENTS:
//
//    The Jack language includes five types of terminal elements (tokens):
//
//    1. KEYWORDS:
//
//          'class', 'constructor', 'function', 'method', 'field', 'static',
//          'var', 'int', 'char', 'boolean', 'void', 'true', 'false', 'null',
//          'this', 'let', 'do', 'if', 'else', 'while', 'return'
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
//
// PROGRAM STRUCTURE:
//
//    A Jack program is a collection of classes, each appearing in a
//    separate file. The compilation unit is a class. A class is a sequence of
//    tokens structured according to the following context free syntax:
//
//    1. CLASS: 'class' className '{' classVarDec* subroutineDec* '}'
//
//    2. CLASS VAR DEC: ('static' or 'field') type varName (',' varName)* ';'
//
//    3. TYPE: 'int' or 'char' or 'boolean' or className
//
//    4. SUBROUTINE DEC: ('constructor' or 'function' or 'method')
//                       ('void' or type) subroutineName '(' parameterList ')'
//                       subroutineBody
//
//    5. PARAMETER LIST: ((type varName) (',' type varName)*)?
//
//    6. SUBROUTINE BODY: '{' varDec* statements '}'
//
//    7. VAR DEC: 'var' type varName (',' varName)* ';'
//
//    8. CLASS NAME: identifier
//
//    9. SUBROUTINE NAME: identifier
//
//    10. VAR NAME: identifier
//
// STATEMENTS:
//
//    1. STATEMENTS: statement*
//
//    2. STATEMENT: letStatement or ifStatement or whileStatement
//                  or doStatement or returnStatement
//
//    3. LET STATEMENT: 'let' varName ('[' expression ']')? '=' expression ';'
//
//    4. IF STATEMENT: 'if '(' expression ')' '{' statements '}'
//                     ('else' '{' statements '}')?
//
//    5. WHILE STATEMENT: 'while' '(' expression ')' '{' statements '}'
//
//    6. DO STATEMENT: 'do' subroutineCall ';'
//
//    7. RETURN STATEMENT: 'return' expression? ';'
//
// EXPRESSIONS:
//
//    1. EXPRESSION: term (op term)*
//
//    2. TERM: integerConstant or stringConstant or keywordConstant or varName
//             or varName '[' expression ']' or subroutineCall
//             or '(' expression ')' unaryOp term
//
//    3. SUBROUTINE CALL: subroutineName '(' expressionList ')'
//                        or (className or varName) '.' subroutineName
//                        '(' expressionList ')'
//
//    4. EXPRESSION LIST: (expression(',' expression)*)?
//
//    5. OP: '+' or '-' or '*' or '/' or '&' or '|' or '<' or '>' or '='
//
//    6. UNARY OP: '-' or '~'
//
//    7. KEYWORD CONSTANT: 'true' or 'false' or 'null' or 'this'

import java.util.HashMap;
import java.util.HashSet;
import java.io.File;

public class TokenTable
{
    // Tokens
    public static final String KEYWORD = "keyword", SYMBOL = "symbol",
                               IDENTIFIER = "identifier",
                               INT_CONST = "integerConstant",
                               STRING_CONST = "stringConstant";
                               
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
                               
    // Identifier character
    public static final String UNDERSCORE = "_";
    
    // String constant beginning and end character
    public static final String DOUBLE_QUOTE = "\"";
    
    // XML acceptable characters
    public static final String XML_LESSER = "&lt;", XML_GREATER = "&gt;", XML_QUOTE = "&quot;", XML_AND = "&amp;";
    
    // String flags
    public static final String INVALID_TOKEN = "invalidToken",
                               UNARY_OPERATOR = "unaryOperator",
                               OPERATOR = "operator",
                               MULTI_OPERATOR = "multiOperator";
    
    // Instance variables
    private HashMap<String, String> keyWordMap, symbolMap, modifiedMap;
    private HashSet<String> operatorSet, unaryOperatorSet;
    
    // Constructors
    public TokenTable()
    {
        initializeKeyWordMap();
        initializeSymbolMap();
        initializeOperatorSet();
        initializeUnaryOperatorSet();
        initializeModifiedMap();
    }
    
    // Methods
    
    private void initializeUnaryOperatorSet()
    {
        unaryOperatorSet = new HashSet<String>();
        
        unaryOperatorSet.add(DASH);
        unaryOperatorSet.add(TILDE);
    }
    
    private void initializeModifiedMap()
    {
        modifiedMap = new HashMap<String, String>();
        
        modifiedMap.put(LEFT_CHEVRON, XML_LESSER);
        modifiedMap.put(RIGHT_CHEVRON, XML_GREATER);
        modifiedMap.put(DOUBLE_QUOTE, XML_QUOTE);
        modifiedMap.put(AMPERSAND, XML_AND);
    }
    
    private void initializeOperatorSet()
    {
        operatorSet = new HashSet<String>();
        
        operatorSet.add(PLUS);
        operatorSet.add(DASH);
        operatorSet.add(ASTERISK);
        operatorSet.add(SLASH);
        operatorSet.add(AMPERSAND);
        operatorSet.add(PIPE);
        operatorSet.add(LEFT_CHEVRON);
        operatorSet.add(RIGHT_CHEVRON);
        operatorSet.add(EQUAL);
    }
    
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
    
    public boolean needsModification(String key)
    {
        return modifiedMap.containsKey(key);
    }
    
    public String getModification(String key)
    {
        return modifiedMap.get(key);
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
        else if(isIntegerConstant(key))
        {
            return INT_CONST;
        }
        else if(isStringConstant(key))
        {
            return STRING_CONST;
        }
        else if(isIdentifier(key))
        {
            return IDENTIFIER;
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
    
    public boolean isOperator(String key)
    {
        return operatorSet.contains(key);
    }
    
    public boolean isUnaryOperator(String key)
    {
        return unaryOperatorSet.contains(key);
    }
    
    public static boolean isIntegerConstant(String value)
    {
        int length = 0;
        
        if(value == null || (length = value.length()) < 1)
        {
            return false;
        }
        
        for(int i = 0; i < length; ++i)
        {
            if(!Character.isDigit(value.charAt(i)))
            {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isIdentifier(String value)
    {
        int length = 0;
        char current;
        
        if(value == null || (length = value.length()) < 1)
        {
            return false;
        }
        
        current = value.charAt(0);
        
        if(!Character.isAlphabetic(current) && current != UNDERSCORE.charAt(0))
        {
            return false;
        }
        
        for(int i = 1; i < length; ++i)
        {
            current = value.charAt(i);
            
            if(!Character.isAlphabetic(current) && current != UNDERSCORE.charAt(0) && !Character.isDigit(current))
            {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isStringConstant(String value)
    {
        int length;
        
        if(value == null || (length = value.length()) < 2)
        {
            return false;
        }
        
        return value.charAt(0) == DOUBLE_QUOTE.charAt(0) && value.charAt(length - 1) == DOUBLE_QUOTE.charAt(0);
    }
    
    public static String adjustExtension(String path, String extension)
    {
        int length = 0;
        char current;
        
        if(path == null || (length = path.length()) < 1)
        {
            return path;
        }
        
        StringBuilder builder = new StringBuilder(length);
        
        for(int i = 0; i < length; ++i)
        {
            current = path.charAt(i);
            
            if(current == PERIOD.charAt(0))
            {
                builder.append(extension);
                return builder.toString();
            }
            else
            {
                builder.append(current);
            }
        }
        
        if(builder.length() > 0 && builder.charAt(builder.length() - 1) == File.separator.charAt(0))
        {
            builder.deleteCharAt(builder.length() - 1);
        }
        
        builder.append(extension);
        
        return builder.toString();
    }
}