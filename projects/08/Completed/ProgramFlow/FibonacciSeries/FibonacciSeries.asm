// 1: push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 2: pop pointer 1
@SP
AM=M-1
D=M
@THAT
M=D
// 3: push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
// 4: pop that 0
@0
D=A
@THAT
A=D+M
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
// 5: push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
// 6: pop that 1
@1
D=A
@THAT
A=D+M
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
// 7: push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 8: push constant 2
@2
D=A
@SP
M=M+1
A=M-1
M=D
// 9: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 10: pop argument 0
@0
D=A
@ARG
A=D+M
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
// 11: label MAIN_LOOP_START
(MAIN_LOOP_START)
// 12: push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 13: if-goto COMPUTE_ELEMENT
@SP
AM=M-1
D=M
@COMPUTE_ELEMENT
D;JNE
// 14: goto END_PROGRAM
@END_PROGRAM
0;JMP
// 15: label COMPUTE_ELEMENT
(COMPUTE_ELEMENT)
// 16: push that 0
@0
D=A
@THAT
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 17: push that 1
@1
D=A
@THAT
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 18: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
// 19: pop that 2
@2
D=A
@THAT
A=D+M
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
// 20: push pointer 1
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
// 21: push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
// 22: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
// 23: pop pointer 1
@SP
AM=M-1
D=M
@THAT
M=D
// 24: push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 25: push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
// 26: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 27: pop argument 0
@0
D=A
@ARG
A=D+M
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
// 28: goto MAIN_LOOP_START
@MAIN_LOOP_START
0;JMP
// 29: label END_PROGRAM
(END_PROGRAM)
