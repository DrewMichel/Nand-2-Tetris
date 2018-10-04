// According to coursework:
// The stack pointer will point to the next insertion address
// pushing onto the stack will insert at the address pointed to by
// the stack pointer and move the stack pointer by one
// popping off the stack will move the stack pointer by one then place
// the current value at the address pointed to by the stack pointer onto
// the appropriate memory address (such as static, local, argument, etc...)
//
// The stack pointer will be initialized at the minimum value of 256 and
// as values are pushed onto the stack it will increment toward 2048
// Inversely, as values are popped off the stack, the pointer will decrement
// toward 256

// Segment names constant (integers), local, argument, this, that,
//               static, pointer, temp
// Cannot pop into constants
//
// Local pointer base address can be initialized to any desired value
// Could be placed within or above the stack
// Local pointer will always point to local base address
// Argument, This, and That pointers will also always point to their respective
// base addresses and can also have their base address located anywhere in RAM
//
// Static pointer should always point to base address 16
// Assembly symbols for static address should be generated using
// The translated file name, a period, and the index argument
// Example: "pop static 5" in file Doe.vm would have the symbol @Doe.5
// Static symbols are mapped starting from register 16 based on the order
// they are encountered
// If "pop static 5" is the first static symbol encountered, it will be placed
// at register 16 and NOT at register 21 (16 + 5)
//
// Temp pointers occupy registers 5-12
// Can use base address of 5 + index to reach each register
//
// Pointer pointers occupy only 2 registers
// Can only pop/push pointer 0/1
// Pointer 0 should access This
// Pointer 1 should access That
// Pointer should access actual This/That pointers (3/4) not their memory values
//
// According to coursework:
// set RAM[0] 256,   // stack pointer
// set RAM[1] 300,   // base address of the local segment
// set RAM[2] 400,   // base address of the argument segment
// set RAM[3] 3000,  // base address of the this segment
// set RAM[4] 3010,  // base address of the that segment
// segments should be iniitalized with these values
//
// @R13, @R14, and @R15 are general purpose registers that can be used during
// operations
//
// According to coursework:
// -1 Represents the boolean true
// 0 Represents the boolean false
//
// function g nVars
// here starts a function called g,
// which has nVars local variables
//
// call g nArgs
// invoke function g for its effect;
// nArgs arguments have already been pushed onto the stack
//
// return
// terminate execution and return control to the call

import java.util.HashMap;

public class PointerTable
{   
    // Pointer addresses
    public static final int STACK_POINTER = 0, LOCAL_POINTER = 1,
                            ARGUMENT_POINTER = 2, THIS_POINTER = 3,
                            THAT_POINTER = 4;
                            
    // Segment ranges
    public static final int STACK_MIN_VALUE = 256, STACK_MAX_VALUE = 2047,
                            HEAP_MIN_VALUE = 2048, STATIC_MIN_VALUE = 16,
                            STATIC_MAX_VALUE = 255, TEMP_MIN_VALUE = 5,
                            TEMP_MAX_VALUE = 12, GENERAL_MIN_VALUE = 13,
                            GENERAL_MAX_VALUE = 15, LOCAL_MIN_VALUE = 300,
                            ARGUMENT_MIN_VALUE = 400, THIS_MIN_VALUE = 3000,
                            THAT_MIN_VALUE = 4000;
    
    // Boolean values
    public static final int TRUE_VALUE = -1, FALSE_VALUE = 0;
    
    // Hack assembly symbols
    public static final String STACK_SYMBOL = "SP", LOCAL_SYMBOL = "LCL",
                               ARGUMENT_SYMBOL = "ARG", THIS_SYMBOL = "THIS",
                               THAT_SYMBOL = "THAT";
                               
    // VM segment names
    public static final String CONSTANT_SEGMENT = "constant",
                               LOCAL_SEGMENT = "local",
                               ARGUMENT_SEGMENT = "argument",
                               THIS_SEGMENT = "this", THAT_SEGMENT = "that",
                               STATIC_SEGMENT = "static",
                               POINTER_SEGMENT = "pointer",
                               TEMP_SEGMENT = "temp";
                               
    public static final int[] DEFAULT_SEGMENT_VALUES = {STACK_MIN_VALUE, LOCAL_MIN_VALUE, ARGUMENT_MIN_VALUE, THIS_MIN_VALUE, THAT_MIN_VALUE};
                               
    public static final String[] SEGMENT_POINTERS = {STACK_SYMBOL, LOCAL_SYMBOL, ARGUMENT_SYMBOL, THIS_SYMBOL, THAT_SYMBOL};
    
    public static final String[] TEMP_REGISTERS = {"R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12"};
    
    public static final String[] GENERAL_PURPOSE_REGISTERS = {"R13", "R14", "R15"};
    
    // 0 THIS, 1 THAT
    public static final String[] POINTER_REGISTERS = {THIS_SYMBOL, THAT_SYMBOL};
                            
    public static final String INVALID_POINTER = "INVALID POINTER ADDRESS",
                               INVALID_SEGMENT = "INVALID SEGMENT NAME";
    
    private HashMap<String, String> generalSegmentMap;
    private HashMap<String, Integer> generalValueMap;
    
    public PointerTable()
    {
        initialize();
    }
    
    private void initialize()
    {
        generalSegmentMap = new HashMap<String, String>();
        generalValueMap = new HashMap<String, Integer>();
        
        generalSegmentMap.put(LOCAL_SEGMENT, LOCAL_SYMBOL);
        generalSegmentMap.put(ARGUMENT_SEGMENT, ARGUMENT_SYMBOL);
        generalSegmentMap.put(THIS_SEGMENT, THIS_SYMBOL);
        generalSegmentMap.put(THAT_SEGMENT, THAT_SYMBOL);
        
        generalValueMap.put(LOCAL_SYMBOL, LOCAL_MIN_VALUE);
        generalValueMap.put(ARGUMENT_SYMBOL, ARGUMENT_MIN_VALUE);
        generalValueMap.put(THIS_SYMBOL, THIS_MIN_VALUE);
        generalValueMap.put(THAT_SYMBOL, THAT_MIN_VALUE);
    }
    
    public boolean containsGeneralSegment(String segment)
    {
        return generalSegmentMap.containsKey(segment);
    }
    
    public String getGeneralSymbol(String segment)
    {
        return generalSegmentMap.get(segment);
    }
    
    public boolean containsGeneralSymbol(String symbol)
    {
        return generalValueMap.containsKey(symbol);
    }
    
    public int getGeneralValue(String symbol)
    {
        return generalValueMap.get(symbol);
    }
    
    // Should be called before pushing onto the stack
    // and after popping off of the stack
    // Could be called before committing the results of an arithmetic
    // operation onto the stack
    public static boolean withinStack(int pointer)
    {
        return (pointer >= STACK_MIN_VALUE && pointer <= STACK_MAX_VALUE);
    }
}