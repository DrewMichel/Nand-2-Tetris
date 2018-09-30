// 1: push constant 111
@111
D=A
@SP
M=M+1
A=M-1
M=D
// 2: push constant 333
@333
D=A
@SP
M=M+1
A=M-1
M=D
// 3: push constant 888
@888
D=A
@SP
M=M+1
A=M-1
M=D
// 4: pop static 8
@SP
AM=M-1
D=M
@StaticTest.8
M=D
// 5: pop static 3
@SP
AM=M-1
D=M
@StaticTest.3
M=D
// 6: pop static 1
@SP
AM=M-1
D=M
@StaticTest.1
M=D
// 7: push static 3
@StaticTest.3
D=M
@SP
M=M+1
A=M-1
M=D
// 8: push static 1
@StaticTest.1
D=M
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
// 10: push static 8
@StaticTest.8
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
