//------------------------------------------------------------------//
//                        COPYRIGHT NOTICE                          //
//------------------------------------------------------------------//
// Copyright (c) 2017, Francisco José Moreno Velo                   //
// All rights reserved.                                             //
//                                                                  //
// Redistribution and use in source and binary forms, with or       //
// without modification, are permitted provided that the following  //
// conditions are met:                                              //
//                                                                  //
// * Redistributions of source code must retain the above copyright //
//   notice, this list of conditions and the following disclaimer.  // 
//                                                                  //
// * Redistributions in binary form must reproduce the above        // 
//   copyright notice, this list of conditions and the following    // 
//   disclaimer in the documentation and/or other materials         // 
//   provided with the distribution.                                //
//                                                                  //
// * Neither the name of the University of Huelva nor the names of  //
//   its contributors may be used to endorse or promote products    //
//   derived from this software without specific prior written      // 
//   permission.                                                    //
//                                                                  //
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND           // 
// CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,      // 
// INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF         // 
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE         // 
// DISCLAIMED. IN NO EVENT SHALL THE COPRIGHT OWNER OR CONTRIBUTORS //
// BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,         // 
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  //
// TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,    //
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND   // 
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT          //
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING   //
// IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF   //
// THE POSSIBILITY OF SUCH DAMAGE.                                  //
//------------------------------------------------------------------//

//------------------------------------------------------------------//
//                      Universidad de Huelva                       //
//          Departamento de Tecnologías de la Información           //
//   Área de Ciencias de la Computación e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//

package tinto.mips.instructions;

import tinto.mips.registers.Register;
import tinto.mips.registers.RegisterSet;

/**
 * Clase permite crear las instrucciones para el procesador MIPS-32
 * 
 * @author Francisco José Moreno Velo
 */
public class InstructionFactory implements InstructionSet {

	/**
	 * Crea una etiqueta "label:"
	 */
	public static Instruction createLabel(String label) 
	{
		return new LabelInstruction(LABEL, label);
	}

	//------------------------------------------------------------------//
	//               Aligned CPU Load/Store Instructions                //
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "lw  target offset(source)"
	 */
	public static Instruction createLW(Register target, Register reg, int offset) 
	{
		return new RDRInstruction(LW, target, reg, offset);
	}

	/**
	 * Crea una instrucción "sw rt offset(base)"
	 */
	public static Instruction createSW(Register source, Register reg, int offset) 
	{
		return new RDRInstruction(SW, source, reg, offset);
	}

	//------------------------------------------------------------------//
	//             Unaligned CPU Load and Store Instructions            //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//           Atomic Update CPU Load and Store Instructions          //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//              Coprocessor Load and Store Instructions             //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	// FPU Load and Store Instructions Using Register+Register Addressing//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//            ALU Instructions With an Immediate Operand            //
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "addiu target source value"
	 */
	public static Instruction createADDIU(Register target, Register source,	int value) 
	{
		return new RRIInstruction(ADDIU, target, source, value);
	}

	/**
	 * Crea una instrucción "li  target value". Es un alias de
	 * "ori target r0 value"
	 */
	public static Instruction createLI(Register target, int value) 
	{
		return new RRIInstruction(ORI, target, RegisterSet.r0, value);
	}

	/**
	 * Crea una instrucción "ori target r0 value"
	 */
	public static Instruction createORI(Register target, Register source, int value)
	{
		return new RRIInstruction(ORI, target, source, value);
	}

	/**
	 * Crea una instrucción "xori target r0 value"
	 */
	public static Instruction createXORI(Register target, Register source,int value) 
	{
		return new RRIInstruction(XORI, target, source, value);
	}

	/**
	 * Crea una instrucción "lui  target value"
	 */
	public static Instruction createLUI(Register target, int value) 
	{
		return new RIInstruction(LUI, target, value);
	}

	/**
	 * Crea una instrucción "slti target r0 value"
	 */
	public static Instruction createSLTI(Register target, Register source, int value) 
	{
		return new RRIInstruction(SLTI, target, source, value);
	}

	//------------------------------------------------------------------//
	//                  Three-Operand ALU Instructions                  //
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "addu target source1 source2"
	 */
	public static Instruction createADDU(Register target, Register source1,	Register source2) 
	{
		return new RRRInstruction(ADDU, target, source1, source2);
	}

	/**
	 * Crea una instrucción "and target source1 source2"
	 */
	public static Instruction createAND(Register target, Register source1,	Register source2) 
	{
		return new RRRInstruction(AND, target, source1, source2);
	}

	/**
	 * Crea una instrucción "or target source1 source2"
	 */
	public static Instruction createOR(Register target, Register source1, Register source2) 
	{
		return new RRRInstruction(OR, target, source1, source2);
	}

	/**
	 * Crea una instrucción "xor target source1 source2"
	 */
	public static Instruction createXOR(Register target, Register source1,Register source2) 
	{
		return new RRRInstruction(XOR, target, source1, source2);
	}

