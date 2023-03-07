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

package tinto.code;

import tinto.ast.*;
import tinto.ast.expression.*;
import tinto.ast.struct.*;
import tinto.ast.statement.*;

/**
 * Clase que traduce el árbol de sintaxis abstracta a código intermedio
 *  
 * @author Francisco José Moreno Velo
 *
 */
public class CodeGenerator implements CodeConstants {

	/**
	 * Constructor
	 */
	public CodeGenerator() 
	{
	}

	//------------------------------------------------------------------------//
	// Métodos de generacion de código intermedio                             //
	//------------------------------------------------------------------------//

	/**
	 * Genera el código intermedio de la biblioteca completa
	 */
	public LibraryCodification generateLibraryCodification(LibraryDeclaration library) 
	{
		String libname = library.getName();
		Function[] function = library.getFunctions();
		String[] imported = library.getImported();

		LibraryCodification codif = new LibraryCodification(libname,imported,function.length);

		for(int i=0; i<function.length; i++) 
		{
			FunctionCodification mc = generateFunctionCode(function[i]);
			codif.setFunctionCodification(i,mc);
		}
		return codif;
	}

	/**
	 * Genera el código asociado a una función
	 */
	private FunctionCodification generateFunctionCode(Function function) 
	{
		Variable[] arg = function.getArguments();
		Variable[] local = function.getLocalVariables();

		FunctionCodification mc = new FunctionCodification(function.getLabel(),function.getType(),arg,local);
		BlockStatement body = function.getBody();
		CodeInstructionList list = generateCodeOfBlockStatement(mc,body);
		mc.setInstructionList(list);
		return mc;
	}

	/**
	 * Genera el código de una instrucción
	 * @param mc Descripción del método al que pertenece la instrucción
	 * @param stm Instrucción a tratar
	 */
	private CodeInstructionList generateCodeOfStatement(FunctionCodification mc, Statement stm) 
	{
		if(stm instanceof BlockStatement) 
		{
			return generateCodeOfBlockStatement(mc,(BlockStatement) stm);
		} 
		else if(stm instanceof IfStatement) 
		{
			return generateCodeOfIfStatement(mc, (IfStatement) stm);
		} 
		else if(stm instanceof WhileStatement) 
		{
			return generateCodeOfWhileStatement(mc, (WhileStatement) stm); 
		} 
		else if(stm instanceof ReturnStatement) 
		{
			return generateCodeOfReturnStatement(mc, (ReturnStatement) stm);
		} 
		else if(stm instanceof CallStatement) 
		{
			return generateCodeOfCallStatement(mc, (CallStatement) stm);
		} 
		else if(stm instanceof AssignStatement) 
		{
			return generateCodeOfAssignStatement(mc, (AssignStatement) stm);
		} 
		else 
		{
			return new CodeInstructionList();
		}
	}

	/**
	 * Genera el código de un conjunto de instrucciones
	 */
	private CodeInstructionList generateCodeOfBlockStatement(FunctionCodification mc, BlockStatement block) 
	{
		CodeInstructionList list = new CodeInstructionList();
		Statement[] inst = block.getStatementList();
		for(int i=0; i<inst.length; i++) 
		{
			CodeInstructionList instcode =  generateCodeOfStatement(mc,inst[i]);
			list.addInstructionList(instcode.getList());
		}
		return list;
	}

