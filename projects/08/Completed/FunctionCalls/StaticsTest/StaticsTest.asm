@256
D=A
@SP
M=D
@Sys.initlunchbreak1
D=A
@SP
M=M+1
A=M-1
M=D
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
@SP
D=M
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(Sys.initlunchbreak1)
// 1: function Class1.set 0
(Class1.set)
// 2: push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 3: pop static 0
@SP
AM=M-1
D=M
@Class1.0
M=D
// 4: push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 5: pop static 1
@SP
AM=M-1
D=M
@Class1.1
M=D
// 6: push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
// 7: return
@LCL
D=M
@R14
M=D
@5
A=D-A
D=M
@R15
M=D
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
@ARG
D=M+1
@SP
M=D
@1
D=A
@R14
A=M-D
D=M
@THAT
M=D
@2
D=A
@R14
A=M-D
D=M
@THIS
M=D
@3
D=A
@R14
A=M-D
D=M
@ARG
M=D
@4
D=A
@R14
A=M-D
D=M
@LCL
M=D
@R15
A=M
0;JMP
// 8: function Class1.get 0
(Class1.get)
// 9: push static 0
@Class1.0
D=M
@SP
M=M+1
A=M-1
M=D
// 10: push static 1
@Class1.1
D=M
@SP
M=M+1
A=M-1
M=D
// 11: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 12: return
@LCL
D=M
@R14
M=D
@5
A=D-A
D=M
@R15
M=D
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
@ARG
D=M+1
@SP
M=D
@1
D=A
@R14
A=M-D
D=M
@THAT
M=D
@2
D=A
@R14
A=M-D
D=M
@THIS
M=D
@3
D=A
@R14
A=M-D
D=M
@ARG
M=D
@4
D=A
@R14
A=M-D
D=M
@LCL
M=D
@R15
A=M
0;JMP
// 13: function Class2.set 0
(Class2.set)
// 14: push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 15: pop static 0
@SP
AM=M-1
D=M
@Class2.0
M=D
// 16: push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// 17: pop static 1
@SP
AM=M-1
D=M
@Class2.1
M=D
// 18: push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
// 19: return
@LCL
D=M
@R14
M=D
@5
A=D-A
D=M
@R15
M=D
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
@ARG
D=M+1
@SP
M=D
@1
D=A
@R14
A=M-D
D=M
@THAT
M=D
@2
D=A
@R14
A=M-D
D=M
@THIS
M=D
@3
D=A
@R14
A=M-D
D=M
@ARG
M=D
@4
D=A
@R14
A=M-D
D=M
@LCL
M=D
@R15
A=M
0;JMP
// 20: function Class2.get 0
(Class2.get)
// 21: push static 0
@Class2.0
D=M
@SP
M=M+1
A=M-1
M=D
// 22: push static 1
@Class2.1
D=M
@SP
M=M+1
A=M-1
M=D
// 23: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 24: return
@LCL
D=M
@R14
M=D
@5
A=D-A
D=M
@R15
M=D
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
@ARG
D=M+1
@SP
M=D
@1
D=A
@R14
A=M-D
D=M
@THAT
M=D
@2
D=A
@R14
A=M-D
D=M
@THIS
M=D
@3
D=A
@R14
A=M-D
D=M
@ARG
M=D
@4
D=A
@R14
A=M-D
D=M
@LCL
M=D
@R15
A=M
0;JMP
// 25: function Sys.init 0
(Sys.init)
// 26: push constant 6
@6
D=A
@SP
M=M+1
A=M-1
M=D
// 27: push constant 8
@8
D=A
@SP
M=M+1
A=M-1
M=D
// 28: call Class1.set 2
@Class1.setlunchbreak2
D=A
@SP
M=M+1
A=M-1
M=D
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
@SP
D=M
@7
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(Class1.setlunchbreak2)
// 29: pop temp 0
@SP
AM=M-1
D=M
@R5
M=D
// 30: push constant 23
@23
D=A
@SP
M=M+1
A=M-1
M=D
// 31: push constant 15
@15
D=A
@SP
M=M+1
A=M-1
M=D
// 32: call Class2.set 2
@Class2.setlunchbreak3
D=A
@SP
M=M+1
A=M-1
M=D
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
@SP
D=M
@7
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.set
0;JMP
(Class2.setlunchbreak3)
// 33: pop temp 0
@SP
AM=M-1
D=M
@R5
M=D
// 34: call Class1.get 0
@Class1.getlunchbreak4
D=A
@SP
M=M+1
A=M-1
M=D
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
@SP
D=M
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.get
0;JMP
(Class1.getlunchbreak4)
// 35: call Class2.get 0
@Class2.getlunchbreak5
D=A
@SP
M=M+1
A=M-1
M=D
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
@SP
D=M
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.get
0;JMP
(Class2.getlunchbreak5)
// 36: label WHILE
(WHILE)
// 37: goto WHILE
@WHILE
0;JMP