	/**
	 * Crea una instrucción "subu target source1 source2"
	 */
	public static Instruction createSUBU(Register target, Register source1,	Register source2) 
	{
		return new RRRInstruction(SUBU, target, source1, source2);
	}

	/**
	 * Crea una instrucción "move target source" Es un alias de
	 * "or target r0 source"
	 */
	public static Instruction createMOVE(Register target, Register source) 
	{
		return new RRRInstruction(OR, target, RegisterSet.r0, source);
	}

	/**
	 * Crea una instrucción "slt target source1 source2"
	 */
	public static Instruction createSLT(Register target, Register source1,Register source2) 
	{
		return new RRRInstruction(SLT, target, source1, source2);
	}

	/**
	 * Crea una instrucción "sltu target source1 source2"
	 */
	public static Instruction createSLTU(Register target, Register source1, Register source2) 
	{
		return new RRRInstruction(SLTU, target, source1, source2);
	}

	//------------------------------------------------------------------//
	//                   Two-Operand ALU Instructions                   //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                       Shift Instructions                         //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                   Multiply/Divide Instructions                   //
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "mult source1 source2"
	 */
	public static Instruction createMULT(Register source1, Register source2) 
	{
		return new RRInstruction(MULT, source1, source2);
	}

	/**
	 * Crea una instrucción "div source1 source2"
	 */
	public static Instruction createDIV(Register source1, Register source2) 
	{
		return new RRInstruction(DIV, source1, source2);
	}

	/**
	 * Crea una instrucción "mflo target"
	 */
	public static Instruction createMFLO(Register target) 
	{
		return new RInstruction(MFLO, target);
	}

	/**
	 * Crea una instrucción "mfhi target"
	 */
	public static Instruction createMFHI(Register target) 
	{
		return new RInstruction(MFHI, target);
	}

	// ------------------------------------------------------------------//
	// Unconditional Jump Within a 256 Megabyte Region //
	// ------------------------------------------------------------------//

	/**
	 * Crea una instrucción de salto incondicional "j label"
	 */
	public static Instruction createJ(String label) 
	{
		return new LabelInstruction(J, label);
	}

	/**
	 * Crea una instrucción de salto incondicional "jal label"
	 */
	public static Instruction createJAL(String label) 
	{
		return new LabelInstruction(JAL, label);
	}

	/**
	 * Crea una instrucción de salto incondicional "jr target"
	 */
	public static Instruction createJR(Register target) {
		return new RInstruction(JR, target);
	}
	
	//------------------------------------------------------------------//
	// PC-Relative Conditional Branch Instructions Comparing Two Regs   //
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción de salto condicional "beq s1 s2 label"
	 * 
	 * @param s1
	 *            Primer regsitro
	 * @param s2
	 *            Segundo registro
	 * @param label
	 *            Etiqueta de salto
	 * @return
	 */
	public static Instruction createBEQ(Register s1, Register s2, String label) 
	{
		return new RRLInstruction(BEQ, s1, s2, label);
	}

	/**
	 * Crea una instrucción de salto condicional "bne s1 s2 label"
	 * 
	 * @param s1
	 *            Primer regsitro
	 * @param s2
	 *            Segundo registro
	 * @param label
	 *            Etiqueta de salto
	 * @return
	 */
	public static Instruction createBNE(Register s1, Register s2, String label) 
	{
		return new RRLInstruction(BNE, s1, s2, label);
	}

	//------------------------------------------------------------------//
	//  PC-Relative Conditional Branch Instructions Comparing With Zero //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//               Deprecated Branch Likely Instructions              //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                     Serialization Instruction                    //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//              System Call and Breakpoint Instructions             //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//       Trap-on-Condition Instructions Comparing Two Registers     //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//    Trap-on-Condition Instructions Comparing an Immediate Value   //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                 CPU Conditional Move Instructions                //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                       Prefetch Instructions                      //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                         NOP Instructions                         //
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "nop"
	 */
	public static Instruction createNOP() 
	{
		return new NInstruction(NOP);
	}

	//------------------------------------------------------------------//
	//                 FPU Move To and From Instructions                //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                   FPU IEEE Arithmetic Operations                 //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//                     FPU Comparing Operations                     //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//               FPU-Approximate Arithmetic Operations              //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//           FPU Multiply-Accumulate Arithmetic Operations          //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//      FPU Conversion Operations Using the FCSR Rounding Mode      //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//     FPU Conversion Operations Using a Directed Rounding Mode     //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//              FPU Formatted Operand Move Instructions             //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//          FPU Conditional Move on True/False Instructions         //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//              FPU Conditional Branch Instructions                 //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//       Deprecated FPU Conditional Branch Likely Instructions      //
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//        CPU Conditional Move on FPU True/False Instructions       //
	//------------------------------------------------------------------//

}
