// Main entry point

import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class Main
{
    public static final String[] ARGUMENT_FLAGS = {"-R", "-C"};
    
    public static void main(String[] args)
    {
        LinkedHashSet<File> regularFiles = new LinkedHashSet<File>();
        HashMap<String, Boolean> flagMap = new HashMap<String, Boolean>();
        
        populateFlagMap(flagMap);
        
        System.out.println("Virtual Machine translator starting.");
        
        if(args == null || args.length <= 0)
        {
            System.out.print("Enter path: ");
            args = receiveInput().split(" ");
        }
        
        args = toUpperCaseArray(args);
        
        processArguments(regularFiles, flagMap, args);
        
        displayFiles(regularFiles);
        
        System.out.println("Detected: " + regularFiles.size() + " valid files.");
        
        System.out.println("Beginning file translation...");
        
        processFiles(regularFiles, flagMap);
        
        System.out.println("Virtual Machine translator ending.");
    }
    
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
            
            codeWriter.close();
        }
    }
    
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
    
    public static void processArguments(LinkedHashSet<File> files, HashMap<String, Boolean> flags, String[] args)
    {
        LinkedHashSet<File> allDirectories = new LinkedHashSet<File>();
        
        File currentFile;
        
        for(int i = 0; i < args.length; ++i)
        {
            currentFile = new File(args[i]);
            
            if(flags.containsKey(args[i]))
            {
                flags.put(args[i], true);
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
    
    public static void accessDirectoryFiles(LinkedHashSet<File> files, LinkedHashSet<File> directories, HashMap<String, Boolean> flags)
    {
        LinkedHashSet<File> innerDirectories = new LinkedHashSet<File>();
        
        addDirectoryFiles(files, directories, innerDirectories, flags);
    }
    
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
    
    public static HashMap<String, Boolean> populateFlagMap(HashMap<String, Boolean> flagMap)
    {
        for(int i = 0; i < ARGUMENT_FLAGS.length; ++i)
        {
            flagMap.put(ARGUMENT_FLAGS[i], false);
        }
        
        return flagMap;
    }
    
    public static String receiveInput()
    {
        return new Scanner(System.in).nextLine();
    }
    
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