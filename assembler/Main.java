// Program entry point

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main
{
    private static String[] ARGUMENT_FLAGS = {"-R"};
    private static String[] VALID_EXTENSIONS = {".asm"};
    private static HashMap<String, Boolean> flagMap;
    
    public static void main(String[] args)
    {
        System.out.println("Assembler Beginning");
        
        populateFlagMap();
        Parser parser = null;
        
        ArrayList<File> regularFiles = new ArrayList<File>();
        
        if(args.length <= 1)
        {
         
            System.out.print("Enter filepath: ");
            args = receiveInput().split(" ");
        }
        
        regularFiles = processArguments(regularFiles, args);
        
        /*
        for(int i = 0; i < regularFiles.size(); i++)
        {
            System.out.println(regularFiles.get(i));
        }
        */
        
        /*
        for(int i = 0; i < regularFiles.size(); i++)
        {
            parser = new Parser(regularFiles.get(i));
        }
        */
        
        // get parser working on a single file first before allowing multiple
        
        System.out.println("Assembler Ending");
    }
    
    public static String receiveInput()
    {
        //Scanner scan = new Scanner(System.in);
        //return scan.nextLine();
        
        return new Scanner(System.in).nextLine();
    }
    
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

    private static ArrayList<File> processArguments(ArrayList<File> regularFiles, String[] arguments)
    {
        ArrayList<File> directories = new ArrayList<File>();
        
        arguments = toUpperCaseArray(arguments);
        
        processElements(directories, regularFiles, arguments);
        
        accessDirectoryFiles(directories, regularFiles);
        
        return regularFiles;
    }
    
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
    
    private static boolean isFlag(File value)
    {
        return isFlag(value.getName());
    }
    
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
    
    private static boolean isFlag(String value)
    {
        return flagMap.containsKey(value);
    }
    
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