// 1: push constant 3030
@3030
D=A
@SP
M=M+1
A=M-1
M=D
// 2: pop pointer 0
@SP
AM=M-1
D=M
@THIS
M=D
// 3: push constant 3040
@3040
D=A
@SP
M=M+1
A=M-1
M=D
// 4: pop pointer 1
@SP
AM=M-1
D=M
@THAT
M=D
// 5: push constant 32
@32
D=A
@SP
M=M+1
A=M-1
M=D
// 6: pop this 2
@2
D=A
@THIS
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
// 7: push constant 46
@46
D=A
@SP
M=M+1
A=M-1
M=D
// 8: pop that 6
@6
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
// 9: push pointer 0
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
// 10: push pointer 1
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
// 11: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
// 12: push this 2
@2
D=A
@THIS
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 13: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 14: push that 6
@6
D=A
@THAT
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 15: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
