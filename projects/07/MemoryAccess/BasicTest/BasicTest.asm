// 1: push constant 10
@10
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
// 3: push constant 21
@21
D=A
@SP
M=M+1
A=M-1
M=D
// 4: push constant 22
@22
D=A
@SP
M=M+1
A=M-1
M=D
// 5: pop argument 2
@2
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
// 6: pop argument 1
@1
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
// 7: push constant 36
@36
D=A
@SP
M=M+1
A=M-1
M=D
// 8: pop this 6
@6
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
// 9: push constant 42
@42
D=A
@SP
M=M+1
A=M-1
M=D
// 10: push constant 45
@45
D=A
@SP
M=M+1
A=M-1
M=D
// 11: pop that 5
@5
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
// 12: pop that 2
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
// 13: push constant 510
@510
D=A
@SP
M=M+1
A=M-1
M=D
// 14: pop temp 6
@SP
AM=M-1
D=M
@R11
M=D
// 15: push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 16: push that 5
@5
D=A
@THAT
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 17: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
// 18: push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 19: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 20: push this 6
@6
D=A
@THIS
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 21: push this 6
@6
D=A
@THIS
A=D+M
D=M
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
// 23: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 24: push temp 6
@R11
D=M
@SP
M=M+1
A=M-1
M=D
// 25: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
