// 1: push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// 2: push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// 3: eq
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@eq1
D;JEQ
@SP
A=M
M=0
@END_OF_OPERATION1
0;JMP
(eq1)
@SP
A=M
M=-1
(END_OF_OPERATION1)
@SP
M=M+1
// 4: push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// 5: push constant 16
@16
D=A
@SP
M=M+1
A=M-1
M=D
// 6: eq
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@eq2
D;JEQ
@SP
A=M
M=0
@END_OF_OPERATION2
0;JMP
(eq2)
@SP
A=M
M=-1
(END_OF_OPERATION2)
@SP
M=M+1
// 7: push constant 16
@16
D=A
@SP
M=M+1
A=M-1
M=D
// 8: push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// 9: eq
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@eq3
D;JEQ
@SP
A=M
M=0
@END_OF_OPERATION3
0;JMP
(eq3)
@SP
A=M
M=-1
(END_OF_OPERATION3)
@SP
M=M+1
// 10: push constant 892
@892
D=A
@SP
M=M+1
A=M-1
M=D
// 11: push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// 12: lt
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@lt4
D;JLT
@SP
A=M
M=0
@END_OF_OPERATION4
0;JMP
(lt4)
@SP
A=M
M=-1
(END_OF_OPERATION4)
@SP
M=M+1
// 13: push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// 14: push constant 892
@892
D=A
@SP
M=M+1
A=M-1
M=D
// 15: lt
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@lt5
D;JLT
@SP
A=M
M=0
@END_OF_OPERATION5
0;JMP
(lt5)
@SP
A=M
M=-1
(END_OF_OPERATION5)
@SP
M=M+1
// 16: push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// 17: push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// 18: lt
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@lt6
D;JLT
@SP
A=M
M=0
@END_OF_OPERATION6
0;JMP
(lt6)
@SP
A=M
M=-1
(END_OF_OPERATION6)
@SP
M=M+1
// 19: push constant 32767
@32767
D=A
@SP
M=M+1
A=M-1
M=D
// 20: push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// 21: gt
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@gt7
D;JGT
@SP
A=M
M=0
@END_OF_OPERATION7
0;JMP
(gt7)
@SP
A=M
M=-1
(END_OF_OPERATION7)
@SP
M=M+1
// 22: push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// 23: push constant 32767
@32767
D=A
@SP
M=M+1
A=M-1
M=D
// 24: gt
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@gt8
D;JGT
@SP
A=M
M=0
@END_OF_OPERATION8
0;JMP
(gt8)
@SP
A=M
M=-1
(END_OF_OPERATION8)
@SP
M=M+1
// 25: push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// 26: push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// 27: gt
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@gt9
D;JGT
@SP
A=M
M=0
@END_OF_OPERATION9
0;JMP
(gt9)
@SP
A=M
M=-1
(END_OF_OPERATION9)
@SP
M=M+1
// 28: push constant 57
@57
D=A
@SP
M=M+1
A=M-1
M=D
// 29: push constant 31
@31
D=A
@SP
M=M+1
A=M-1
M=D
// 30: push constant 53
@53
D=A
@SP
M=M+1
A=M-1
M=D
// 31: add
@SP
AM=M-1
D=M
A=A-1
M=D+M
// 32: push constant 112
@112
D=A
@SP
M=M+1
A=M-1
M=D
// 33: sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// 34: neg
@SP
A=M-1
M=-M
// 35: and
@SP
AM=M-1
D=M
A=A-1
M=D&M
// 36: push constant 82
@82
D=A
@SP
M=M+1
A=M-1
M=D
// 37: or
@SP
AM=M-1
D=M
A=A-1
M=D|M
// 38: not
@SP
A=M-1
M=!M
