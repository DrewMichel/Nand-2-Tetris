// 1: push constant 7
@7
D=A
@SP
M=M+1
A=M-1
M=D
// 2: push constant 8
@8
D=A
@SP
M=M+1
A=M-1
M=D
// 3: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
