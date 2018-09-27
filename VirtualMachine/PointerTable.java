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

import java.util.HashMap;

public class PointerTable
{                           
    public static final int STACK_MIN_VALUE = 256, STACK_MAX_VALUE = 2047,
                            HEAP_MIN_VALUE = 2048, STATIC_MIN_VALUE = 16,
                            STATIC_MAX_VALUE = 255, TEMP_MIN_VALUE = 5,
                            TEMP_MAX_VALUE = 12, GENERAL_MIN_VALUE = 13,
                            GENERAL_MAX_VALUE = 15, STACK_POINTER = 0,
                            LOCAL_POINTER = 1, ARGUMENT_POINTER = 2,
                            THIS_POINTER = 3, THAT_POINTER = 4;
                            
    public static final String INVALID_POINTER = "INVALID POINTER ADDRESS";
}