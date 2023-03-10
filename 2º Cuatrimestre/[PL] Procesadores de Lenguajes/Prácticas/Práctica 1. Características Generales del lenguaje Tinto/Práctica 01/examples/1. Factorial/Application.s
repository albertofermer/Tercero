# SPIM S20 MIPS simulator.
# The default exception handler for spim.
#
# Copyright (C) 1990-2004 James Larus, larus@cs.wisc.edu.
# ALL RIGHTS RESERVED.
#
# SPIM is distributed under the following conditions:
#
# You may make copies of SPIM for your own use and modify those copies.
#
# All copies of SPIM must retain my name and copyright notice.
#
# You may not sell SPIM or distributed SPIM in conjunction with a commerical
# product or service without the expressed written consent of James Larus.
#
# THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
# IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
# PURPOSE.
#

# $Header: $


# Define the exception handling code.  This must go first!

	.kdata
__m1_:	.asciiz	"  Exception "
__m2_:	.asciiz	" occurred and ignored\n"
__e0_:	.asciiz	"  [Interrupt] "
__e1_:	.asciiz	"  [TLB]"
__e2_:	.asciiz	"  [TLB]"
__e3_:	.asciiz	"  [TLB]"
__e4_:	.asciiz	"  [Address error in inst/data fetch] "
__e5_:	.asciiz	"  [Address error in store] "
__e6_:	.asciiz	"  [Bad instruction address] "
__e7_:	.asciiz	"  [Bad data address] "
__e8_:	.asciiz	"  [Error in syscall] "
__e9_:	.asciiz	"  [Breakpoint] "
__e10_:	.asciiz	"  [Reserved instruction] "
__e11_:	.asciiz	""
__e12_:	.asciiz	"  [Arithmetic overflow] "
__e13_:	.asciiz	"  [Trap] "
__e14_:	.asciiz	""
__e15_:	.asciiz	"  [Floating point] "
__e16_:	.asciiz	""
__e17_:	.asciiz	""
__e18_:	.asciiz	"  [Coproc 2]"
__e19_:	.asciiz	""
__e20_:	.asciiz	""
__e21_:	.asciiz	""
__e22_:	.asciiz	"  [MDMX]"
__e23_:	.asciiz	"  [Watch]"
__e24_:	.asciiz	"  [Machine check]"
__e25_:	.asciiz	""
__e26_:	.asciiz	""
__e27_:	.asciiz	""
__e28_:	.asciiz	""
__e29_:	.asciiz	""
__e30_:	.asciiz	"  [Cache]"
__e31_:	.asciiz	""
__excp:	.word __e0_, __e1_, __e2_, __e3_, __e4_, __e5_, __e6_, __e7_, __e8_, __e9_
	.word __e10_, __e11_, __e12_, __e13_, __e14_, __e15_, __e16_, __e17_, __e18_,
	.word __e19_, __e20_, __e21_, __e22_, __e23_, __e24_, __e25_, __e26_, __e27_,
	.word __e28_, __e29_, __e30_, __e31_
s1:	.word 0
s2:	.word 0

# This is the exception handler code that the processor runs when
# an exception occurs. It only prints some information about the
# exception, but can server as a model of how to write a handler.
#
# Because we are running in the kernel, we can use $k0/$k1 without
# saving their old values.

# This is the exception vector address for MIPS-1 (R2000):
#	.ktext 0x80000080
# This is the exception vector address for MIPS32:
	.ktext 0x80000180
# Select the appropriate one for the mode in which SPIM is compiled.
	.set noat
	move $k1 $at		# Save $at
	.set at
	sw $v0 s1		# Not re-entrant and we can't trust $sp
	sw $a0 s2		# But we need to use these registers

	mfc0 $k0 $13		# Cause register
	srl $a0 $k0 2		# Extract ExcCode Field
	andi $a0 $a0 0x1f

# Print information about exception.
#
	li $v0 4		# syscall 4 (print_str)
	la $a0 __m1_
	syscall

	li $v0 1		# syscall 1 (print_int)
	srl $a0 $k0 2		# Extract ExcCode Field
	andi $a0 $a0 0x1f
	syscall

	li $v0 4	# syscall 4 (print_str)
	andi $a0 $k0 0x3c
	lw $a0 __excp($a0)
	nop
	syscall

	bne $k0 0x18 ok_pc	# Bad PC exception requires special checks
	nop

	mfc0 $a0 $14		# EPC
	andi $a0 $a0 0x3	# Is EPC word-aligned?
	beq $a0 0 ok_pc
	nop

	li $v0 10		# Exit on really bad PC
	syscall