	/**
	 * Genera el código de una instrucción IF
	 */
	private CodeInstructionList generateCodeOfIfStatement(FunctionCodification mc, IfStatement inst) 
	{
		Expression condition = inst.getCondition();
		Statement thenInst = inst.getThenInstruction();
		Statement elseInst = inst.getElseInstruction();
		CodeLabel lbTrue = mc.getNewLabel();
		CodeLabel lbFalse = mc.getNewLabel();
		CodeInstruction lbTrueInst = new CodeInstruction(LABEL,lbTrue,null,null);
		CodeInstruction lbFalseInst = new CodeInstruction(LABEL,lbFalse,null,null);

		CodeInstructionList condinst = generateCodeForCondition(mc,condition,lbTrue,lbFalse);
		CodeInstructionList theninst = generateCodeOfStatement(mc,thenInst);
		CodeInstructionList elseinst = generateCodeOfStatement(mc,elseInst);

		CodeInstructionList codelist = new CodeInstructionList();
		codelist.addInstructionList(condinst.getList());
		codelist.addInstruction(lbTrueInst);
		codelist.addInstructionList(theninst.getList());

		if(elseInst == null) 
		{
			codelist.addInstruction(lbFalseInst);
		} 
		else 
		{
			CodeLabel lbEnd = mc.getNewLabel();
			CodeInstruction lbEndInst = new CodeInstruction(LABEL,lbEnd,null,null);	
			CodeInstruction gotoEnd = new CodeInstruction(JUMP,lbEnd,null,null);
			codelist.addInstruction(gotoEnd);
			codelist.addInstruction(lbFalseInst);
			codelist.addInstructionList(elseinst.getList());
			codelist.addInstruction(lbEndInst);
		}

		return codelist;
	}

	/**
	 * Genera el código de una instrucción WHILE
	 */
	private CodeInstructionList generateCodeOfWhileStatement(FunctionCodification mc, WhileStatement inst) 
	{
		Expression condition = inst.getCondition();
		Statement block = inst.getInstruction();

		CodeLabel lbBegin = mc.getNewLabel();
		CodeLabel lbTrue = mc.getNewLabel();
		CodeLabel lbFalse = mc.getNewLabel();

		CodeInstruction lbBeginInst = new CodeInstruction(LABEL,lbBegin,null,null);
		CodeInstruction lbTrueInst = new CodeInstruction(LABEL,lbTrue,null,null);
		CodeInstruction lbFalseInst = new CodeInstruction(LABEL,lbFalse,null,null);
		CodeInstruction jmpBegin = new CodeInstruction(JUMP,lbBegin,null,null);

		CodeInstructionList condinst = generateCodeForCondition(mc,condition,lbTrue,lbFalse);
		CodeInstructionList blockinst = generateCodeOfStatement(mc,block);

		CodeInstructionList codelist = new CodeInstructionList();
		codelist.addInstruction(lbBeginInst);
		codelist.addInstructionList(condinst.getList());
		codelist.addInstruction(lbTrueInst);
		codelist.addInstructionList(blockinst.getList());
		codelist.addInstruction(jmpBegin);
		codelist.addInstruction(lbFalseInst);

		return codelist;		
	}

	/**
	 * Genera el código de una instrucción RETURN
	 */
	private CodeInstructionList generateCodeOfReturnStatement(FunctionCodification mc, ReturnStatement inst) 
	{
		Expression exp = inst.getExpression();

		CodeInstructionList codelist = new CodeInstructionList();

		if(exp == null) 
		{
			CodeLiteral result = new CodeLiteral(0);
			codelist.addInstruction(new CodeInstruction(RETURN,result,null,null));
		} 
		else 
		{
			CodeVariable result = generateCodeForExpression(mc,exp,codelist);
			codelist.addInstruction(new CodeInstruction(RETURN,result,null,null));
		}

		return codelist;
	}

	/**
	 * Genera el código de una instrucción de llamada a una función
	 */
	private CodeInstructionList generateCodeOfCallStatement(FunctionCodification mc, CallStatement inst) 
	{
		CallExpression action = inst.getExpression();
		CodeInstructionList codelist = new CodeInstructionList();			
		generateCodeForExpression(mc,action,codelist);
		return codelist;
	}

	/**
	 * Genera el código de una instrucción de asignación
	 */
	private CodeInstructionList generateCodeOfAssignStatement(FunctionCodification mc, AssignStatement inst) 
	{
		Variable var = inst.getLeftHand();
		Expression exp = inst.getExpression();
		CodeVariable target = mc.getVariable(var);
		CodeInstructionList codelist = new CodeInstructionList();		
		CodeVariable result = generateCodeForExpression(mc,exp,codelist);
		CodeInstruction assign = new CodeInstruction(ASSIGN, target, result, null);			
		codelist.addInstruction(assign);
		return codelist;
	}

