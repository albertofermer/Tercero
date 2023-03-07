#------------------------------------------------------------------
# Copyright (c) 2007, Francisco José Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------

#------------------------------------------------------------------
# Console.print.char
#------------------------------------------------------------------

	.globl	Console.print.char
	.ent	Console.print.char
Console.print.char:
	addiu $sp $sp -12
	sw $ra 4($sp)
	sw $fp 0($sp)
	or $fp $0 $sp
	lw $a0 12($sp)
	ori $v0 $0 11
	syscall
Console.print.char.return:
	sw $v0 8($fp)
	or $sp $0 $fp
	lw $ra 4($sp)
	lw $fp 0($sp)
	addiu $sp $sp 12
	jr $ra
	nop
	.end	Console.print.char

#------------------------------------------------------------------
# Console.print.int
#------------------------------------------------------------------

	.globl	Console.print.int
	.ent	Console.print.int
Console.print.int:
	addiu $sp $sp -12
	sw $ra 4($sp)
	sw $fp 0($sp)
	or $fp $0 $sp
	lw $a0 12($sp)
	ori $v0 $0 1
	syscall
Console.print.int.return:
	sw $v0 8($fp)
	or $sp $0 $fp
	lw $ra 4($sp)
	lw $fp 0($sp)
	addiu $sp $sp 12
	jr $ra
	nop
	.end	Console.print.int

#------------------------------------------------------------------
# Console.readInt
#------------------------------------------------------------------

	.globl	Console.readInt
	.ent	Console.readInt
Console.readInt:
	addiu $sp $sp -16
	sw $ra 8($sp)
	sw $fp 4($sp)
	or $fp $0 $sp
	ori $v0 $0 5
	syscall
	nop
Console.readInt.return:
	sw $v0 12($fp)
	or $sp $0 $fp
	lw $ra 8($sp)
	lw $fp 4($sp)
	addiu $sp $sp 16
	jr $ra
	nop
	.end	Console.readInt

#------------------------------------------------------------------
# Console.readChar
#------------------------------------------------------------------

	.globl	Console.readChar
	.ent	Console.readChar
Console.readChar:
	addiu $sp $sp -16
	sw $ra 8($sp)
	sw $fp 4($sp)
	or $fp $0 $sp
	ori $v0 $0 12
	syscall
	nop
Console.readChar.return:
	sw $v0 12($fp)
	or $sp $0 $fp
	lw $ra 8($sp)
	lw $fp 4($sp)
	addiu $sp $sp 16
	jr $ra
	nop
	.end	Console.readChar

