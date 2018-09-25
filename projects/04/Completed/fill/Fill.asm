// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
@i      // create i variable

(LOOP)  // primary loop label

@16384  // A=16384, start of screen addresses
D=A     // D=16384
@i      // A=i
M=D     // i's memory = 16384

@24576  // A=24576, keyboard address
D=M     // set D to value of keyboard memory

@GOWHITE
D;JEQ // goto gowhite label if keyboard memory is zero, else fall through to loopblack

(LOOPBLACK) // loopblack label
@i      // A=i
D=M     // set D to i's memory

A=D     // change A to screen value stored in D 

M=-1    // book incorrectly states that 1 is black when -1 is black

@i      // A=i
D=M     // set D to i's memory
M=M+1   // increment i's memory screen value by one
@24575  // A=24575, last screen value
D=A-D   // subtract i's previous screen value from the last screen value

@LOOPBLACK
D;JGT // if D is greater than zero, goto loopblack

@LOOP
D;JMP // unconditional jump to primary loop

(GOWHITE) // gowhite label

(LOOPWHITE) // loopwhite label

@i      // A=i
D=M     // set D to i's memory

A=D     // change A to screen value stored in D 

M=0     // set to white

@i      // A=i
D=M     // set D to i's memory
M=M+1   // increment i's memory screen value by one
@24575  // A=24575, last screen value
D=A-D   // subtract i's previous screen value from the last screen value

@LOOPWHITE
D;JGT // if D is greater than zero, goto loopwhite

@LOOP
D;JMP // unconditional jump to primary loop
