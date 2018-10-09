// Removes all comments and white space from the input stream and breaks
// it into Jack-language tokens, as specified by the Jack grammar

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

public class JackTokenizer
{
    // TODO: XML CHANGE TO JXML TO AVOID OVERWRITTING COURSEWORK FILES, REVERT TO XML
    public static final String DEFAULT_OUTPUT_EXTENSION = ".jxml",
                               DEFAULT_INPUT_EXTENSION = ".jack",
                               TOKEN_INDICATOR = "tokens",
                               LINE_COMMENT_INDICATOR = "//",
                               BLOCK_COMMENT_START = "/*",
                               BLOCK_COMMENT_END = "*/",
                               DOCUMENT_COMMENT_START = "/**";
    
    // Instance variables
    private TokenTable tokenTable;
    private Scanner fileScanner, lineScanner;
    private PrintWriter fileWriter;
    private String fileNameIn, fileNameOut, currentToken, currentTokenType, currentValue, currentLine;
    private int programCounter;
    private boolean initialized, inBlockComment, inAPIComment, inString;
    
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
            fileNameIn = path.getAbsolutePath();
            fileNameOut = TokenTable.adjustExtension(fileNameIn, DEFAULT_OUTPUT_EXTENSION);
            fileWriter = new PrintWriter(fileNameOut);
            programCounter = 0;
            tokenTable = new TokenTable();
            inBlockComment = false;
            inAPIComment = false;
            inString = false;
            initialized = true;
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage());
            initialized = false;
        }
    }
    
    // Returns true if the file has more tokens
    // Otherwise, returns false
    public boolean hasMoreTokens()
    {
        return initialized && fileScanner.hasNext();
    }
    
    // Gets the next token from the input and makes it the current token.
    // This method should only be called if hasMoreTokens() is true.
    // Initially, there is no current token set.
    public void advance()
    {
        int index = 0, commentIndex = -1;
        String currentChar;
        
        currentLine = fileScanner.nextLine();
        
        // Check for block comment ending
        
        currentLine = eliminateComments(currentLine);
        
        // Comments dealt with, process current line

        lineScanner = new Scanner(currentLine);
        
        StringBuilder stringLines = new StringBuilder(currentLine.length());
        
        while(lineScanner.hasNext())
        {
            currentValue = lineScanner.next();
            StringBuilder builder = new StringBuilder(currentValue.length());
            
            for(int i = 0; i < currentValue.length(); ++i)
            {
                currentChar = currentValue.substring(i, i + 1);
                
                if(inString == true && currentChar.equals(TokenTable.DOUBLE_QUOTE))
                {
                    fileWriter.println(inlineToken(TokenTable.STRING_CONST, stringLines.toString()));
                    inString = false;
                    
                    stringLines = new StringBuilder(currentLine.length());
                    
                    lineScanner.reset();
                }
                else if(currentChar.equals(TokenTable.DOUBLE_QUOTE))
                {
                    inString = true;
                    
                    lineScanner.useDelimiter("\\n");
                }
                else if(inString)
                {
                    stringLines.append(currentChar);
                }
                else if(tokenTable.isSymbol(currentChar))
                {
                    if(builder.length() > 0)
                    {
                        currentToken = builder.toString();
                        currentTokenType = tokenTable.get(currentToken);
                    
                        fileWriter.println(inlineToken(currentTokenType, currentToken));
                    }
                    
                    if(tokenTable.needsModification(currentChar))
                    {
                        fileWriter.println(inlineToken(tokenTable.get(currentChar), tokenTable.getModification(currentChar)));
                    }
                    else
                    {
                        fileWriter.println(inlineToken(tokenTable.get(currentChar), currentChar));
                    }
                    
                    builder = new StringBuilder(currentValue.length());
                }
                else if(!Character.isWhitespace(currentChar.charAt(0)))
                {
                    builder.append(currentChar);
                }
            }
            
            if(builder.length() > 0)
            {
                currentToken = builder.toString();
                currentTokenType = tokenTable.get(currentToken);
                
                fileWriter.println(inlineToken(currentTokenType, currentToken));
            }
        }
    }
    
    private String eliminateComments(String line)
    {
        int lineIndex = -1, blockIndex = -1, docIndex = -1;
        
        if(inAPIComment || inBlockComment)
        {
            int end = line.indexOf(BLOCK_COMMENT_END);
            
            if(end > -1)
            {
                inAPIComment = false;
                inBlockComment = false;
                
                if(line.length() > end + 2)
                {
                    line = line.substring(end + 2);
                    
                    line = eliminateComments(line);
                }
                else
                {
                    line = "";
                }
            }
            else
            {
                line = "";
            }
        }
        else if(!inAPIComment && !inBlockComment && ((lineIndex = line.indexOf(LINE_COMMENT_INDICATOR)) > -1 | (blockIndex = line.indexOf(BLOCK_COMMENT_START)) > -1 | (docIndex = line.indexOf(DOCUMENT_COMMENT_START)) > -1))
        {
            if(lineIndex > -1 && (lineIndex < blockIndex || blockIndex < 0))
            {
                line = line.substring(0, lineIndex);
            }
            else if(blockIndex < docIndex || (blockIndex > -1 && docIndex < 0))
            {
                inBlockComment = true;
                line = merge(line, blockIndex);
                
                line = eliminateComments(line);
            }
            else if(docIndex > -1)
            {
                inAPIComment = true;
                line = merge(line, docIndex);
                
                line = eliminateComments(line);
            }
        }
        
        return line;
    }
    
    private String merge(String line, int index)
    {
        StringBuilder builder = new StringBuilder(line.substring(0, index));
        
        String after = "";
        
        if(line.length() > index + 2)
        {
            after = line.substring(index + 2);
            
            int indexOf = after.indexOf(BLOCK_COMMENT_END);
            
            if(indexOf > -1)
            {
                inBlockComment = false;
                inAPIComment = false;
                
                if(after.length() > indexOf + 2)
                {
                    builder.append(after.substring(indexOf + 2));
                }
            }
        }
        
        
        return builder.toString();
    }

    // Returns the type of the current token.
    public String tokenType()
    {
        return currentTokenType;
    }
    

    // Returns the keyword which is the current token.
    // Should be called only when tokenType() is KEYWORD.
    public String keyWord()
    {
        return currentToken;
    }

    // Returns the character which is the current token.
    // Should be called only when tokenType() is SYMBOL.
    public String symbol()
    {
        return currentToken;
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
    
    public void writeFileHeader()
    {
        fileWriter.println(beginToken(TOKEN_INDICATOR));
    }
    
    public void writeFileFooter()
    {
        fileWriter.println(endToken(TOKEN_INDICATOR));
    }
    
    public void close()
    {
        if(fileScanner != null)
        {
            fileScanner.close();
        }
        if(fileWriter != null)
        {
            fileWriter.close();
        }
        
        initialized = false;
    }
    
    public static String beginToken(String value)
    {
        return TokenTable.LEFT_CHEVRON + value + TokenTable.RIGHT_CHEVRON;
    }
    
    public static String endToken(String value)
    {
        return TokenTable.LEFT_CHEVRON + TokenTable.SLASH + value + TokenTable.RIGHT_CHEVRON;
    }
    
    public static String inlineToken(String tokenType, String value)
    {
        return beginToken(tokenType) + " " + value + " " + endToken(tokenType);
    }
    
    public static boolean validExtension(File path, String extension)
    {
        return validExtension(path.getAbsolutePath(), extension);
    }
    
    public static boolean validExtension(String path, String extension)
    {
        int index = -1;
        
        if(path == null || path.length() < 1)
        {
            return false;
        }
        
        index = path.lastIndexOf(TokenTable.PERIOD);
        
        return index > -1 && path.substring(index).equalsIgnoreCase(extension);
    }
}