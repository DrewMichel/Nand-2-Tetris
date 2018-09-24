// A symbol table that keeps a correspondence between
// symbolic labels and numeric addresses

import java.util.HashMap;

public class SymbolTable
{
    // Constants
    public static final int INVALID_ADDRESS = -1;
    
    // Instance variables
    private HashMap<String, Integer> symbolMap;
    
    // Constructor
    public SymbolTable()
    {
        symbolMap = new HashMap<String, Integer>();
    }
    
    // Methods
    
    // Adds a pair to the table
    public boolean addEntry(String symbol, int address)
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
}