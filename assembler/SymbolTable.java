// A symbol table that keeps a correspondence between
// symbolic labels and numeric addresses

import java.util.HashMap;

public class SymbolTable
{
    // Constants
    public static final int INVALID_ADDRESS = -1, ALLOCATED_STARTING_REGISTER = 0, ALLOCATED_INVALID_REGISTER = 16;
    private int nextElement;
    
    // Instance variables
    private HashMap<String, Integer> symbolMap;
    
    // Constructor
    public SymbolTable()
    {
        populateSymbolMap();
        
        nextElement = ALLOCATED_INVALID_REGISTER;
    }
    
    // Methods
    
    // Adds a pair to the table
    public void addEntry(String symbol, int address)
    {
        symbolMap.put(symbol, address);
    }
    
    // Returns true if the table contains the symbol, otherwise false
    public boolean contains(String symbol)
    {
        return symbolMap.containsKey(symbol);
    }
    
    // Returns the address associated with the symbol within the table
    public int getAddress(String symbol)
    {
        if(contains(symbol))
        {
            return symbolMap.get(symbol);
        }
        else
        {
            return INVALID_ADDRESS;
        }
    }
    
    public int next()
    {
        return nextElement++;
    }
    
    private void populateSymbolMap()
    {
        symbolMap = new HashMap<String, Integer>();
        
        for(int i = ALLOCATED_STARTING_REGISTER; i < ALLOCATED_INVALID_REGISTER; i++)
        {
            symbolMap.put("R" + i, i);
        }
        
        symbolMap.put("SCREEN", 16384);
        symbolMap.put("KBD", 24576);
        symbolMap.put("SP", 0);
        symbolMap.put("LCL", 1);
        symbolMap.put("ARG", 2);
        symbolMap.put("THIS", 3);
        symbolMap.put("THAT", 4);
    }
}