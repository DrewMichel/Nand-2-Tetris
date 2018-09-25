// Program entry point

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main
{
    // Constants
    private final static String[] ARGUMENT_FLAGS = {"-R"};
    private final static String[] VALID_EXTENSIONS = {".asm"};
    
    // Static members
    private static HashMap<String, Boolean> flagMap;
    
    // Methods
    
    // Main method
    public static void main(String[] args)
    {
        System.out.println("Assembler Beginning");
        
        populateFlagMap();
        
        ArrayList<File> regularFiles = new ArrayList<File>();
        
        // Prompts user to enter arguments via Scanner if none were entered
        // via command line
        if(args.length <= 1)
        {
            System.out.print("Enter filepath: ");
            args = receiveInput().split(" ");
        }
        
        // Adds files into ArrayList
        regularFiles = processArguments(regularFiles, args);
        
        System.out.println("Processing: " + regularFiles.size() + " files...");
        
        // Parses each file and creates a .hack for each one
        processFiles(regularFiles);
        
        System.out.println("Parsed: " + regularFiles.size() + " files");
        System.out.println("Assembler Ending");
    }
    
    // Receives an ArrayList of type File
    // Iterates over each file within the ArrayList and outputs a .hack
    // file based on each file's contents
    public static void processFiles(ArrayList<File> files)
    {
        Parser parser = null;
        SymbolTable table = null;
        
        for(int i = 0; i < files.size(); i++)
        {
            parser = new Parser(files.get(i));
            table = new SymbolTable();
            
            while(parser.getPassCounter() < 2)
            {
                // look for labels
                if(parser.getPassCounter() == 0)
                {
                    while(parser.hasMoreCommands())
                    {
                        parser.advance();
                        
                        parser.commandType();
                        
                        if(parser.getCurrentType() == Parser.L_COMMAND && Parser.isValidSymbol(parser.symbol(parser.getCurrentCommand()))) // found label
                        {
                            table.addEntry(parser.symbol(parser.getCurrentCommand()), parser.getProgramCounter());
                        }
                        else if(parser.getCurrentType() == Parser.A_COMMAND || parser.getCurrentType() == Parser.C_COMMAND)
                        {
                            parser.incrementProgramCounter();
                        }
                    }
                    
                    parser.incrementPassCounter();
                    parser.resetProgramCounter();
                    parser.resetScanner();
                }
                else // process each line
                {
                    while(parser.hasMoreCommands())
                    {
                        parser.advance();
                        
                        parser.commandType();
                        
                        if(parser.getCurrentType() == Parser.A_COMMAND) // found A command
                        {
                            String currentSymbol = parser.symbol(parser.getCurrentCommand());
                            
                            boolean valid = Parser.isValidSymbol(currentSymbol);
                            
                            parser.incrementProgramCounter();
                            
                            if(table.contains(currentSymbol))
                            {
                                parser.setOutCommand(Parser.ensureLength(Integer.toBinaryString(table.getAddress(currentSymbol)), false));
                                
                                parser.write();
                            }
                            else if(valid)
                            {
                                table.addEntry(currentSymbol, table.next());
                                
                                parser.setOutCommand(Parser.ensureLength(Integer.toBinaryString(table.getAddress(currentSymbol)), false));
                                
                                parser.write();
                            }
                            else if(Parser.onlyDigits(currentSymbol))
                            {
                                parser.setOutCommand(Parser.ensureLength(Integer.toBinaryString(Integer.parseInt(currentSymbol)), false));
                                
                                parser.write();
                            }
                            else
                            {
                                // just crash?
                            }
                        }
                        else if(parser.getCurrentType() == Parser.C_COMMAND) // found C command
                        {
                            String dest = parser.dest(parser.getCurrentCommand()), comp = parser.comp(parser.getCurrentCommand()), jump = parser.jump(parser.getCurrentCommand());
                            
                            parser.incrementProgramCounter();
                            
                            parser.setOutCommand(Code.MOST_SIGNIFICANT_BITS + comp + dest + jump);
                            
                            parser.write();
                        }
                    }
                    
                    parser.incrementPassCounter();
                }
            }
            
            System.out.println("Completed file #" + (i + 1));
            
            parser.closeStream();
        }
    }
    
    // Receives a line of input from the user and returns it
    public static String receiveInput()
    {
        return new Scanner(System.in).nextLine();
    }
    
    // Iterates over all files within the current directory level and adds them
    // to an ArrayList of files. If the -R flag has been set, recursively
    // adds all files within sub-folders.
    // Returns the ArrayList of files.
    private static ArrayList<File> accessDirectoryFiles(ArrayList<File> directories, ArrayList<File> regularFiles)
    {
        ArrayList<File> innerDirectories = new ArrayList<File>();
        
        for(int i = 0; i < directories.size(); i++)
        {
            File[] innerFiles = directories.get(i).listFiles();
            
            for(int k = 0; k < innerFiles.length; k++)
            {
                if(innerFiles[k].isDirectory())
                {
                    innerDirectories.add(innerFiles[k]);
                }
                else if(innerFiles[k].isFile() && isValidExtension(innerFiles[k]))
                {
                    regularFiles.add(innerFiles[k]);
                }
            }
        }
        
        if(flagMap.get(ARGUMENT_FLAGS[0]) && innerDirectories.size() > 0)
        {
            accessDirectoryFiles(innerDirectories, regularFiles);
        }
        
        return regularFiles;
    }

    // Processes arguments to seperate valid elements into directories, files,
    // or flags, then moves any files within directories into files. If -R
    // flag has been set, Will recursively move all files within sub-folders.
    // Returns an ArrayList containing files.
    private static ArrayList<File> processArguments(ArrayList<File> regularFiles, String[] arguments)
    {
        ArrayList<File> directories = new ArrayList<File>();
        
        arguments = toUpperCaseArray(arguments);
        
        processElements(directories, regularFiles, arguments);
        
        accessDirectoryFiles(directories, regularFiles);
        
        return regularFiles;
    }
    
    // Processes arguments to separate valid elements into directories, files,
    // or flags
    private static void processElements(ArrayList<File> directories, ArrayList<File> regularFiles, String[] arguments)
    {
        for(int i = 0; i < arguments.length; i++)
        {
            File currentFile = new File(arguments[i]);
            
            if(currentFile.exists())
            {
                if(currentFile.isDirectory())
                {
                    directories.add(currentFile);
                }
                else if(currentFile.isFile() && isValidExtension(currentFile))
                {
                    regularFiles.add(currentFile);
                }
            }
            else if(isFlag(currentFile))
            {
                flagMap.put(currentFile.getName(), true);
            }
        }
    }
    
    // Iterates over String array parameter and uppercases each element
    // Also returns the same array
    public static String[] toUpperCaseArray(String[] array)
    {
        if(array != null)
        {
            for(int i = 0; i < array.length; i++)
            {
                array[i] = array[i].toUpperCase();
            }
        }
        
        return array;
    }
    
    // Returns true if the File parameter value has a valid file extension
    // Otherwise returns false
    private static boolean isValidExtension(File value)
    {
        String name = value.getName();
        
        if(name.contains("."))
        {
            String extension = name.substring(name.lastIndexOf(".")).toLowerCase();
            
            for(int i = 0; i < VALID_EXTENSIONS.length; i++)
            {
                if(extension.equals(VALID_EXTENSIONS[i]))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    // Returns true if flagMap contains the File parameter key as a key
    private static boolean isFlag(File key)
    {
        return isFlag(key.getName());
    }
    
    // Returns true if flagMap contains the String parameter key as a key
    private static boolean isFlag(String key)
    {
        return flagMap.containsKey(key);
    }
    
    // Populates flagMap with argument flags as keys and each value as false
    private static HashMap<String, Boolean> populateFlagMap()
    {
        flagMap = new HashMap<String, Boolean>();
        
        for(int i = 0; i < ARGUMENT_FLAGS.length; i++)
        {
            flagMap.put(ARGUMENT_FLAGS[i], false);
        }
        
        return flagMap;
    }
}