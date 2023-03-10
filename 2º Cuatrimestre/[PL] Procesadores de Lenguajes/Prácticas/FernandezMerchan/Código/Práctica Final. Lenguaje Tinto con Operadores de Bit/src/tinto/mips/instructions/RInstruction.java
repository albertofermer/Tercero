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
 * Clase que describe una instrucci?n sobre un registro
 * 
 * @author Francisco Jos? Moreno Velo
 */
public class RInstruction extends Instruction {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Referencia al registro de destino de la instrucci?n
	 */
	private Register target;

	//----------------------------------------------------------------//
	//                            Constructor                         //
	//----------------------------------------------------------------//

	/**
	 * Constructor basado en las referencias a los registros
	 */
	public RInstruction(int code, Register target) 
	{
		super(code);
		this.target = target;
	}

	/**
	 * Constructor basado en los c?digos de los registros
	 */
	public RInstruction(int code, int t) 
	{
		super(code);
		this.target = new Register(t);
	}

	//----------------------------------------------------------------//
	//                        M?todos p?blicos                        //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la referencia al registro de destino
	 */
	public Register getTarget() 
	{
		return this.target;
	}

	/**
	 * Obtiene la representaci?n de la instrucci?n en ensamblador
	 */
	public String getAssembler() 
	{
		String asm = "\t" + getInstructionName();
		asm += " " + target.getName();
		return asm;
	}
}
