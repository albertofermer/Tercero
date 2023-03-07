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

package tinto.mips;

import java.io.PrintStream;
import tinto.code.*;
import tinto.mips.instructions.Instruction;
import tinto.mips.instructions.InstructionFactory;
import tinto.mips.registers.DispRegister;
import tinto.mips.registers.Register;
import tinto.mips.registers.RegisterSet;

import java.util.Vector;
import java.util.Stack;

/**
 * Clase que contiene la descripción una función en ensamblador
 * 
 * @author Francisco José Moreno Velo
 */
public class FunctionAssembler {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Etiqueta de comienzo del método
	 */
	private String label;

	/**
	 * Tamaño del registro de activación (en bytes)
	 */
	private int size;

	/**
	 * Pila de los tamaños de PRECALL. Es necesaria para conocer el
	 * desplazamiento del Stack Pointer a la vuelta de una llamada
	 */
	private Stack<CodeLiteral> callstack;

	/**
	 * Lista de instrucciones
	 */
	private Instruction[] list;

	/**
	 * Registro que está siendo leído en la última instrucción y que, por tanto,
	 * no puede ser utilizado hasta el siguiente ciclo.
	 */
	private Register fetchedRegister;
	
	/**
	 * Posición de memoria que está siendo escrita. Su valor no puede ser
	 * utilizado hasta el siguiente ciclo.
	 */
	private DispRegister fetchedMemory;
	
	//----------------------------------------------------------------//
	//                            Constructor                         //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 */
	public FunctionAssembler(FunctionCodification codif) 
	{
		this.label = codif.getFunctionLabel();
		this.size = codif.getFrameSize();
		this.callstack = new Stack<CodeLiteral>();
		this.list = createAssembler(codif);
	}

	//----------------------------------------------------------------//
	//                        Métodos públicos                        //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el nombre de la etiqueta de la función
	 */
	public String getMethodLabel() 
	{
		return this.label;
	}

	/**
	 * Obtiene la lista de instrucciones de la función
	 */
	public Instruction[] getInstructionList() 
	{
		return this.list;
	}

	/**
	 * Escribe el código completo de la función sobre un flujo
	 */
	public void print(PrintStream stream) 
	{
		stream.println("#------------------------------------------------------------------");
		stream.println("# " + label);
		stream.println("#------------------------------------------------------------------");
		stream.println();
		stream.println("\t.globl\t" + label);
		stream.println("\t.ent\t" + label);

		for (int i = 0; i < list.length; i++)
			stream.println(list[i].getAssembler());

		stream.println("\t.end\t" + label);
		stream.println();
	}

	//----------------------------------------------------------------//
	//                         Métodos privados                       //
	//----------------------------------------------------------------//