	/**
	 * Genera el código para calcular el resultado de una expresión
	 */
	private CodeVariable generateCodeForExpression(FunctionCodification mc, Expression exp, CodeInstructionList codelist) 
	{
		int type = exp.getType();

		if(exp instanceof IntegerLiteralExpression) 
		{
			return generateCodeForIntegerLiteralExpression(mc,(IntegerLiteralExpression) exp,codelist);
		} 
		else if(exp instanceof CharLiteralExpression) 
		{
			return generateCodeForCharLiteralExpression(mc,(CharLiteralExpression) exp,codelist);
		} 
		else if(exp instanceof BooleanLiteralExpression) 
		{
			return generateCodeForBooleanLiteralExpression(mc,(BooleanLiteralExpression) exp,codelist);
		} 
		else if(type == Type.BOOLEAN_TYPE && exp instanceof BinaryExpression) 
		{
			return generateCodeForBooleanExpression(mc,exp,codelist);
		} 
		else if(type == Type.BOOLEAN_TYPE && exp instanceof UnaryExpression) 
		{
			return generateCodeForBooleanExpression(mc,exp,codelist);
		} 
		else if(exp instanceof UnaryExpression) 
		{
			return generateCodeForUnaryExpression(mc, (UnaryExpression) exp, codelist);
		} 
		else if(exp instanceof BinaryExpression) 
		{
			return generateCodeForBinaryExpression(mc, (BinaryExpression) exp, codelist);
		} 
		else if(exp instanceof CallExpression) 
		{
			return generateCodeForCallExpression(mc,(CallExpression) exp, codelist);
		} 
		else if(exp instanceof VariableExpression) 
		{
			return generateCodeForVariableExpression(mc,(VariableExpression) exp, codelist);
		}
		return null;
	}

	/**
	 * Genera el código para analizar una condición
	 */
	private CodeInstructionList generateCodeForCondition(FunctionCodification mc, Expression cond, CodeLabel lbtrue, CodeLabel lbfalse) 
	{
		if(cond instanceof BooleanLiteralExpression) // LITERALES TRUE O FALSE
		{
			boolean val = ( (BooleanLiteralExpression) cond).getValue();
			CodeLabel lb = ( val? lbtrue : lbfalse);
			CodeInstructionList codelist = new CodeInstructionList();
			CodeInstruction jmp = new CodeInstruction(JUMP,lb,null,null);
			codelist.addInstruction(jmp);
			return codelist;
		} 
		else if(cond instanceof UnaryExpression) // NEGACIÓN
		{
			Expression exp = ((UnaryExpression) cond).getExpression(); 
			return generateCodeForCondition(mc,exp,lbfalse,lbtrue);
		} 
		else if(cond instanceof BinaryExpression)  // AND, OR Y COMPARACIONES
		{
			BinaryExpression exp = (BinaryExpression) cond;
			Expression left = exp.getLeftExpression();
			Expression right = exp.getRightExpression();
			int operator = exp.getOperator();
			if(operator == BinaryExpression.AND) 
			{
				CodeLabel lb = mc.getNewLabel();
				CodeInstruction lbInst = new CodeInstruction(LABEL,lb,null,null);
				CodeInstructionList leftcode = generateCodeForCondition(mc,left,lb,lbfalse);
				CodeInstructionList rightcode = generateCodeForCondition(mc,right,lbtrue,lbfalse);
				CodeInstructionList code = new CodeInstructionList();
				code.addInstructionList(leftcode.getList());
				code.addInstruction(lbInst);
				code.addInstructionList(rightcode.getList());
				return code;
			} 
			else if(operator == BinaryExpression.OR) 
			{
				CodeLabel lb = mc.getNewLabel();
				CodeInstruction lbInst = new CodeInstruction(LABEL,lb,null,null);
				CodeInstructionList leftcode = generateCodeForCondition(mc,left,lbtrue,lb);
				CodeInstructionList rightcode = generateCodeForCondition(mc,right,lbtrue,lbfalse);
				CodeInstructionList code = new CodeInstructionList();
				code.addInstructionList(leftcode.getList());
				code.addInstruction(lbInst);
				code.addInstructionList(rightcode.getList());
				return code;
			} 
			else 
			{
				int codekind = getBinaryCode(operator);
				CodeInstructionList leftcode = new CodeInstructionList();
				CodeInstructionList rightcode = new CodeInstructionList();
				CodeVariable source1 = generateCodeForExpression(mc,left,leftcode);
				CodeVariable source2 = generateCodeForExpression(mc,right,rightcode);

				CodeInstructionList code = new CodeInstructionList();
				code.addInstructionList(leftcode.getList());
				code.addInstructionList(rightcode.getList());
				code.addInstruction(new CodeInstruction(codekind,lbtrue,source1,source2));
				code.addInstruction(new CodeInstruction(JUMP,lbfalse,null,null));
				return code;
			}
		} 
		else // expresiones de tipo boolean (metodos o variables)
		{
			CodeInstructionList code = new CodeInstructionList();
			CodeVariable target = generateCodeForExpression(mc,cond,code);
			code.addInstruction(new CodeInstruction(JMP1,lbtrue,target,null));
			code.addInstruction(new CodeInstruction(JUMP,lbfalse,null,null));
			return code;
		}
	}

