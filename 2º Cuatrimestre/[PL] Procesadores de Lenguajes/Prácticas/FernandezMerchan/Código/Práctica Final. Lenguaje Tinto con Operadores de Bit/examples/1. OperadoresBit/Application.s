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


#------------------------------------------------------------------
# Copyright (c) 2017, Francisco José Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------
#------------------------------------------------------------------
# Main.Main
#------------------------------------------------------------------

	.globl	Main.Main
	.ent	Main.Main
Main.Main:
	addiu $sp $sp 0xfffffc94
	sw $ra 868($sp)
	sw $fp 864($sp)
	or $fp $0 $sp
	ori $a0 $0 0x2a
	sw $a0 12($fp)
	nop
	lw $a0 12($fp)
	nop
	nor $v0 $a0 $0
	sw $v0 16($fp)
	nop
	lw $a0 16($fp)
	nop
	sw $a0 0($fp)
	ori $a0 $0 0xf
	sw $a0 20($fp)
	nop
	lw $a0 20($fp)
	nop
	sw $a0 4($fp)
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x54
	sw $a0 24($fp)
	nop
	lw $a0 24($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 28($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x49
	sw $a0 32($fp)
	nop
	lw $a0 32($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 36($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x4c
	sw $a0 40($fp)
	nop
	lw $a0 40($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 44($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x44
	sw $a0 48($fp)
	nop
	lw $a0 48($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 52($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x45
	sw $a0 56($fp)
	nop
	lw $a0 56($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 60($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x28
	sw $a0 64($fp)
	nop
	lw $a0 64($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 68($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x7e
	sw $a0 72($fp)
	nop
	lw $a0 72($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 76($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	nor $v0 $a0 $0
	sw $v0 80($fp)
	nop
	lw $a0 80($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 84($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x29
	sw $a0 88($fp)
	nop
	lw $a0 88($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 92($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3a
	sw $a0 96($fp)
	nop
	lw $a0 96($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 100($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 104($fp)
	nop
	lw $a0 104($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 108($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Main.imprimir.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 112($fp)
	addiu $sp $sp 0x4
	ori $a0 $0 0x2a
	sw $a0 116($fp)
	nop
	lw $a0 116($fp)
	nop
	sw $a0 0($fp)
	nop
	lw $a0 0($fp)
	lw $a1 4($fp)
	nop
	and $v0 $a0 $a1
	sw $v0 120($fp)
	nop
	lw $a0 120($fp)
	nop
	sw $a0 8($fp)
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x42
	sw $a0 124($fp)
	nop
	lw $a0 124($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 128($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x49
	sw $a0 132($fp)
	nop
	lw $a0 132($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 136($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x54
	sw $a0 140($fp)
	nop
	lw $a0 140($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 144($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x5f
	sw $a0 148($fp)
	nop
	lw $a0 148($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 152($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x41
	sw $a0 156($fp)
	nop
	lw $a0 156($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 160($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x4e
	sw $a0 164($fp)
	nop
	lw $a0 164($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 168($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x44
	sw $a0 172($fp)
	nop
	lw $a0 172($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 176($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x28
	sw $a0 180($fp)
	nop
	lw $a0 180($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 184($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 188($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x26
	sw $a0 192($fp)
	nop
	lw $a0 192($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 196($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 200($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x29
	sw $a0 204($fp)
	nop
	lw $a0 204($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 208($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3a
	sw $a0 212($fp)
	nop
	lw $a0 212($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 216($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 220($fp)
	nop
	lw $a0 220($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 224($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 8($fp)
	nop
	sw $a0 0($sp)
	jal Main.imprimir.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 228($fp)
	addiu $sp $sp 0x4
	lw $a0 0($fp)
	lw $a1 4($fp)
	nop
	or $v0 $a0 $a1
	sw $v0 232($fp)
	nop
	lw $a0 232($fp)
	nop
	sw $a0 8($fp)
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x42
	sw $a0 236($fp)
	nop
	lw $a0 236($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 240($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x49
	sw $a0 244($fp)
	nop
	lw $a0 244($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 248($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x54
	sw $a0 252($fp)
	nop
	lw $a0 252($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 256($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x5f
	sw $a0 260($fp)
	nop
	lw $a0 260($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 264($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x4f
	sw $a0 268($fp)
	nop
	lw $a0 268($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 272($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x52
	sw $a0 276($fp)
	nop
	lw $a0 276($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 280($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x28
	sw $a0 284($fp)
	nop
	lw $a0 284($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 288($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 292($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x7c
	sw $a0 296($fp)
	nop
	lw $a0 296($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 300($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 304($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x29
	sw $a0 308($fp)
	nop
	lw $a0 308($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 312($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3a
	sw $a0 316($fp)
	nop
	lw $a0 316($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 320($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 324($fp)
	nop
	lw $a0 324($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 328($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 8($fp)
	nop
	sw $a0 0($sp)
	jal Main.imprimir.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 332($fp)
	addiu $sp $sp 0x4
	lw $a0 0($fp)
	lw $a1 4($fp)
	nop
	xor $v0 $a0 $a1
	sw $v0 336($fp)
	nop
	lw $a0 336($fp)
	nop
	sw $a0 8($fp)
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x42
	sw $a0 340($fp)
	nop
	lw $a0 340($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 344($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x49
	sw $a0 348($fp)
	nop
	lw $a0 348($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 352($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x54
	sw $a0 356($fp)
	nop
	lw $a0 356($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 360($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x5f
	sw $a0 364($fp)
	nop
	lw $a0 364($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 368($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x58
	sw $a0 372($fp)
	nop
	lw $a0 372($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 376($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x4f
	sw $a0 380($fp)
	nop
	lw $a0 380($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 384($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x52
	sw $a0 388($fp)
	nop
	lw $a0 388($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 392($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x28
	sw $a0 396($fp)
	nop
	lw $a0 396($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 400($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 404($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x5e
	sw $a0 408($fp)
	nop
	lw $a0 408($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 412($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 416($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x29
	sw $a0 420($fp)
	nop
	lw $a0 420($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 424($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3a
	sw $a0 428($fp)
	nop
	lw $a0 428($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 432($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 436($fp)
	nop
	lw $a0 436($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 440($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 8($fp)
	nop
	sw $a0 0($sp)
	jal Main.imprimir.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 444($fp)
	addiu $sp $sp 0x4
	ori $a0 $0 0x5
	sw $a0 452($fp)
	lw $a0 0($fp)
	lw $a1 452($fp)
	nop
	sllv $v0 $a0 $a1
	sw $v0 448($fp)
	nop
	lw $a0 448($fp)
	nop
	sw $a0 8($fp)
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x4c
	sw $a0 456($fp)
	nop
	lw $a0 456($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 460($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x5f
	sw $a0 464($fp)
	nop
	lw $a0 464($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 468($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x53
	sw $a0 472($fp)
	nop
	lw $a0 472($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 476($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x48
	sw $a0 480($fp)
	nop
	lw $a0 480($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 484($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x49
	sw $a0 488($fp)
	nop
	lw $a0 488($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 492($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x46
	sw $a0 496($fp)
	nop
	lw $a0 496($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 500($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x54
	sw $a0 504($fp)
	nop
	lw $a0 504($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 508($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x28
	sw $a0 512($fp)
	nop
	lw $a0 512($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 516($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 520($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3c
	sw $a0 524($fp)
	nop
	lw $a0 524($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 528($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3c
	sw $a0 532($fp)
	nop
	lw $a0 532($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 536($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x35
	sw $a0 540($fp)
	nop
	lw $a0 540($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 544($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x29
	sw $a0 548($fp)
	nop
	lw $a0 548($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 552($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3a
	sw $a0 556($fp)
	nop
	lw $a0 556($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 560($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 564($fp)
	nop
	lw $a0 564($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 568($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 8($fp)
	nop
	sw $a0 0($sp)
	jal Main.imprimir.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 572($fp)
	addiu $sp $sp 0x4
	ori $a0 $0 0x2a
	sw $a0 576($fp)
	nop
	lw $a0 576($fp)
	nop
	subu $v0 $0 $a0
	sw $v0 580($fp)
	nop
	lw $a0 580($fp)
	nop
	sw $a0 0($fp)
	ori $a0 $0 0x3
	sw $a0 588($fp)
	lw $a0 0($fp)
	lw $a1 588($fp)
	nop
	srav $v0 $a0 $a1
	sw $v0 584($fp)
	nop
	lw $a0 584($fp)
	nop
	sw $a0 8($fp)
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x52
	sw $a0 592($fp)
	nop
	lw $a0 592($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 596($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x53
	sw $a0 600($fp)
	nop
	lw $a0 600($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 604($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x5f
	sw $a0 608($fp)
	nop
	lw $a0 608($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 612($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x53
	sw $a0 616($fp)
	nop
	lw $a0 616($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 620($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x48
	sw $a0 624($fp)
	nop
	lw $a0 624($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 628($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x49
	sw $a0 632($fp)
	nop
	lw $a0 632($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 636($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x46
	sw $a0 640($fp)
	nop
	lw $a0 640($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 644($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x54
	sw $a0 648($fp)
	nop
	lw $a0 648($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 652($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x28
	sw $a0 656($fp)
	nop
	lw $a0 656($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 660($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 664($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3e
	sw $a0 668($fp)
	nop
	lw $a0 668($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 672($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3e
	sw $a0 676($fp)
	nop
	lw $a0 676($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 680($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x33
	sw $a0 684($fp)
	nop
	lw $a0 684($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 688($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x29
	sw $a0 692($fp)
	nop
	lw $a0 692($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 696($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3a
	sw $a0 700($fp)
	nop
	lw $a0 700($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 704($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 708($fp)
	nop
	lw $a0 708($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 712($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 8($fp)
	nop
	sw $a0 0($sp)
	jal Main.imprimir.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 716($fp)
	addiu $sp $sp 0x4
	ori $a0 $0 0x3
	sw $a0 724($fp)
	lw $a0 0($fp)
	lw $a1 724($fp)
	nop
	srlv $v0 $a0 $a1
	sw $v0 720($fp)
	nop
	lw $a0 720($fp)
	nop
	sw $a0 8($fp)
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x52
	sw $a0 728($fp)
	nop
	lw $a0 728($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 732($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x55
	sw $a0 736($fp)
	nop
	lw $a0 736($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 740($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x5f
	sw $a0 744($fp)
	nop
	lw $a0 744($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 748($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x53
	sw $a0 752($fp)
	nop
	lw $a0 752($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 756($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x48
	sw $a0 760($fp)
	nop
	lw $a0 760($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 764($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x49
	sw $a0 768($fp)
	nop
	lw $a0 768($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 772($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x46
	sw $a0 776($fp)
	nop
	lw $a0 776($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 780($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x54
	sw $a0 784($fp)
	nop
	lw $a0 784($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 788($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x28
	sw $a0 792($fp)
	nop
	lw $a0 792($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 796($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 800($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3e
	sw $a0 804($fp)
	nop
	lw $a0 804($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 808($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3e
	sw $a0 812($fp)
	nop
	lw $a0 812($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 816($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3e
	sw $a0 820($fp)
	nop
	lw $a0 820($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 824($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x33
	sw $a0 828($fp)
	nop
	lw $a0 828($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 832($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x29
	sw $a0 836($fp)
	nop
	lw $a0 836($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 840($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x3a
	sw $a0 844($fp)
	nop
	lw $a0 844($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 848($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0x20
	sw $a0 852($fp)
	nop
	lw $a0 852($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 856($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	lw $a0 8($fp)
	nop
	sw $a0 0($sp)
	jal Main.imprimir.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 860($fp)
	addiu $sp $sp 0x4
Main.Main.return:
	sw $v0 872($fp)
	or $sp $0 $fp
	lw $ra 868($sp)
	lw $fp 864($sp)
	addiu $sp $sp 0x36c
	jr $ra
	nop
	.end	Main.Main

#------------------------------------------------------------------
# Main.imprimir.int
#------------------------------------------------------------------

	.globl	Main.imprimir.int
	.ent	Main.imprimir.int
Main.imprimir.int:
	addiu $sp $sp 0xffffffe8
	sw $ra 16($sp)
	sw $fp 12($sp)
	or $fp $0 $sp
	addiu $sp $sp 0xfffffffc
	lw $a0 24($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.int
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 0($fp)
	addiu $sp $sp 0x4
	addiu $sp $sp 0xfffffffc
	ori $a0 $0 0xa
	sw $a0 4($fp)
	nop
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console.print.char
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 8($fp)
	addiu $sp $sp 0x4
Main.imprimir.int.return:
	sw $v0 20($fp)
	or $sp $0 $fp
	lw $ra 16($sp)
	lw $fp 12($sp)
	addiu $sp $sp 0x18
	jr $ra
	nop
	.end	Main.imprimir.int

