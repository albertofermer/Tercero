#------------------------------------------------------------------
# Copyright (c) 2017, Francisco José Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------
#------------------------------------------------------------------
# Main_Main
#------------------------------------------------------------------

	.globl	Main_Main
	.ent	Main_Main
Main_Main:
	addiu $sp $sp -60
	sw $ra 52($sp)
	sw $fp 48($sp)
	or $fp $0 $sp
	ori $a0 $0 5
	sw $a0 8($fp)
	lw $a0 8($fp)
	nop
	sw $a0 0($fp)
	addiu $sp $sp -4
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Main_factorial_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 12($fp)
	addiu $sp $sp 4
	lw $a0 12($fp)
	nop
	sw $a0 4($fp)
	addiu $sp $sp -4
	lw $a0 0($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 16($fp)
	addiu $sp $sp 4
	addiu $sp $sp -4
	ori $a0 $0 33
	sw $a0 20($fp)
	lw $a0 20($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_1
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 24($fp)
	addiu $sp $sp 4
	addiu $sp $sp -4
	ori $a0 $0 61
	sw $a0 28($fp)
	lw $a0 28($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_1
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 32($fp)
	addiu $sp $sp 4
	addiu $sp $sp -4
	ori $a0 $0 32
	sw $a0 36($fp)
	lw $a0 36($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_1
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 40($fp)
	addiu $sp $sp 4
	addiu $sp $sp -4
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 44($fp)
	addiu $sp $sp 4
Main_Main_ret:
	sw $v0 56($fp)
	or $sp $0 $fp
	lw $ra 52($sp)
	lw $fp 48($sp)
	addiu $sp $sp 60
	jr $ra
	nop
	.end	Main_Main

#------------------------------------------------------------------
# Main_factorial_0
#------------------------------------------------------------------

	.globl	Main_factorial_0
	.ent	Main_factorial_0
Main_factorial_0:
	addiu $sp $sp -40
	sw $ra 32($sp)
	sw $fp 28($sp)
	or $fp $0 $sp
	ori $a0 $0 1
	sw $a0 4($fp)
	lw $a0 40($fp)
	lw $a1 4($fp)
	nop
	beq $a0 $a1 Main_factorial_0_1
	nop
	j Main_factorial_0_2
	nop
Main_factorial_0_1:
	ori $a0 $0 1
	sw $a0 8($fp)
	lw $v0 8($fp)
	j Main_factorial_0_ret
	nop
Main_factorial_0_2:
	addiu $sp $sp -4
	ori $a0 $0 1
	sw $a0 16($fp)
	lw $a0 40($fp)
	lw $a1 16($fp)
	nop
	subu $v0 $a0 $a1
	sw $v0 12($fp)
	lw $a0 12($fp)
	nop
	sw $a0 0($sp)
	jal Main_factorial_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 20($fp)
	addiu $sp $sp 4
	lw $a0 20($fp)
	nop
	sw $a0 0($fp)
	lw $a0 40($fp)
	lw $a1 0($fp)
	nop
	mult $a0 $a1
	mflo $v0
	sw $v0 24($fp)
	lw $v0 24($fp)
	j Main_factorial_0_ret
	nop
Main_factorial_0_ret:
	sw $v0 36($fp)
	or $sp $0 $fp
	lw $ra 32($sp)
	lw $fp 28($sp)
	addiu $sp $sp 40
	jr $ra
	nop
	.end	Main_factorial_0

