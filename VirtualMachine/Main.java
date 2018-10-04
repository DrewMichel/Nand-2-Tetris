// Main entry point
//
// Argument Flags:
// -R: Disabled. Recursively searches sub directories. 
// -C: Disabled. Causes directories to translate into a single file.
// -N: Enabled. No file header is written. Necessary for all files except
//              StaticsTest and FibonacciElement
//              (not to be confused with FibonacciSeries)

import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class Main
{
    // Constants
    public static final String[] ARGUMENT_FLAGS = {"-R", "-C", "-N"};
    
    // Methods
    
    // Main method
    public static void main(String[] args)
    {
        LinkedHashSet<File> regularFiles = new LinkedHashSet<File>();
        LinkedHashSet<File> directories = new LinkedHashSet<File>();
        HashMap<String, Boolean> flagMap = new HashMap<String, Boolean>();
        
        populateFlagMap(flagMap);
        
        System.out.println("Virtual Machine translator starting.");
        
        if(args == null || args.length <= 0)
        {
            System.out.print("Enter path: ");
            args = receiveInput().split(" ");
        }
        
        //args = toUpperCaseArray(args);
        
        //processArguments(regularFiles, flagMap, args);
        weakProcessArguments(directories, regularFiles, flagMap, args);
        
        displayFiles(directories);
        displayFiles(regularFiles);
        
        System.out.println("Beginning file translation...");
        
        //processFiles(regularFiles, flagMap);
        weakProcess(directories, regularFiles, flagMap);
        
        System.out.println("Virtual Machine translator ending.");
    }
    
    // Processes arguments into directories, files, and flags
    public static void weakProcessArguments(LinkedHashSet<File> directories, LinkedHashSet<File> regularFiles, HashMap<String, Boolean> flagMap, String[] args)
    {
        File currentFile;
        
        for(int i = 0; i < args.length; ++i)
        {
            currentFile = new File(args[i]);
            
            if(currentFile.exists())
            {
                if(currentFile.isDirectory())
                {
                    directories.add(currentFile);
                }
                else if(currentFile.isFile())
                {
                    regularFiles.add(currentFile);
                }
            }
            else if(flagMap.containsKey(args[i].toUpperCase()))
            {
                flagMap.put(args[i].toUpperCase(), true);
            }
        }
    }
    
    // Translates directories and files
    public static void weakProcess(LinkedHashSet<File> directories, LinkedHashSet<File> files, HashMap<String, Boolean> flagMap)
    {
        weakProcessDirectories(directories, flagMap);
        weakProcessFiles(files, flagMap);
    }
    
    // Translates files within directories into a single file
    public static void weakProcessDirectories(LinkedHashSet<File> directories, HashMap<String, Boolean> flagMap)
    {
        File[] directoryFiles;
        File currentDirectory;
        String directoryName, currentFileName;
        Iterator<File> iterator = directories.iterator();
        
        while(iterator.hasNext())
        {
            Parser parser = new Parser();
            CodeWriter codeWriter = new CodeWriter();
            
            currentDirectory = iterator.next();
            
            directoryName = currentDirectory.getAbsolutePath();
            
            directoryFiles = currentDirectory.listFiles();
            
            for(int i = 0; i < directoryFiles.length; ++i)
            {
                currentFileName = directoryFiles[i].getAbsolutePath();
                
                if(Parser.hasValidFileExtension(currentFileName))
                {
                    parser.initialize(new File(currentFileName));
                    
                    if(!codeWriter.isInitialized())
                    {
                        codeWriter.initialize(new File(directoryName + File.separator + CodeWriter.getRealName(directoryName) + CodeWriter.OUTPUT_FILE_EXTENSION));
                        codeWriter.setDirectoryName(directoryName);
                        
                        if(!flagMap.get(ARGUMENT_FLAGS[2]))
                        {
                            codeWriter.writeFileHeader();
                        }
                    }
                    
                    codeWriter.setFileName(currentFileName);
                    
                    process(parser, codeWriter);
                    
                    parser.close();
                }
            }
            
            codeWriter.close();
        }
    }
    
    // Translates files
    public static void weakProcessFiles(LinkedHashSet<File> files, HashMap<String, Boolean> flagMap)
    {
        Iterator<File> iterator = files.iterator();
        File currentFile;
        String currentFileName;
        
        while(iterator.hasNext())
        {
            Parser parser = new Parser();
            CodeWriter codeWriter = new CodeWriter();
            
            currentFile = iterator.next();
            
            currentFileName = currentFile.getAbsolutePath();
            
            if(Parser.hasValidFileExtension(currentFileName))
            {
                parser.initialize(new File(currentFileName));
                codeWriter.initialize(new File(CodeWriter.adjustExtension(currentFileName, CodeWriter.OUTPUT_FILE_EXTENSION)));
                
                if(!flagMap.get(ARGUMENT_FLAGS[2]))
                {
                    codeWriter.writeFileHeader();
                }
                
                process(parser, codeWriter);
            }
            
            parser.close();
            codeWriter.close();
        }
    }
    
    // Parses over a file and translates then writes each line
    public static void process(Parser parser, CodeWriter codeWriter)
    {
        while(parser.hasMoreCommands())
        {
            parser.advance();
            
            parser.commandType();
            
            if(parser.getCurrentType().equals(CommandTable.C_ARITHMETIC_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writeArithmetic(parser.getCurrentCommand());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_POP_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writePop(parser.segment(), parser.arg2());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_PUSH_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writePush(parser.segment(), parser.arg2());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_LABEL_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writeLabel(parser.segment());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_GOTO_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writeGoto(parser.segment());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_IF_GOTO_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writeIfGoto(parser.segment());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_FUNCTION_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writeFunction(parser.segment(), parser.arg2());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_CALL_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writeCall(parser.segment(), parser.arg2());
            }
            else if(parser.getCurrentType().equals(CommandTable.C_RETURN_TYPE))
            {
                codeWriter.writeComment(parser.getCurrentCommand());
                codeWriter.incrementProgramCounter();
                codeWriter.writeReturn();
            }
        }
    }
    
    // Translates files
    public static void processFiles(LinkedHashSet<File> files, HashMap<String, Boolean> flags)
    {
        if(files != null)
        {
            Parser parser = null;
            CodeWriter codeWriter = null;
            
            Iterator<File> fileIterator = files.iterator();
            
            File currentFile;
            
            while(fileIterator.hasNext())
            {
                currentFile = fileIterator.next();
                
                parser = new Parser(currentFile);
                codeWriter = new CodeWriter(CodeWriter.adjustExtension(currentFile, CodeWriter.OUTPUT_FILE_EXTENSION));
                
                process(parser, codeWriter);
            }
            
            codeWriter.close();
        }
    }
    
    // Displays file paths
    public static void displayFiles(LinkedHashSet<File> files)
    {
        if(files != null)
        {
            Iterator<File> fileIterator = files.iterator();
        
            while(fileIterator.hasNext())
            {
                System.out.println(fileIterator.next().getAbsolutePath());
            }
        }
    }
    
    // Processes arguments into files, directories, and flags
    public static void processArguments(LinkedHashSet<File> files, HashMap<String, Boolean> flags, String[] args)
    {
        LinkedHashSet<File> allDirectories = new LinkedHashSet<File>();
        
        File currentFile;
        
        for(int i = 0; i < args.length; ++i)
        {
            currentFile = new File(args[i]);
            
            if(flags.containsKey(args[i].toUpperCase()))
            {
                flags.put(args[i].toUpperCase(), true);
            }
            else if(currentFile.exists())
            {
                if(currentFile.isDirectory() && !allDirectories.contains(currentFile))
                {
                    allDirectories.add(currentFile);
                }
                else if(currentFile.isFile() && Parser.hasValidFileExtension(currentFile))
                {
                    files.add(currentFile);
                }
            }
        }
        
        accessDirectoryFiles(files, allDirectories, flags);
    }
    
    // Pulls files within directories
    public static void accessDirectoryFiles(LinkedHashSet<File> files, LinkedHashSet<File> directories, HashMap<String, Boolean> flags)
    {
        LinkedHashSet<File> innerDirectories = new LinkedHashSet<File>();
        
        addDirectoryFiles(files, directories, innerDirectories, flags);
    }
    
    // Adds all files within a directory into the LinkedHashSet<File> files parameter
    private static void addDirectoryFiles(LinkedHashSet<File> files, LinkedHashSet<File> allDirectories, LinkedHashSet<File> currentDirectories, HashMap<String, Boolean> flags)
    {
        Iterator<File> directoryIterator = allDirectories.iterator();
        
        File currentFile;
        File[] filesInDirectory;
        
        while(directoryIterator.hasNext())
        {
            currentFile = directoryIterator.next();
            
            if(currentFile.exists())
            {
                filesInDirectory = currentFile.listFiles();
                
                for(int i = 0; i < filesInDirectory.length; i++)
                {
                    if(filesInDirectory[i].exists())
                    {
                        if(filesInDirectory[i].isDirectory())
                        {
                            currentDirectories.add(filesInDirectory[i]);
                        }
                        else if(filesInDirectory[i].isFile() && Parser.hasValidFileExtension(filesInDirectory[i]))
                        {
                            files.add(filesInDirectory[i]);
                        }
                    }
                } 
            }
        }
        
        addInnerDirectoryFiles(files, allDirectories, currentDirectories, flags);
    }
    
    // Recursively adds files within sub directories
    private static void addInnerDirectoryFiles(LinkedHashSet<File> files, LinkedHashSet<File> allDirectories, LinkedHashSet<File> currentDirectories, HashMap<String, Boolean> flags)
    {
        if(flags.get(ARGUMENT_FLAGS[0]) && !currentDirectories.isEmpty())
        {
            LinkedHashSet<File> innerDirectories = new LinkedHashSet<File>();
            
            Iterator<File> directoryIterator = currentDirectories.iterator();
            
            File currentFile;
            
            File[] filesInDirectory;
            
            while(directoryIterator.hasNext())
            {
                currentFile = directoryIterator.next();
                
                if(currentFile.exists())
                {
                    filesInDirectory = currentFile.listFiles();
                    
                    for(int i = 0; i < filesInDirectory.length; ++i)
                    {
                        if(filesInDirectory[i].isDirectory() && !allDirectories.contains(filesInDirectory[i]))
                        {
                            innerDirectories.add(filesInDirectory[i]);
                        }
                        else if(filesInDirectory[i].isFile() && Parser.hasValidFileExtension(filesInDirectory[i]))
                        {
                            files.add(filesInDirectory[i]);
                        }
                    } 
                }
            }
            
            currentDirectories = null;
            
            addInnerDirectoryFiles(files, allDirectories, innerDirectories, flags);
        }
    }
    
    // Populates flagMap parameter with ARGUMENT_FALSE elements as keys
    // and false as values
    public static HashMap<String, Boolean> populateFlagMap(HashMap<String, Boolean> flagMap)
    {
        for(int i = 0; i < ARGUMENT_FLAGS.length; ++i)
        {
            flagMap.put(ARGUMENT_FLAGS[i], false);
        }
        
        return flagMap;
    }
    
    // Returns a line
    public static String receiveInput()
    {
        return new Scanner(System.in).nextLine();
    }
    
    // Loops over an array of Strings and converts each element to uppercase
    public static String[] toUpperCaseArray(String[] array)
    {
        if(array != null)
        {
            for(int i = 0; i < array.length; ++i)
            {
                array[i] = array[i].toUpperCase();
            }
        }
        
        return array;
    }
}