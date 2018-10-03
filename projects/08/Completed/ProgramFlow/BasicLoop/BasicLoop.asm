// 1: push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
// 2: pop local 0
@0
D=A
@LCL
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
// 3: label LOOP_START
(LOOP_START)
// 4: push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 5: push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 6: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
// 7: pop local 0
@0
D=A
@LCL
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
// 8: push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 9: push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
// 10: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 11: pop argument 0
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
// 13: if-goto LOOP_START
@SP
AM=M-1
D=M
@LOOP_START
D;JNE
// 14: push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