	/**
	 * Traduce el código intermedio de la función a código ensamblador
	 */
	private Instruction[] createAssembler(FunctionCodification codif) 
	{
		Vector<Instruction> vector = new Vector<Instruction>();
		int retsize = 4;

		// Instrucciones de entrada a una función:
		//
		// function_label:              # Etiqueta de salto a la función
		// addiu $sp $sp -size          # Reserva espacio para el registro de activación
		// sw $ra (size-8)($fp)         # Almacena la dirección de retorno
		// sw $fp (size-12)($fp)        # Almacena el frame pointer
		// move $fp $sp                 # Asigna el nuevo frame pointer
		//
		vector.add(InstructionFactory.createLabel(label));
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, -size));
		vector.add(InstructionFactory.createSW(RegisterSet.ra, RegisterSet.sp,size - retsize - 4)); 
		vector.add(InstructionFactory.createSW(RegisterSet.fp, RegisterSet.sp, size - retsize - 8));
		vector.add(InstructionFactory.createMOVE(RegisterSet.fp, RegisterSet.sp)); 

		// Traduce a ensamblador el cuerpo del método
		CodeInstruction codelist[] = codif.getCodeInstructionList().getList();
		for (int i = 0; i < codelist.length; i++) 
		{
			createAssembler(vector, codelist[i]);
		}

		// Instrucciones de salida de una función:
		//
		// function_label_ret:          # Etiqueta de retorno de la función
		// sw $v0 (size-4)($fp)         # Almacena el valor de retorno
		// move $sp $fp                 # Libera todo el registro de activación
		// lw $ra (size-8)($sp)         # Recupera la dirección de retorno
		// lw $fp (size-12)($sp)        # Recupera el frame pointer anterior
		// addiu $sp $sp size           # Recupera el stack pointer anterior
		// jr $ra                       # Salta a la direción de retorno
		// nop                          # Instrucción en blanco
		//
		vector.add(InstructionFactory.createLabel(label + ".return"));  
		vector.add(InstructionFactory.createSW(RegisterSet.v0, RegisterSet.fp,(size - retsize)));  
		vector.add(InstructionFactory.createMOVE(RegisterSet.sp, RegisterSet.fp)); 
		vector.add(InstructionFactory.createLW(RegisterSet.ra, RegisterSet.sp, (size - retsize - 4))); 
		vector.add(InstructionFactory.createLW(RegisterSet.fp, RegisterSet.sp, (size - retsize - 8))); 
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp,RegisterSet.sp, size));  
		vector.add(InstructionFactory.createJR(RegisterSet.ra)); 
		vector.add(InstructionFactory.createNOP());

		Instruction[] list = new Instruction[vector.size()];
		vector.toArray(list);
		return list;
	}

	/**
	 * Genera la descripción en ensamblador de cada una de las instrucciones del
	 * código intermedio
	 */
	private void createAssembler(Vector<Instruction> vector,CodeInstruction inst) 
	{
		int kind = inst.getKind();
		CodeAddress target = inst.getTarget();
		CodeAddress source1 = inst.getSource1();
		CodeAddress source2 = inst.getSource2();

		switch (kind) 
		{
		case CodeConstants.LABEL:
			translateLabel(vector, target);
			break;
		case CodeConstants.ASSIGN:
			translateASSIGN(vector, target, source1);
			break;
		case CodeConstants.ADD:
			translateADD(vector, target, source1, source2);
			break;
		case CodeConstants.SUB:
			translateSUB(vector, target, source1, source2);
			break;
		case CodeConstants.MUL:
			translateMUL(vector, target, source1, source2);
			break;
		case CodeConstants.DIV:
			translateDIV(vector, target, source1, source2);
			break;
		case CodeConstants.MOD:
			translateMOD(vector, target, source1, source2);
			break;
		case CodeConstants.INV:
			translateINV(vector, target, source1);
			break;
		case CodeConstants.AND:
			translateAND(vector, target, source1, source2);
			break;
		case CodeConstants.OR:
			translateOR(vector, target, source1, source2);
			break;
		case CodeConstants.NOT:
			translateNOT(vector, target, source1);
			break;
		case CodeConstants.JMPEQ:
			translateJMPEQ(vector, target, source1, source2);
			break;
		case CodeConstants.JMPNE:
			translateJMPNE(vector, target, source1, source2);
			break;
		case CodeConstants.JMPGT:
			translateJMPGT(vector, target, source1, source2);
			break;
		case CodeConstants.JMPGE:
			translateJMPGE(vector, target, source1, source2);
			break;
		case CodeConstants.JMPLT:
			translateJMPLT(vector, target, source1, source2);
			break;
		case CodeConstants.JMPLE:
			translateJMPLE(vector, target, source1, source2);
			break;
		case CodeConstants.JUMP:
			translateJUMP(vector, target);
			break;
		case CodeConstants.JMP1:
			translateJMP1(vector, target, source1);
			break;
		case CodeConstants.PARAM:
			translatePARAM(vector, target, source1);
			break;
		case CodeConstants.PRECALL:
			translatePRECALL(vector, target);
			break;
		case CodeConstants.CALL:
			translateCALL(vector, target, source1);
			break;
		case CodeConstants.RETURN:
			translateRETURN(vector, target);
			break;
		}
	}

	//----------------------------------------------------------------//
	//                  Traducción de las etiquetas                   //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una etiqueta
	 */
	private void translateLabel(Vector<Instruction> vector, CodeAddress label) 
	{
		CodeLabel codelb = (CodeLabel) label;
		vector.add(InstructionFactory.createLabel(codelb.toString()));
	}

	//----------------------------------------------------------------//
	//     Traducción de las instrucciones aritméticas de tipo int    //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una asignación de tipo int
	 */
	private void translateASSIGN(Vector<Instruction> vector,CodeAddress target, CodeAddress source) 
	{
		Register target_reg = getTargetRegister(target, RegisterSet.a0);
		Register reg = translateLoadIntValue(vector, source, target_reg);
		translateStoreIntValue(vector, target, reg);
	}

	/**
	 * Genera el código ensamblador para una suma entera
	 */
	private void translateADD(Vector<Instruction> vector, CodeAddress target,CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg,source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createADDU(target_reg, source1_reg, source2_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el código ensamblador para una resta entera
	 */
	private void translateSUB(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1,RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg,source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSUBU(target_reg, source1_reg,source2_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el código ensamblador para una multiplicación entera
	 */
	private void translateMUL(Vector<Instruction> vector, CodeAddress target,CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1,RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg,source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createMULT(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFLO(target_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el código ensamblador para una división entera
	 */
	private void translateDIV(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg,source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createDIV(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFLO(target_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el código ensamblador para el resto de una división entera
	 */
	private void translateMOD(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg,source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createDIV(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFHI(target_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el código ensamblador para un cambio de signo
	 */
	private void translateINV(Vector<Instruction> vector, CodeAddress target, CodeAddress source) 
	{
		Register source_reg = translateLoadIntValue(vector, source, RegisterSet.a0);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSUBU(target_reg, RegisterSet.r0, source_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	//----------------------------------------------------------------//
	//             Traducción de las instrucciones booleanas          //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una conjunción lógica
	 */
	private void translateAND(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg,source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createAND(target_reg, source1_reg, source2_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el código ensamblador para una disyunción lógica
	 */
	private void translateOR(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg,source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createOR(target_reg, source1_reg, source2_reg));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el código ensamblador para una negación lógica
	 */
	private void translateNOT(Vector<Instruction> vector, CodeAddress target,CodeAddress source1) 
	{ 
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		if(needsNOP(source1_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSLTI(target_reg, source1_reg, 1));
		setFetched(null,null,0);
		translateStoreIntValue(vector, target, target_reg);
	}

	//----------------------------------------------------------------//
	//      Instrucciones de salto condicional para el tipo int       //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para un salto condicional 
	 * jump if(source1 == source2)
	 */
	private void translateJMPEQ(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		if(needsNOP(source1_reg, source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createBEQ(source1_reg, source2_reg, label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	/**
	 * Genera el código ensamblador para un salto condicional 
	 * jump if(source1 != source2)
	 */
	private void translateJMPNE(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		if(needsNOP(source1_reg, source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createBNE(source1_reg, source2_reg, label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	/**
	 * Genera el código ensamblador para un salto condicional 
	 * jump if(source1 > source2)
	 */
	private void translateJMPGT(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		if(needsNOP(source1_reg, source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source2_reg, source1_reg));
		vector.add(InstructionFactory.createBNE(RegisterSet.v0, RegisterSet.r0,	label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	/**
	 * Genera el código ensamblador para un salto condicional 
	 * jump if(source1 >= source2)
	 */
	private void translateJMPGE(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		if(needsNOP(source1_reg, source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source1_reg, source2_reg));
		vector.add(InstructionFactory.createBEQ(RegisterSet.v0, RegisterSet.r0,	label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	/**
	 * Genera el código ensamblador para un salto condicional 
	 * jump if(source1 < source2)
	 */
	private void translateJMPLT(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2,RegisterSet.a1);
		String label = target.toString();
		if(needsNOP(source1_reg, source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source1_reg, source2_reg));
		vector.add(InstructionFactory.createBNE(RegisterSet.v0, RegisterSet.r0,	label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	/**
	 * Genera el código ensamblador para un salto condicional si source1 <=
	 * source2
	 */
	private void translateJMPLE(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		if(needsNOP(source1_reg, source2_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source2_reg, source1_reg));
		vector.add(InstructionFactory.createBEQ(RegisterSet.v0, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	//----------------------------------------------------------------//
	//                  Otras instrucciones de salto                  //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para un salto incondicional
	 */
	private void translateJUMP(Vector<Instruction> vector, CodeAddress target) 
	{
		String label = target.toString();
		vector.add(InstructionFactory.createJ(label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	/**
	 * Genera el código ensamblador para un salto condicional 
	 * jump if(source != 0)
	 */
	private void translateJMP1(Vector<Instruction> vector, CodeAddress target, CodeAddress source1) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		String label = target.toString();
		if(needsNOP(source1_reg)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createBNE(source1_reg, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	//----------------------------------------------------------------//
	//                Instrucciones de manejo de funciones            //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una instrucción "return temp"
	 */
	private void translateRETURN(Vector<Instruction> vector, CodeAddress target) 
	{
		Register r = translateLoadIntValue(vector, target, RegisterSet.v0);
		if (r != RegisterSet.v0) vector.add(InstructionFactory.createMOVE(RegisterSet.v0, r));
		vector.add(InstructionFactory.createJ(this.label + ".return"));
		vector.add(InstructionFactory.createNOP());
		setFetched(null,null,0);
	}

	/**
	 * Genera el código para almacenar el valor de un argumento de la próxima
	 * llamada
	 * 
	 * @param vector  Lista de instrucciones
	 * @param target  Variable que contiene el valor del argumento
	 * @param source  Posición del argumento respecto al Stack Pointer
	 */
	private void translatePARAM(Vector<Instruction> vector, CodeAddress target, CodeAddress source) 
	{
		Register r = translateLoadIntValue(vector, target, RegisterSet.a0);
		String hex = ((CodeLiteral) source).getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		if(needsNOP(r)) vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createSW(r, RegisterSet.sp, offset));
		setFetched(null,RegisterSet.sp, offset);
	}

	/**
	 * Genera el código previo a una llamada a una función (desplazamiento del
	 * Stack Pointer) conocido los argumentos de la llamada
	 */
	private void translatePRECALL(Vector<Instruction> vector, CodeAddress target) 
	{
		callstack.push((CodeLiteral) target);
		String hex = ((CodeLiteral) target).getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp,RegisterSet.sp, -offset));
		setFetched(null,null,0);
	}

	/**
	 * Genera el código ensamblador para una instrucción "call target function"
	 * 
	 * @param vector    Lista de instrucciones
	 * @param target    Variable en la que almacenar el resultado
	 * @param function  Etiqueta de salto a la función
	 */
	private void translateCALL(Vector<Instruction> vector, CodeAddress target,	CodeAddress function) 
	{
		vector.add(InstructionFactory.createJAL(function.toString()));
		vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createLW(RegisterSet.v0, RegisterSet.sp,	-4));
		vector.add(InstructionFactory.createNOP());
		translateStoreIntValue(vector, target, RegisterSet.v0);
		CodeLiteral argsize = callstack.pop();
		String hex = argsize.getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, offset));
		setFetched(null,null,0);
	}

	//----------------------------------------------------------------//
	//     Instrucciones de intercambio a memoria para el tipo int    //
	//----------------------------------------------------------------//

	/**
	 * Genera el código para volcar un valor (un literal o una variable) en un
	 * registro
	 * 
	 * @param vector    Lista de instrucciones
	 * @param address   Referencia al valor a almacenar
	 * @param r         Registro propuesto para el destino
	 * @return Registro en el que se coloca el valor
	 */
	private Register translateLoadIntValue(Vector<Instruction> vector,	CodeAddress address, Register r) 
	{
		if (address instanceof CodeLiteral)  // Referencia a un valor constante
		{
			setFetched(null,null,0);
			return translateLoadLiteral(vector, (CodeLiteral) address, r);
		}
		else  // Referencia a una variable
		{
			CodeVariable svar = (CodeVariable) address;
			if (svar.inRegister())  // Variables almacenadas en registro
			{
				return RegisterSet.getRegister(svar.getLocation());
			} 
			else // Variables almacenadas en el registro de activación de la función
			{ 
				if(needsNOP(RegisterSet.fp, svar.getLocation())) vector.add(InstructionFactory.createNOP());
				vector.add(InstructionFactory.createLW(r, RegisterSet.fp, svar.getLocation()));
				setFetched(r,null,0);
				return r;
			}
		}
	}

	/**
	 * Genera el código para colocar un valor constante (literal) en un registro
	 */
	private Register translateLoadLiteral(Vector<Instruction> vector, CodeLiteral literal, Register r) 
	{
		String hex = literal.getHexDescription();
		int value = Integer.parseInt(hex, 16);
		if (value<0 || value > 0x0FFFF)  // Valores mayores de 16 bits
		{
			int upper = (value & 0xFFFF0000) >> 16;
			int lower = (value & 0x0000FFFF);
			vector.add(InstructionFactory.createLUI(r, upper));
			vector.add(InstructionFactory.createORI(r, r, lower));
		} 
		else  // Valores menores de 16 bits
		{
			vector.add(InstructionFactory.createLI(r, value));
		}
		return r;
	}

	/**
	 * Obtiene el registro donde almacenar el resultado de una operación. Si la
	 * variable de destino se almacena en un registro entonces devuelve dicho
	 * registro. Si la variable de destino se almacena en memoria devuelve el
	 * registro destino propuesto.
	 * 
	 * @param address  Variable de destino de la operación
	 * @param r        Registro propuesto como destino
	 * @return
	 */
	private Register getTargetRegister(CodeAddress address, Register r) 
	{
		CodeVariable tvar = (CodeVariable) address;
		if (tvar.inRegister())
			return RegisterSet.getRegister(tvar.getLocation());
		else
			return r;
	}

	/**
	 * Genera el código para almacenar un valor en una variable
	 * 
	 * @param vector   Lista de instrucciones
	 * @param address  Referencia a la variable
	 * @param r        Registro en el que se encuentra el valor a almacenar
	 */
	private void translateStoreIntValue(Vector<Instruction> vector, CodeAddress address, Register r) 
	{
		CodeVariable tvar = (CodeVariable) address;
		if(needsNOP(r)) vector.add(InstructionFactory.createNOP());

		if (tvar.inRegister() && tvar.getLocation() != r.getCode()) 
		{ 
			Register target = RegisterSet.getRegister(tvar.getLocation());
			vector.add(InstructionFactory.createMOVE(target, r));
			setFetched(null,null,0);
		} 
		else if (!tvar.inRegister()) // Variables almacenadas en el registro de activación de una función
		{ 
			vector.add(InstructionFactory.createSW(r, RegisterSet.fp, tvar.getLocation()));
			setFetched(null,RegisterSet.fp, tvar.getLocation());
		}
	}

	//----------------------------------------------------------------//
	//           Control de los registros fetched                     //
	//----------------------------------------------------------------//

	/**
	 * Estudia si es necesario incluir una operación NOP
	 */
	private boolean needsNOP(Register r)
	{
		if(this.fetchedRegister == r) return true;
		return false;
	}
	
	/**
	 * Estudia si es necesario incluir una operación NOP
	 */
	private boolean needsNOP(Register r1, Register r2)
	{
		if(this.fetchedRegister == r1 || this.fetchedRegister == r2) return true;
		return false;
	}
	
	/**
	 * Estudia si es necesario incluir una operación NOP
	 */
	private boolean needsNOP(Register dr, int dl)
	{
		if(this.fetchedMemory != null && this.fetchedMemory.equals(dr,dl)) return true;
		return false;
	}
	
	/**
	 * Asigna los valores de los marcadores fetched
	 */
	private void setFetched(Register r, Register dr, int dl)
	{
		this.fetchedRegister = r;
		this.fetchedMemory = new DispRegister(dl,dr);
	}
}