	/**
	 * Genera el código asociado a una constante entera.
	 */
	private CodeVariable generateCodeForIntegerLiteralExpression(FunctionCodification mc, IntegerLiteralExpression exp, CodeInstructionList codelist) 
	{
		CodeVariable temp = mc.getNewTemp();
		CodeLiteral literal = new CodeLiteral(exp.getValue());
		codelist.addInstruction(new CodeInstruction(ASSIGN,temp,literal,null));
		return temp;
	}


	/**
	 * Genera el código asociado a una constante char.
	 */
	private CodeVariable generateCodeForCharLiteralExpression(FunctionCodification mc, CharLiteralExpression exp, CodeInstructionList codelist) 
	{
		CodeVariable temp = mc.getNewTemp();
		CodeLiteral literal = new CodeLiteral(exp.getValue());
		codelist.addInstruction(new CodeInstruction(ASSIGN,temp,literal,null));
		return temp;
	}

	/**
	 * Genera el código asociado a una constante entera.
	 */
	private CodeVariable generateCodeForBooleanLiteralExpression(FunctionCodification mc, BooleanLiteralExpression exp, CodeInstructionList codelist) 
	{
		CodeVariable temp = mc.getNewTemp();
		int bvalue = (exp.getValue()? 1 : 0);
		CodeLiteral literal = new CodeLiteral(bvalue);
		codelist.addInstruction(new CodeInstruction(ASSIGN,temp,literal,null));
		return temp;
	}

	/**
	 * Genera el código asociado a una expresión booleana asignando el
	 * resultado a una variable
	 */
	private CodeVariable generateCodeForBooleanExpression(FunctionCodification mc, Expression exp, CodeInstructionList codelist) 
	{
		CodeLabel lbTrue = mc.getNewLabel();
		CodeLabel lbFalse = mc.getNewLabel();
		CodeLabel lbNext = mc.getNewLabel();

		CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
		CodeInstruction lbFalseInst = new CodeInstruction(LABEL, lbFalse, null, null);
		CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);

		CodeVariable target = mc.getNewTemp();
		CodeLiteral valueTrue = new CodeLiteral(1);
		CodeLiteral valueFalse = new CodeLiteral(0);

		CodeInstructionList code = generateCodeForCondition(mc,exp,lbTrue,lbFalse);
		code.addInstruction(lbTrueInst);
		code.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
		code.addInstruction(new CodeInstruction(JUMP,lbNext, null, null));
		code.addInstruction(lbFalseInst);
		code.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
		code.addInstruction(lbNextInst);