ok_pc:
	li $v0 4	# syscall 4 (print_str)
	la $a0 __m2_
	syscall

	srl $a0 $k0 2		# Extract ExcCode Field
	andi $a0 $a0 0x1f
	bne $a0 0 ret		# 0 means exception was an interrupt
	nop

# Interrupt-specific code goes here!
# Don't skip instruction at EPC since it has not executed.


ret:
# Return from (non-interrupt) exception. Skip offending instruction
# at EPC to avoid infinite loop.
#
	mfc0 $k0 $14		# Bump EPC register
	addiu $k0 $k0 4		# Skip faulting instruction
				# (Need to handle delayed branch case here)
	mtc0 $k0 $14


# Restore registers and reset procesor state
#
	lw $v0 s1		# Restore other registers
	lw $a0 s2

	.set noat
	move $at $k1		# Restore $at
	.set at

	mtc0 $0 $13		# Clear Cause register

	mfc0 $k0 $12		# Set Status register
	ori  $k0 0x1		# Interrupts enabled
	mtc0 $k0 $12

# Return from exception on MIPS32:
	eret

# Return sequence for MIPS-I (R2000):
#	rfe			# Return from exception handler
				# Should be in jr's delay slot
#	jr $k0
#	nop



# Standard startup code.  Invoke the constructor of "Main" with arguments:
#	main(argc, argv, envp)
#
	.text
	.globl __start
__start:
	ori $27 $0 0
	lui $27 0x1000
	lw $a0 0($sp)		# argc
	addiu $a1 $sp 4		# argv
	addiu $a2 $a1 4		# envp
	sll $v0 $a0 2
	addu $a2 $a2 $v0
	jal Main.Main
	nop

	li $v0 10
	syscall			# syscall 10 (exit)
	.end	__start




#------------------------------------------------------------------
# Copyright (c) 2007, Francisco Jos? Moreno Velo                   
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


#------------------------------------------------------------------
# Copyright (c) 2017, Francisco Jos? Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------
#------------------------------------------------------------------
# Main.Main
#------------------------------------------------------------------

	.globl	Main.Main
	.ent	Main.Main
Main.Main:
	addiu $sp $sp 0xffffffc4
	sw $ra 52($sp)
	sw $fp 48($sp)
	or $fp $0 $sp
	ori $a0 $0 0x5
	sw $a0 8($fp)
	nop
	lw $a0 8($fp)
	nop
	sw $a0 0($fp)
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Main.factorial.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 12($fp)
	addiu $sp $sp 0x4
	lw $a0 12($fp)
	nop
	sw $a0 4($fp)
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 16($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x21
	sw $a0 20($fp)
	nop
	lw $a0 20($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 24($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3d
	sw $a0 28($fp)
	nop
	lw $a0 28($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 32($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 36($fp)
	nop
	lw $a0 36($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 40($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 44($fp)
	addiu $sp $sp 0x4
Main.Main.return:
	sw $v0 56($fp)
	or $sp $0 $fp
	lw $ra 52($sp)
	lw $fp 48($sp)
	addiu $sp $sp 0x3c
	jr $ra
	nop
	.end	Main.Main

#------------------------------------------------------------------
# Main.factorial.int
#------------------------------------------------------------------

	.globl	Main.factorial.int
	.ent	Main.factorial.int
Main.factorial.int:
	addiu $sp $sp 0xffffffd8
	sw $ra 32($sp)
	sw $fp 28($sp)
	or $fp $0 $sp
	ori $a0 $0 0x1
	sw $a0 4($fp)
	lw $a0 40($fp)
	lw $a1 4($fp)
	nop
	beq $a0 $a1 Main.factorial.int.1
	nop
	j Main.factorial.int.2
	nop
Main.factorial.int.1:
	ori $a0 $0 0x1
	sw $a0 8($fp)
	nop
	lw $v0 8($fp)
	j Main.factorial.int.return
	nop
Main.factorial.int.2:
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x1
	sw $a0 16($fp)
	lw $a0 40($fp)
	lw $a1 16($fp)
	nop
	subu $v0 $a0 $a1
	sw $v0 12($fp)
	nop
	lw $a0 12($fp)
	nop
	sw $a0 0($sp)
	jal Main.factorial.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 20($fp)
	addiu $sp $sp 0x4
	lw $a0 20($fp)
	nop
	sw $a0 0($fp)
	lw $a0 40($fp)
	lw $a1 0($fp)
	nop
	mult $a0 $a1
	mflo $v0
	sw $v0 24($fp)
	nop
	lw $v0 24($fp)
	j Main.factorial.int.return
	nop
Main.factorial.int.return:
	sw $v0 36($fp)
	or $sp $0 $fp
	lw $ra 32($sp)
	lw $fp 28($sp)
	addiu $sp $sp 0x28
	jr $ra
	nop
	.end	Main.factorial.int

