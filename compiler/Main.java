import java.util.Scanner;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class Main
{
    public static void main(String[] args)
    {
        if(args == null || args.length < 1)
        {
            System.out.print("Enter path: ");
            args = receiveLine().split(" ");
        }
        
        LinkedHashSet<File> validFiles = new LinkedHashSet<File>();
        
        processArguments(validFiles, args);
        
        displayFiles(validFiles);
        
        processFiles(validFiles);
    }
    
    public static void processFiles(LinkedHashSet<File> files)
    {
        if(files != null)
        {
            Iterator<File> iterator = files.iterator();
            File current;
            JackTokenizer tokenizer;
            while(iterator.hasNext())
            {
                current = iterator.next();
                
                tokenizer = new JackTokenizer(current);
                
                tokenizer.writeFileHeader();
                
                while(tokenizer.hasMoreTokens())
                {
                    tokenizer.advance();
                }
                
                tokenizer.writeFileFooter();
                
                tokenizer.close();
            }
        }
    }
    
    public static LinkedHashSet<File> processArguments(LinkedHashSet<File> files, String[] args)
    {
        File current;
        int length = args.length;
        
        for(int i = 0; i < length; ++i)
        {
            current = new File(args[i]);
            
            if(current.exists())
            {
                if(current.isDirectory())
                {
                    File[] directoryFiles = current.listFiles();
                    int directoryLength = directoryFiles.length;
                    
                    for(int k = 0; k < directoryLength; ++k)
                    {
                        if(directoryFiles[k].exists() && directoryFiles[k].isFile() && JackTokenizer.validExtension(directoryFiles[k], JackTokenizer.DEFAULT_INPUT_EXTENSION))
                        {
                            files.add(directoryFiles[k]);
                        }
                    }
                }
                else if(current.isFile() && JackTokenizer.validExtension(current, JackTokenizer.DEFAULT_INPUT_EXTENSION))
                {
                    files.add(current);
                }
            }
        }
        
        return files;
    }
    
    public static String receiveLine()
    {
        return new Scanner(System.in).nextLine();
    }
    
    public static void displayFiles(LinkedHashSet<File> files)
    {
        if(files != null)
        {
            Iterator<File> iterator = files.iterator();
            
            File current;
            
            while(iterator.hasNext())
            {
                current = iterator.next();
                
                System.out.println(current.getAbsolutePath());
            }
        }
    }
}