		codelist.addInstructionList(code.getList());
		return target;
	}

	/**
	 * Genera el código de una operación aritmética unaria
	 */
	private CodeVariable generateCodeForUnaryExpression(FunctionCodification mc, UnaryExpression exp, CodeInstructionList codelist) 
	{
		Expression operand = exp.getExpression();

		CodeInstructionList code = new CodeInstructionList();
		CodeVariable source = generateCodeForExpression(mc,operand,code);
		CodeVariable target = mc.getNewTemp();

		code.addInstruction(new CodeInstruction(INV,target,source,null));
		codelist.addInstructionList(code.getList());

		return target;		
	}

	/**
	 * Genera el código de una expresión aritmética binaria
	 */
	private CodeVariable generateCodeForBinaryExpression(FunctionCodification mc, BinaryExpression exp, CodeInstructionList codelist) 
	{
		int operator = exp.getOperator();
		Expression left = exp.getLeftExpression();
		Expression right = exp.getRightExpression();

		CodeVariable target = mc.getNewTemp();

		CodeInstructionList code = new CodeInstructionList();
		CodeVariable source1 = generateCodeForExpression(mc,left,code);
		CodeVariable source2 = generateCodeForExpression(mc,right,code);

		int op = getBinaryCode(operator);
		code.addInstruction(new CodeInstruction(op,target,source1,source2));
		codelist.addInstructionList(code.getList());
		return target;		
	}

	/**
	 * Genera el código de una expresión de llamada a una función
	 */
	private CodeVariable generateCodeForCallExpression(FunctionCodification mc, CallExpression exp, CodeInstructionList codelist) 
	{
		Function function = exp.getFunction();
		CallParameters call = exp.getCallParameters();
		Expression[] paramexp = call.getParameters();

		int argsize = 4*paramexp.length; // Espacio para los argumentos

		CodeInstructionList code = new CodeInstructionList();
		code.addInstruction(new CodeInstruction(PRECALL, new CodeLiteral(argsize), null, null));

		CodeVariable[] param = new CodeVariable[paramexp.length];
		for(int i=0; i<param.length; i++) 
		{
			param[i] = generateCodeForExpression(mc,paramexp[i],code);
			CodeLiteral pos = new CodeLiteral(4*i);
			code.addInstruction(new CodeInstruction(PARAM,param[i],pos,null));
		}

		CodeVariable target = mc.getNewTemp();
		CodeLabel functionlabel = new CodeLabel(function.getLabel());
		code.addInstruction(new CodeInstruction(CALL,target,functionlabel,null));

		codelist.addInstructionList(code.getList());
		return target;
	}

	/**
	 * Genera el código de una expresión de referencia a una variable local.
	 * Esto no genera código. Tan solo devuelve la referencia a la variable.
	 */
	private CodeVariable generateCodeForVariableExpression(FunctionCodification mc, VariableExpression exp, CodeInstructionList codelist) 
	{
		Variable var = exp.getVariable();
		return mc.getVariable(var);
	}

	/**
	 * Obtiene el código de una instrucción a partir del código
	 * de una expresión binaria.
	 */
	private int getBinaryCode(int op) 
	{
		switch(op) 
		{
		case BinaryExpression.EQ: return JMPEQ;
		case BinaryExpression.NEQ: return JMPNE;
		case BinaryExpression.GE: return JMPGE;
		case BinaryExpression.GT: return JMPGT;
		case BinaryExpression.LE: return JMPLE;
		case BinaryExpression.LT: return JMPLT;
		case BinaryExpression.PLUS: return ADD;
		case BinaryExpression.MINUS: return SUB;
		case BinaryExpression.PROD: return MUL;
		case BinaryExpression.DIV: return DIV;
		case BinaryExpression.MOD: return MOD;
		default: return 0;
		}		
	}

}
