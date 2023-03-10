//------------------------------------------------------------------//
//                        COPYRIGHT NOTICE                          //
//------------------------------------------------------------------//
// Copyright (c) 2017, Francisco Jos? Moreno Velo                   //
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
//          Departamento de Tecnolog?as de la Informaci?n           //
//   ?rea de Ciencias de la Computaci?n e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//

package tinto.mips.instructions;

import tinto.mips.registers.Register;

/**
 * Clase que describe una instrucci?n con dos registros y una etiqueta
 * 
 * @author Francisco Jos? Moreno Velo
 */
public class RRLInstruction extends Instruction {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Referencia al primer registro
	 */
	private Register reg1;

	/**
	 * Referencia al segundo registro
	 */
	private Register reg2;

	/**
	 * Etiqueta
	 */
	private String label;

	//----------------------------------------------------------------//
	//                            Constructor                         //
	//----------------------------------------------------------------//

	/**
	 * Constructor basado en las referencias a los registros
	 */
	public RRLInstruction(int code, Register r1, Register r2, String lb) 
	{
		super(code);
		this.reg1 = r1;
		this.reg2 = r2;
		this.label = lb;
	}

	/**
	 * Constructor basado en los c?digos de los registros
	 */
	public RRLInstruction(int code, int s1, int s2, String lb) 
	{
		super(code);
		this.reg1 = new Register(s1);
		this.reg2 = new Register(s2);
		this.label = lb;
	}

	//----------------------------------------------------------------//
	//                        M?todos p?blicos                        //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el valor de la etiqueta
	 */
	public String getLabel() 
	{
		return this.label;
	}

	/**
	 * Obtiene la referencia al primer registro
	 */
	public Register getRegister1() 
	{
		return this.reg1;
	}

	/**
	 * Obtiene la referencia al segundo registro
	 */
	public Register getRegister2() 
	{
		return this.reg2;
	}

	/**
	 * Obtiene la representaci?n de la instrucci?n en ensamblador
	 */
	public String getAssembler() 
	{
		String asm = "\t" + getInstructionName();
		asm += " " + reg1.getName();
		asm += " " + reg2.getName();
		asm += " " + label;
		return asm;
	}

}
