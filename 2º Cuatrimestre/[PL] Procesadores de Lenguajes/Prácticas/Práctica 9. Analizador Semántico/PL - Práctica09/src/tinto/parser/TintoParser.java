//------------------------------------------------------------------//
//                        COPYRIGHT NOTICE                          //
//------------------------------------------------------------------//
// Copyright (c) 2017, Francisco Jos� Moreno Velo                   //
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
//           Departamento de Tecnolog�as de la Informaci�n          //
//   �rea de Ciencias de la Computaci�n e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//

package tinto.parser;


import java.io.FileInputStream;
import tinto.ast.*;
import tinto.ast.expression.*;
import tinto.ast.statement.*;
import tinto.ast.struct.*;

/**
 * Analizador sint�ctico de Tinto basado en una gram�tica BNF y LL(1)
 *  
 * @author Francisco Jos� Moreno Velo
 *
 */
public class TintoParser implements TokenConstants {

	//----------------------------------------------------------------//
	//           Miembros privados dedicados al an�lisis              //
	//----------------------------------------------------------------//

	/**
	 * Analizador l�xico
	 */
	private TintoLexer lexer;

	/**
	 * �ltimo token analizado
	 */
	private Token prevToken;

	/**
	 * Siguiente token de la cadena de entrada
	 */
	private Token nextToken;

	//----------------------------------------------------------------//
	//      Miembros privados dedicados al tratamiento de errores     //
	//----------------------------------------------------------------//

	/**
	 * Contador de errores
	 */
	private int errorCount;

	/**
	 * Mensaje de errores
	 */
	private String errorMsg;

	//----------------------------------------------------------------//
	//              M�todos relacionados con el an�lisis              //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 */
	public TintoParser(FileInputStream fis) 
	{
		this.lexer = new TintoLexer(fis);
		this.prevToken = null;
		this.nextToken = lexer.getNextToken();
	}

	/**
	 * Analiza un fichero ".tinto" a�adiendo la informaci�n del cuerpo de los m�todos
	 */
	public void parse(String name, SymbolTable symtab)
	{
		this.errorCount = 0;
		this.errorMsg = "";
		symtab.setActiveLibrary(name);
		tryCompilationUnit(symtab);
	}

	//----------------------------------------------------------------//
	//       M�todos relacionados con el tratamiento de errores       //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el n�mero de errores del an�lisis
	 * @return
	 */
	public int getErrorCount()
	{
		return this.errorCount;
	}

	/**
	 * Obtiene el mensaje de error del an�lisis
	 * @return
	 */
	public String getErrorMsg()
	{
		return this.errorMsg;
	}

	/**
	 * Almacena un error de an�lisis
	 * @param ex
	 */
	private void catchError(Exception ex)
	{
		this.errorCount++;
		this.errorMsg += ex.toString();
	}

	/**
	 * Sincroniza la cadena de tokens
	 * @param left
	 * @param right
	 */
	private void skipTo(int[] left, int[] right) {
		boolean flag = false;
		if(prevToken.getKind() == EOF || nextToken.getKind() == EOF) flag = true;
		for(int i=0; i<left.length; i++) if(prevToken.getKind() == left[i]) flag = true;
		for(int i=0; i<right.length; i++) if(nextToken.getKind() == right[i]) flag = true;

		while(!flag) 
		{
			prevToken = nextToken;
			nextToken = lexer.getNextToken();
			if(prevToken.getKind() == EOF || nextToken.getKind() == EOF) flag = true;
			for(int i=0; i<left.length; i++) if(prevToken.getKind() == left[i]) flag = true;
			for(int i=0; i<right.length; i++) if(nextToken.getKind() == right[i]) flag = true;
		}
	}

	//----------------------------------------------------------------//
	//                M�todos de verificaci�n sem�ntica               //
	//----------------------------------------------------------------//

	/**
	 * Verifica que la sentencia a a�adir a un bloque es alcanzable
	 */
	private void verifyUnreachableCode(Statement stm, BlockStatement block)
	{
		if(stm != null && block.returns() )
		{
			int errorcode = SemanticException.UNREACHABLE_CODE;
			catchError(new SemanticException(errorcode,prevToken));
		}
	}

	/**
	 * Verifica que el c�digo de una funci�n alcanza siempre un return
	 */
	private void verifyUnfinishedFunction(Token tk, BlockStatement body, SymbolTable symtab)
	{
		int type = symtab.getActiveFunction().getType();
		if(!TypeSystem.isVoid(type) && !body.returns() )
		{
			int errorcode = SemanticException.UNFINISHED_FUNCTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que una variable no est� duplicada
	 */
	private void verifyDuplicatedVariable(Token tk, SymbolTable symtab)
	{
		if(symtab.getVariableInScope(tk.getLexeme())!=null)
		{
			int errorcode = SemanticException.DUPLICATED_VARIABLE_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que una condici�n sea de tipo booleana.
	 */
	private void verifyConditionType(Token tk, Expression expr)
	{
		if(!TypeSystem.isBoolean( expr.getType() ))
		{
			int errorcode = SemanticException.INVALID_CONDITION_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que una instrucci�n return devuelve un tipo de dato correcto
	 */
	private void verifyReturnType(Token tk, Expression expr, SymbolTable symtab)
	{
		int type = symtab.getActiveFunction().getType();
		if(expr == null && !TypeSystem.isVoid(type))
		{
			int errorcode = SemanticException.INVALID_RETURN_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
		if(expr != null && !TypeSystem.isAssignable(type,expr.getType()))
		{
			int errorcode = SemanticException.INVALID_RETURN_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica la existencia una variable de una instrucci�n de asignaci�n
	 */
	private void verifyUnknownVariable(Token tk, SymbolTable symtab)
	{
		if(symtab.getVariable(tk.getLexeme()) == null)
		{
			int errorcode = SemanticException.UNKNOWN_VARIABLE_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/** 
	 * Verifica que los tipos de datos en una instrucci�n de asignaci�n son correctos
	 */
	private void verifyAssignTypes(Token tk, Variable var, Expression expr)
	{
		if(!TypeSystem.isAssignable(var.getType(),expr.getType()))
		{
			int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}		
	}

	/**
	 * Verifica la existencia de una biblioteca en la tabla de s�mbolos
	 */
	private void verifyUnknownLibrary(Token tk, SymbolTable symtab)
	{
		if(symtab.getLibrary(tk.getLexeme()) == null)
		{
			int errorcode = SemanticException.UNKNOWN_LIBRARY_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica la existencia de una funci�n en la biblioteca activa
	 */
	private void verifyUnknownFunction(Token tk, CallParameters param, SymbolTable symtab)
	{
		if(symtab.getActiveLibrary().getAnyFunction(tk.getLexeme(),param.getTypes()) == null)
		{
			int errorcode = SemanticException.UNKNOWN_FUNCTION_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica la existencia de una funci�n p�blica en una cierta biblioteca
	 */
	private void verifyUnknownFunction(Token tk, CallParameters param, LibraryDeclaration library)
	{
		if(library.getPublicFunction(tk.getLexeme(),param.getTypes()) == null)
		{
			int errorcode = SemanticException.UNKNOWN_FUNCTION_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que los dos operandos de una expresi�n l�gica (AND, OR) sean booleanos
	 */
	private void verifyBooleanTypes(Token tk, Expression exp1, Expression exp2)
	{
		if(!TypeSystem.isBoolean(exp1.getType()) || !TypeSystem.isBoolean(exp2.getType()))
		{
			int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que los tipos de los dos operandos de una relaci�n sean correctos
	 */
	private void verifyRelationTypes(Token tk, int relop, Expression exp1, Expression exp2)
	{
		switch(relop)
		{
		case BinaryExpression.EQ:
		case BinaryExpression.NEQ:
			if(!TypeSystem.isComparable(exp1.getType(),exp2.getType()))
			{
				int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
				catchError(new SemanticException(errorcode,tk));
			}
			break;
		case BinaryExpression.GT:
		case BinaryExpression.GE:
		case BinaryExpression.LT:
		case BinaryExpression.LE:
			if(!TypeSystem.isOrderable(exp1.getType(),exp2.getType()))
			{
				int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
				catchError(new SemanticException(errorcode,tk));
			}
			break;
		}
	}

	/**
	 * Verifica que una expresi�n sea de tipo booleana (para poder apilcar NOT).
	 */
	private void verifyBooleanType(Token tk, Expression expr)
	{
		if(!TypeSystem.isBoolean( expr.getType() ))
		{
			int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que dos expresiones sean de tipo num�rico (para poder aplicar '+' y '-').
	 */
	private void verifyNumericType(Token tk, Expression exp)
	{
		if(!TypeSystem.isNumeric(exp.getType()))
		{
			int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que dos expresiones sean de tipo num�rico (para poder aplicar '+','-','*'y'/').
	 */
	private void verifyNumericTypes(Token tk, Expression exp1, Expression exp2)
	{
		if(!TypeSystem.isNumeric( exp1.getType() ) || !TypeSystem.isNumeric( exp2.getType() ))
		{
			int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que dos expresiones sean de tipo entero (para poder aplicar '%').
	 */
	private void verifyIntegerTypes(Token tk, Expression exp1, Expression exp2)
	{
		if(!TypeSystem.isInteger( exp1.getType() ) || !TypeSystem.isInteger( exp2.getType() ))
		{
			int errorcode = SemanticException.TYPE_MISMATCH_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que el valor de un literal entero sea correcto
	 */
	private void verifyIntegerValue(Token tk)
	{
		try
		{
			String lexeme = tk.getLexeme();
			if(lexeme.startsWith("0x") || lexeme.startsWith("0X")) Integer.parseInt(lexeme.substring(2),16);
			if(lexeme.startsWith("0b") || lexeme.startsWith("0B")) Integer.parseInt(lexeme.substring(2),2);
			else if(lexeme.startsWith("0")) Integer.parseInt(lexeme,8);
			else Integer.parseInt(lexeme);
		}
		catch(Exception e)
		{
			int errorcode = SemanticException.NUMBER_FORMAT_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	//----------------------------------------------------------------//
	//       M�todos relacionados con las aciones sem�nticas          //
	//----------------------------------------------------------------//

	/**
	 * Acci�n se�ntica asociada a a�adir un tipo de argumento a una
	 * lista de tipos
	 */
	private int[] actionAddArgumentType(int type, int[]args)
	{
		int[] nl = new int[args.length+1];
		System.arraycopy(args,0,nl,0,args.length);
		nl[args.length] = type;
		return nl;
	}

	/**
	 * Acci�n sem�ntica asociada a a�adir una instrucci�n a un bloque de
	 * instrucciones.
	 */
	private void actionAddStatement(BlockStatement block, Statement stm)
	{
		if(stm == null) return;
		verifyUnreachableCode(stm,block);
		block.addStatement(stm);
	}

	/**
	 * Acci�n sem�ntica asociada a la asignaci�n del cuerpo de una funci�n
	 */
	private void actionSetFunctionBody(Token tk, BlockStatement block, SymbolTable symtab)
	{
		verifyUnfinishedFunction(tk,block,symtab);
		symtab.getActiveFunction().setBody(block);
	}

	/**
	 * Acci�n sem�ntica asociada al reconocimiento de una declaraci�n de variable
	 */
	private Variable actionAddDeclaration(SymbolTable symtab,int type,Token tid)
	{
		verifyDuplicatedVariable(tid, symtab);
		Variable var = new Variable(type, tid.getLexeme());
		symtab.addLocalVariable(var);
		return var;
	}

	/**
	 * Acci�n sem�ntica asociada al reconocimiento de una asignaci�n
	 */
	private void actionAddAssignement(BlockStatement block, Variable var, Expression exp)
	{
		if(exp != null)
		{
			AssignStatement stm = new AssignStatement(var,exp);
			block.addStatement(stm);
		}									
	}

	/**
	 * Acci�n sem�ntica que obtiene la instrucci�n asociada a una declaraci�n
	 * de variables. Si no hay inicializaciones devuelve null. Si s�lo hay una
	 * devuelve esa asignaci�n. Si hay m�s de una devuelve el bloque de
	 * asignaciones.
	 */
	private Statement actionGetStatementFromBlock(BlockStatement block)
	{
		Statement[] list = block.getStatementList();
		if(list.length == 0) return null;
		if(list.length == 1) return list[0];
		return block;
	}

	/**
	 * Acci�n sem�ntica asociada a la creaci�n de una instrucci�n if
	 */
	private Statement actionIfStatement(Token tk, Expression cond, Statement thenStm, Statement elseStm)
	{
		verifyConditionType(tk,cond);
		return new IfStatement(cond,thenStm,elseStm);
	}

	/**
	 * Acci�n sem�ntica asociada a la creaci�n de una instrucci�n while
	 */
	private Statement actionWhileStatement(Token tk, Expression cond, Statement body)
	{
		verifyConditionType(tk,cond);
		return new WhileStatement(cond,body);
	}

	/**
	 * Acci�n asociada a la creaci�n de una instrucci�n return
	 */
	private Statement actionReturnStatement(Token tk, Expression exp, SymbolTable symtab)
	{
		verifyReturnType(tk,exp,symtab);
		return new ReturnStatement(exp);
	}

	/**
	 * Acci�n sem�ntica asociada al reconocimiento de una instrucci�n de asignaci�n
	 */
	private Statement actionAssignStatement(Token tk, Expression exp, SymbolTable symtab)
	{
		verifyUnknownVariable(tk,symtab);
		Variable var = symtab.getVariable(tk.getLexeme());
		verifyAssignTypes(tk,var,exp);
		return new AssignStatement(var,exp);
	}

	/**
	 * Acci�n sem�ntica asociada al reconocimiento de una llamada a un m�todo
	 * de la misma biblioteca
	 */
	private Statement actionCallStatement(Token tk, CallParameters param, SymbolTable symtab)
	{
		verifyUnknownFunction(tk,param,symtab);		
		Function called = symtab.getActiveLibrary().getAnyFunction(tk.getLexeme(),param.getTypes());
		CallExpression exp = new CallExpression(called,param,symtab.getActiveLibrary());
		return new CallStatement(exp);
	}

	/**
	 * Acci�n sem�ntica asociada al reconocimiento de una llamada a un m�todo
	 * de una biblioteca importada
	 */
	private Statement actionCallStatement(Token tid1, Token tid2, CallParameters param, SymbolTable symtab)
	{
		verifyUnknownLibrary(tid1,symtab); 
		LibraryDeclaration lib = symtab.getLibrary(tid1.getLexeme());
		verifyUnknownFunction(tid2,param,lib);
		Function called = lib.getPublicFunction(tid2.getLexeme(),param.getTypes());
		CallExpression exp = new CallExpression(called,param,lib);
		return new CallStatement(exp);
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n binaria que define un OR entre
	 * dos expresiones
	 */
	private Expression actionOrExpression(Token tk,Expression exp1, Expression exp2)
	{
		if(exp1 == null || exp2 == null) return null;
		verifyBooleanTypes(tk,exp1,exp2);
		int op = BinaryExpression.OR;
		Expression exp = new BinaryExpression(op, exp1, exp2);
		return exp; 
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n binaria que define un AND entre
	 * dos expresiones
	 */
	private Expression actionAndExpression(Token tk,Expression exp1, Expression exp2)
	{
		if(exp1 == null || exp2 == null) return null;
		verifyBooleanTypes(tk,exp1,exp2);
		int op = BinaryExpression.AND;
		Expression exp = new BinaryExpression(op, exp1, exp2);
		return exp; 
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n binaria que define una relaci�n entre
	 * dos expresiones.
	 */
	private Expression actionRelExpression(Token tk, int op, Expression exp1, Expression exp2)
	{
		if(exp1 == null || exp2 == null) return null;
		verifyRelationTypes(tk,op,exp1,exp2);
		Expression exp = new BinaryExpression(op, exp1, exp2);
		return exp; 
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n unaria sobre otra expresi�n.
	 */
	private Expression actionUnaryExpression(Token tk, int op, Expression exp)
	{
		switch(op)
		{
		case UnaryExpression.NONE: 
			return exp;
		case UnaryExpression.NOT: 
			verifyBooleanType(tk,exp); 
			return new UnaryExpression(op,exp);
		case UnaryExpression.MINUS:
			verifyNumericType(tk,exp); 
			return new UnaryExpression(op,exp);
		case UnaryExpression.PLUS:
			verifyNumericType(tk,exp); 
			return exp;
		}
		return exp; 
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n binaria en forma de suma o resta
	 */
	private Expression actionSumExpression(Token tk,int op, Expression exp1, Expression exp2)
	{
		if(exp1 == null || exp2 == null) return null;
		verifyNumericTypes(tk,exp1,exp2);
		Expression exp = new BinaryExpression(op, exp1, exp2);
		return exp; 
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n binaria en forma de suma o resta
	 */
	private Expression actionProdExpression(Token tk,int op, Expression exp1, Expression exp2)
	{
		if(exp1 == null || exp2 == null) return null;
		if(op == BinaryExpression.MOD) verifyIntegerTypes(tk,exp1,exp2);
		else verifyNumericTypes(tk,exp1,exp2);
		Expression exp = new BinaryExpression(op, exp1, exp2);
		return exp; 
	}

	/**
	 * Acci�n sem�ntica que crea un literal de tipo entero
	 */
	private LiteralExpression actionIntegerLiteral(Token tk)
	{
		verifyIntegerValue(tk);
		return new IntegerLiteralExpression(tk.getLexeme()); 
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n de referencia a una
	 * variable o a una funci�n
	 */
	private Expression actionReferenceExpression(Token tid1,Token tid2,CallParameters param, SymbolTable symtab)
	{
		if(param == null) return actionVariableExpression(tid1, symtab);
		else if(tid2 == null) return actionCallExpression(tid1,param,symtab);
		else return actionCallExpression(tid1,tid2,param,symtab);
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n de referencia a una variable
	 */
	private Expression actionVariableExpression(Token tid, SymbolTable symtab)
	{
		verifyUnknownVariable(tid,symtab);
		Variable var = symtab.getVariable(tid.getLexeme());
		return new VariableExpression(var);
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n de llamada a una funci�n de la
	 * misma biblioteca
	 */
	private Expression actionCallExpression(Token tk, CallParameters param, SymbolTable symtab)
	{
		verifyUnknownFunction(tk,param,symtab);		
		Function called = symtab.getActiveLibrary().getAnyFunction(tk.getLexeme(),param.getTypes());
		return new CallExpression(called,param,symtab.getActiveLibrary());
	}

	/**
	 * Acci�n sem�ntica que crea una expresi�n de llamada a una funci�n de una
	 * biblioteca importada
	 */
	private Expression actionCallExpression(Token tid1, Token tid2, CallParameters param, SymbolTable symtab)
	{
		verifyUnknownLibrary(tid1,symtab); 
		LibraryDeclaration lib = symtab.getLibrary(tid1.getLexeme());
		verifyUnknownFunction(tid2,param,lib);
		Function called = lib.getPublicFunction(tid2.getLexeme(),param.getTypes());
		return new CallExpression(called,param,lib);
	}	

	//----------------------------------------------------------------//
	//     M�todos relacionados con el an�lisis de la gram�tica       //
	//----------------------------------------------------------------//

	/**
	 * M�todo que consume un token de la cadena de entrada
	 * @param kind Tipo de token a consumir
	 * @throws SintaxException Si el tipo no coincide con el token 
	 */
	private Token match(int kind) throws SintaxException 
	{
		if(nextToken.getKind() == kind) 
		{
			prevToken = nextToken;
			nextToken = lexer.getNextToken();
			return prevToken;
		}
		else throw new SintaxException(nextToken,kind);
	}

	/**
	 * Analiza el s�mbolo <CompilationUnit>
	 */
	private void tryCompilationUnit(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { EOF };
		try
		{
			parseCompilationUnit(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}

	/**
	 * Analiza el s�mbolo <CompilationUnit>
	 * @throws SintaxException
	 */
	private void parseCompilationUnit(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { IMPORT, LIBRARY };
		switch(nextToken.getKind()) 
		{
		case IMPORT:
		case LIBRARY:
			tryImportClauseList();
			tryLibraryDecl(symtab);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ImportClauseList>
	 */
	private void tryImportClauseList() 
	{
		int[] lsync = { };
		int[] rsync = { LIBRARY };

		try
		{
			parseImportClauseList();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}

	/**
	 * Analiza el s�mbolo <ImportClauseList>
	 * @throws SintaxException
	 */
	private void parseImportClauseList() throws SintaxException 
	{
		int[] expected = { IMPORT, LIBRARY };
		switch(nextToken.getKind()) 
		{
		case IMPORT:
			tryImportClause();
			tryImportClauseList();
			break;
		case LIBRARY:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ImportClause>
	 */
	private void tryImportClause() 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { IMPORT, LIBRARY };

		try
		{
			parseImportClause();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}

	/**
	 * Analiza el s�mbolo <ImportClause>
	 * @throws SintaxException
	 */
	private void parseImportClause() throws SintaxException 
	{
		int[] expected = { IMPORT };
		switch(nextToken.getKind()) 
		{
		case IMPORT:
			match(IMPORT);
			match(IDENTIFIER);
			match(SEMICOLON);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <LibraryDecl>
	 */
	private void tryLibraryDecl(SymbolTable symtab)  
	{
		int[] lsync = { };
		int[] rsync = { };

		try
		{
			parseLibraryDecl(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}

	/**
	 * Analiza el s�mbolo <LibraryDecl>
	 * @throws SintaxException
	 */
	private void parseLibraryDecl(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { LIBRARY };
		switch(nextToken.getKind()) 
		{
		case LIBRARY:
			match(LIBRARY);
			match(IDENTIFIER);
			match(LBRACE);
			tryFunctionList(symtab);
			match(RBRACE);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <FunctionList>
	 */
	private void tryFunctionList(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { RBRACE }; 

		try
		{
			parseFunctionList(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		} 
	}

	/**
	 * Analiza el s�mbolo <FunctionList>
	 * @throws SintaxException
	 */
	private void parseFunctionList(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { PUBLIC, PRIVATE };
		switch(nextToken.getKind()) 
		{
		case PUBLIC:
		case PRIVATE:
			tryFunctionDecl(symtab);
			tryFunctionList(symtab);
			break;
		case RBRACE:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <FunctionDecl>
	 */
	private void tryFunctionDecl(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { PUBLIC, PRIVATE }; 
		try
		{
			parseFunctionDecl(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}  
	}

	/**
	 * Analiza el s�mbolo <FunctionDecl>
	 * @throws SintaxException
	 */
	private void parseFunctionDecl(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { PUBLIC, PRIVATE };
		switch(nextToken.getKind()) 
		{
		case PUBLIC:
		case PRIVATE:
			tryAccess();
			tryFunctionType();
			Token tid = match(IDENTIFIER);
			int[] args = tryArgumentDecl();
			symtab.setActiveFunction(tid.getLexeme(), args);
			tryFunctionBody(symtab);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <Access>
	 */
	private void tryAccess() 
	{
		int[] lsync = { };
		int[] rsync = { VOID, INT, CHAR, BOOLEAN };

		try
		{
			parseAccess();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}

	/**
	 * Analiza el s�mbolo <Access>
	 * @throws SintaxException
	 */
	private void parseAccess() throws SintaxException 
	{
		int[] expected = { PUBLIC, PRIVATE };
		switch(nextToken.getKind()) 
		{
		case PUBLIC:
			match(PUBLIC);
			break;
		case PRIVATE:
			match(PRIVATE);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <FunctionType>
	 */
	private void tryFunctionType() 
	{
		int[] lsync = { };
		int[] rsync = { IDENTIFIER };

		try
		{
			parseFunctionType();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}

	/**
	 * Analiza el s�mbolo <FunctionType>
	 * @throws SintaxException
	 */
	private void parseFunctionType() throws SintaxException 
	{
		int[] expected = { VOID, INT, CHAR, BOOLEAN };
		switch(nextToken.getKind()) 
		{
		case VOID:
			match(VOID);
			break;
		case INT:
		case CHAR:
		case BOOLEAN:
			tryType();
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <Type>
	 */
	private int tryType() 
	{
		int[] lsync = { };
		int[] rsync = { IDENTIFIER };
		int type = Type.MISMATCH_TYPE;

		try
		{
			type = parseType();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}

		return type;
	}

	/**
	 * Analiza el s�mbolo <Type>
	 * @throws SintaxException
	 */
	private int parseType() throws SintaxException 
	{
		int type;
		int[] expected = { INT, CHAR, BOOLEAN };
		switch(nextToken.getKind()) 
		{
		case INT:
			match(INT);
			type = Type.INT_TYPE;
			break;
		case CHAR:
			match(CHAR);
			type = Type.CHAR_TYPE;
			break;
		case BOOLEAN:
			match(BOOLEAN);
			type = Type.BOOLEAN_TYPE;
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return type;
	}

	/**
	 * Analiza el s�mbolo <ArgumentDecl>
	 */
	private int[] tryArgumentDecl() 
	{
		int[] lsync = { RPAREN };
		int[] rsync = { LBRACE };
		int[] args = null;

		try
		{
			args = parseArgumentDecl();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}

		return args;
	}

	/**
	 * Analiza el s�mbolo <ArgumentDecl>
	 * @throws SintaxException
	 */
	private int[] parseArgumentDecl() throws SintaxException 
	{
		int[] args = new int[0];
		int[] expected = { LPAREN };
		switch(nextToken.getKind()) 
		{
		case LPAREN:
			match(LPAREN);
			args = tryArgumentList(args);
			match(RPAREN);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return args;
	}

	/**
	 * Analiza el s�mbolo <ArgumentList>
	 */
	private int[] tryArgumentList(int[] args) 
	{
		int[] lsync = { };
		int[] rsync = { RPAREN };

		try
		{
			args = parseArgumentList(args);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return args;
	}

	/**
	 * Analiza el s�mbolo <ArgumentList>
	 * @throws SintaxException
	 */
	private int[] parseArgumentList(int[] args) throws SintaxException 
	{
		int[] expected = { INT, CHAR, BOOLEAN, RPAREN };
		switch(nextToken.getKind()) 
		{
		case INT:
		case CHAR:
		case BOOLEAN:
			args = tryArgument(args);
			args = tryMoreArguments(args);
			break;
		case RPAREN:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return args;
	}

	/**
	 * Analiza el s�mbolo <Argument>
	 */
	private int[] tryArgument(int[] args) 
	{
		int[] lsync = { };
		int[] rsync = { COMMA, RPAREN };

		try
		{
			args = parseArgument(args);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return args;
	}

	/**
	 * Analiza el s�mbolo <Argument>
	 * @throws SintaxException
	 */
	private int[] parseArgument(int[] args) throws SintaxException 
	{
		int type;
		int[] expected = { INT, CHAR, BOOLEAN };
		switch(nextToken.getKind()) 
		{
		case INT:
		case CHAR:
		case BOOLEAN:
			type = tryType();
			match(IDENTIFIER);
			return actionAddArgumentType(type,args);
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <MoreArguments>
	 */
	private int[] tryMoreArguments(int[] args) 
	{
		int[] lsync = { };
		int[] rsync = { RPAREN };

		try
		{
			args = parseMoreArguments(args);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return args;
	}

	/**
	 * Analiza el s�mbolo <MoreArguments>
	 * @throws SintaxException
	 */
	private int[] parseMoreArguments(int[] args) throws SintaxException 
	{
		int[] expected = { COMMA, RPAREN };
		switch(nextToken.getKind()) 
		{
		case COMMA:
			match(COMMA);
			args = tryArgument(args);
			args = tryMoreArguments(args);
			break;
		case RPAREN:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return args;
	}

	/**
	 * Analiza el s�mbolo <FunctionBody>
	 */
	private void tryFunctionBody(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { PUBLIC, PRIVATE }; 
		try
		{
			parseFunctionBody(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}  
	}

	/**
	 * Analiza el s�mbolo <FunctionBody>
	 * @throws SintaxException
	 */
	private void parseFunctionBody(SymbolTable symtab) throws SintaxException 
	{
		BlockStatement block = new BlockStatement();
		Token tk;

		int[] expected = { LBRACE };
		switch(nextToken.getKind()) 
		{
		case LBRACE:
			match(LBRACE);
			tryStatementList(block, symtab);
			tk = match(RBRACE);
			actionSetFunctionBody(tk, block, symtab);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**************************************************************/
	/*       EL AN�LISIS DE LAS INSTRUCCIONES COMIENZA AQU�       */
	/**************************************************************/

	/**
	 * Analiza el s�mbolo <StatementList>
	 */
	private void tryStatementList(BlockStatement block, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { RBRACE }; 
		try
		{
			parseStatementList(block,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}  
	}

	/**
	 * Analiza el s�mbolo <StatementList>
	 * @throws SintaxException
	 */
	private void parseStatementList(BlockStatement block, SymbolTable symtab) throws SintaxException 
	{
		Statement stm;
		int[] expected = { INT, CHAR, BOOLEAN, IDENTIFIER, IF, WHILE, 
				RETURN, SEMICOLON, LBRACE, RBRACE };
		switch(nextToken.getKind()) 
		{
		case INT:
		case CHAR:
		case BOOLEAN:
		case IDENTIFIER:
		case IF:
		case WHILE:
		case RETURN:
		case SEMICOLON:
		case LBRACE:
			stm = tryStatement(symtab);
			actionAddStatement(block,stm);
			tryStatementList(block,symtab);
			break;
		case RBRACE:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 *  Analiza el s�mbolo <Statement>
	 * @param symtab
	 * @return
	 */
	private Statement tryStatement(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseStatement(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <Statement>
	 * @throws SintaxException
	 */
	private Statement parseStatement(SymbolTable symtab) throws SintaxException 
	{
		Statement stm;
		int[] expected = { INT, CHAR, BOOLEAN, IDENTIFIER, IF, WHILE, 
				RETURN, SEMICOLON, LBRACE };
		switch(nextToken.getKind()) 
		{
		case INT:
		case CHAR:
		case BOOLEAN:
			stm = tryDecl(symtab);
			break;
		case IDENTIFIER:
			stm = tryIdStm(symtab);
			break;
		case IF:
			stm = tryIfStm(symtab);
			break;
		case WHILE:
			stm = tryWhileStm(symtab);
			break;
		case RETURN:
			stm = tryReturnStm(symtab);
			break;
		case SEMICOLON:
			stm = tryNoStm(symtab);
			break;
		case LBRACE:
			stm = tryBlockStm(symtab);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <Decl>
	 */
	private Statement tryDecl(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseDecl(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <Decl>
	 * @throws SintaxException
	 */
	private Statement parseDecl(SymbolTable symtab) throws SintaxException 
	{
		BlockStatement block = new BlockStatement();
		int[] expected = { INT, CHAR, BOOLEAN };
		switch(nextToken.getKind()) 
		{
		case INT:
		case CHAR:
		case BOOLEAN:
			int type = tryType();
			Token tid = match(IDENTIFIER);
			Expression exp = tryAssignement(symtab);
			Variable var = actionAddDeclaration(symtab, type, tid);
			actionAddAssignement(block,var,exp);
			tryMoreDecl(block,type,symtab);
			match(SEMICOLON);
			break;

		default:
			throw new SintaxException(nextToken,expected);
		}
		return actionGetStatementFromBlock(block);
	}

	/**
	 * Analiza el s�mbolo <MoreDecl>
	 */
	private void tryMoreDecl(BlockStatement block, int type, SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};

		try
		{
			parseMoreDecl(block,type,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}

	/**
	 * Analiza el s�mbolo <MoreDecl>
	 * @throws SintaxException
	 */
	private void parseMoreDecl(BlockStatement block, int type, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { COMMA, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case COMMA:
			match(COMMA);
			Token tid = match(IDENTIFIER);
			Expression exp = tryAssignement(symtab);
			Variable var = actionAddDeclaration(symtab, type, tid);
			actionAddAssignement(block,var,exp);
			tryMoreDecl(block,type,symtab);
			break;
		case SEMICOLON:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <Assignement>
	 */
	private Expression tryAssignement(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { COMMA, SEMICOLON };
		Expression exp = null;

		try
		{
			exp = parseAssignement(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}

	/**
	 * Analiza el s�mbolo <Assignement>
	 * @throws SintaxException
	 */
	private Expression parseAssignement(SymbolTable symtab) throws SintaxException 
	{
		Expression exp = null;
		int[] expected = { ASSIGN, COMMA, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case ASSIGN:
			match(ASSIGN);
			exp = tryExpr(symtab);
			break;
		case COMMA:
		case SEMICOLON:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return exp;
	}

	/**
	 * Analiza el s�mbolo <IfStm>
	 * @param symtab
	 * @return
	 */
	private Statement tryIfStm(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseIfStm(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <IfStm>
	 * @throws SintaxException
	 */
	private Statement parseIfStm(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { IF };
		switch(nextToken.getKind()) 
		{
		case IF:
			match(IF);
			Token tk = match(LPAREN);
			Expression cond = tryExpr(symtab);
			match(RPAREN);
			Statement thenStm = tryStatement(symtab);
			Statement elseStm = tryElseStm(symtab);
			return actionIfStatement(tk,cond,thenStm,elseStm);
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ElseStm>
	 * @param symtab
	 * @return
	 */
	private Statement tryElseStm(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseElseStm(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <ElseStm>
	 * @throws SintaxException
	 */
	private Statement parseElseStm(SymbolTable symtab) throws SintaxException 
	{
		Statement stm = null;
		int[] expected = { ELSE, INT, CHAR, BOOLEAN, IDENTIFIER, IF, WHILE, 
				RETURN, SEMICOLON, LBRACE, RBRACE };
		switch(nextToken.getKind()) 
		{
		case ELSE:
			match(ELSE);
			stm = tryStatement(symtab);
			break;
		case INT:
		case CHAR:
		case BOOLEAN:
		case IDENTIFIER:
		case IF:
		case WHILE:
		case RETURN:
		case SEMICOLON:
		case LBRACE:
		case RBRACE:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <WhileStm>
	 * @param symtab
	 * @return
	 */
	private Statement tryWhileStm(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseWhileStm(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <WhileStm>
	 * @throws SintaxException
	 */
	private Statement parseWhileStm(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { WHILE };
		switch(nextToken.getKind()) 
		{
		case WHILE:
			match(WHILE);
			Token tk = match(LPAREN);
			Expression cond = tryExpr(symtab);
			match(RPAREN);
			Statement body = tryStatement(symtab);
			return actionWhileStatement(tk,cond,body);
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ReturnStm>
	 */	
	private Statement tryReturnStm(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseReturnStm(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <ReturnStm>
	 * @throws SintaxException
	 */
	private Statement parseReturnStm(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { RETURN };
		switch(nextToken.getKind()) 
		{
		case RETURN:
			Token tk = match(RETURN);
			Expression exp = tryReturnExpr(symtab);
			match(SEMICOLON);
			return actionReturnStatement(tk,exp,symtab);
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ReturnExpr>
	 */	
	private Expression tryReturnExpr(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON };
		Expression exp = null;

		try
		{
			exp = parseReturnExpr(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}

	/**
	 * Analiza el s�mbolo <ReturnExpr>
	 * @throws SintaxException
	 */
	private Expression parseReturnExpr(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN, NOT, MINUS, PLUS, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case NOT:
		case MINUS:
		case PLUS:
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			Expression exp = tryExpr(symtab);
			return exp;
		case SEMICOLON:
			return null;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <NoStm>
	 */
	private Statement tryNoStm(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseNoStm(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}

	/**
	 * Analiza el s�mbolo <NoStm>
	 * @throws SintaxException
	 */
	private Statement parseNoStm(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case SEMICOLON:
			match(SEMICOLON);
			return null;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <IdStm>
	 */
	private Statement tryIdStm(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;
		
		try
		{
			stm = parseIdStm(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}
		
	/**
	 * Analiza el s�mbolo <IdStm>
	 * @throws SintaxException
	 */
	private Statement parseIdStm(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { IDENTIFIER };
		switch(nextToken.getKind()) 
		{
		case IDENTIFIER:
			Token tk = match(IDENTIFIER);
			Statement stm = tryIdStmContinue(tk,symtab);
			match(SEMICOLON);
			return stm;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <IdStmContinue>
	 */
	private Statement tryIdStmContinue(Token tk, SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseIdStmContinue(tk,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}
	
	/**
	 * Analiza el s�mbolo <IdStmContinue>
	 * @throws SintaxException
	 */
	private Statement parseIdStmContinue(Token tk, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { ASSIGN, LPAREN, DOT };
		switch(nextToken.getKind()) 
		{
		case ASSIGN:
			match(ASSIGN);
			Expression exp = tryExpr(symtab);
			return actionAssignStatement(tk,exp,symtab);
		case LPAREN:
			CallParameters param = tryFunctionCall(symtab);
			return actionCallStatement(tk,param,symtab);
		case DOT:
			match(DOT);
			Token tid = match(IDENTIFIER);
			CallParameters param2 = tryFunctionCall(symtab);
			return actionCallStatement(tk,tid,param2,symtab);
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <BlockStm>
	 */
	private Statement tryBlockStm(SymbolTable symtab) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
		Statement stm = null;

		try
		{
			stm = parseBlockStm(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return stm;
	}
		
	/**
	 * Analiza el s�mbolo <BlockStm>
	 * @throws SintaxException
	 */
	private Statement parseBlockStm(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { LBRACE };
		switch(nextToken.getKind()) 
		{
		case LBRACE:
			match(LBRACE);
			symtab.createScope();
			BlockStatement block = new BlockStatement();
			tryStatementList(block,symtab);
			symtab.deleteScope();
			match(RBRACE);
			return block;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}


	/**************************************************************/
	/*       EL AN�LISIS DE LAS EXPRESIONES COMIENZA AQU�         */
	/**************************************************************/

	/**
	 * Analiza el s�mbolo <Expr>
	 */
	private Expression tryExpr(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN };
		Expression exp = null;

		try
		{
			exp = parseExpr(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;		
	}

	/**
	 * Analiza el s�mbolo <Expr>
	 * @throws SintaxException
	 */
	private Expression parseExpr(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN, NOT, MINUS, PLUS };
		switch(nextToken.getKind()) 
		{
		case NOT:
		case MINUS:
		case PLUS:
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			Expression exp = tryAndExpr(symtab);
			exp = tryMoreOrExpr(exp, symtab);
			return exp;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <Expr>
	 */
	private Expression tryMoreOrExpr(Expression exp1, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN };
		Expression exp = null;
		
		try
		{
			exp = parseMoreOrExpr(exp1,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;		
	}
	
	/**
	 * Analiza el s�mbolo <MoreOrExpr>
	 * @throws SintaxException
	 */
	private Expression parseMoreOrExpr(Expression exp1, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { OR, COMMA, RPAREN, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case OR:
			Token tk = match(OR);
			Expression exp2 = tryAndExpr(symtab);
			Expression exp = actionOrExpression(tk, exp1,exp2);
			exp = tryMoreOrExpr(exp,symtab);
			return exp;
		case COMMA:
		case RPAREN:
		case SEMICOLON:
			return exp1;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <AndExpr>
	 */
	private Expression tryAndExpr(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR };
		Expression exp = null;

		try
		{
			exp = parseAndExpr(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}
	
	/**
	 * Analiza el s�mbolo <AndExpr>
	 * @throws SintaxException
	 */
	private Expression parseAndExpr(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN, NOT, MINUS, PLUS };
		switch(nextToken.getKind()) 
		{
		case NOT:
		case MINUS:
		case PLUS:
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			Expression exp = tryRelExpr(symtab);
			exp = tryMoreAndExpr(exp,symtab);
			return exp;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <MoreAndExpr>
	 */
	private Expression tryMoreAndExpr(Expression exp1, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR };
		Expression exp = null;

		try
		{
			exp = parseMoreAndExpr(exp1,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}
	
	/**
	 * Analiza el s�mbolo <MoreAndExpr>
	 * @throws SintaxException
	 */
	private Expression parseMoreAndExpr(Expression exp1, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { AND, OR, COMMA, RPAREN, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case AND:
			Token tk = match(AND);
			Expression exp2 = tryRelExpr(symtab);
			Expression exp = actionAndExpression(tk, exp1,exp2);
			exp = tryMoreAndExpr(exp,symtab);
			return exp;
		case OR:
		case COMMA:
		case RPAREN:
		case SEMICOLON:
			return exp1;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <RelExpr>
	 */
	private Expression tryRelExpr(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND };
		Expression exp = null;

		try
		{
			exp = parseRelExpr(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}
		
	/**
	 * Analiza el s�mbolo <RelExpr>
	 * @throws SintaxException
	 */
	private Expression parseRelExpr(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN, NOT, MINUS, PLUS };
		switch(nextToken.getKind()) 
		{
		case NOT:
		case MINUS:
		case PLUS:
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			Expression exp = trySumExpr(symtab);
			exp = tryMoreRelExpr(exp,symtab);
			return exp;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <MoreRelExpr>
	 */
	private Expression tryMoreRelExpr(Expression exp1, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND };
		Expression exp = null;

		try
		{
			exp = parseMoreRelExpr(exp1, symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}
	
	/**
	 * Analiza el s�mbolo <MoreRelExpr>
	 * @throws SintaxException
	 */
	private Expression parseMoreRelExpr(Expression exp1, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { EQ, NE, GT, GE, LT, LE, AND, OR, COMMA, RPAREN, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case EQ:
		case NE:
		case GT:
		case GE:
		case LT:
		case LE:
			Token tk = nextToken;
			int op = parseRelOp();
			Expression exp2 = tryRelExpr(symtab);
			Expression exp = actionRelExpression(tk, op, exp1,exp2);
			return exp;
		case AND:
		case OR:
		case COMMA:
		case RPAREN:
		case SEMICOLON:
			return exp1;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <RelOp>
	 * @throws SintaxException
	 */
	private int parseRelOp() throws SintaxException 
	{
		int[] expected = { EQ, NE, GT, GE, LT, LE };
		switch(nextToken.getKind()) 
		{
		case EQ:
			match(EQ);
			return BinaryExpression.EQ;
		case NE:
			match(NE);
			return BinaryExpression.NEQ;
		case GT:
			match(GT);
			return BinaryExpression.GT;
		case GE:
			match(GE);
			return BinaryExpression.GE;
		case LT:
			match(LT);
			return BinaryExpression.LT;
		case LE:
			match(LE);
			return BinaryExpression.LE;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <SumExpr>
	 */
	private Expression trySumExpr(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE };
		Expression exp = null;

		try
		{
			exp = parseSumExpr(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}
		
	/**
	 * Analiza el s�mbolo <SumExpr>
	 * @throws SintaxException
	 */
	private Expression parseSumExpr(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN, NOT, MINUS, PLUS };
		switch(nextToken.getKind()) 
		{
		case NOT:
		case MINUS:
		case PLUS:
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			Token tk = nextToken;
			int unop = parseUnOp();
			Expression exp1 = tryProdExpr(symtab);
			exp1 = actionUnaryExpression(tk,unop,exp1);
			exp1 = tryMoreSumExpr(exp1,symtab);
			return exp1;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <UnOp>
	 * @throws SintaxException
	 */
	private int parseUnOp() throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN, NOT, MINUS, PLUS };
		switch(nextToken.getKind()) 
		{
		case NOT:
			match(NOT);
			return UnaryExpression.NOT;
		case MINUS:
			match(MINUS);
			return UnaryExpression.MINUS;
		case PLUS:
			match(PLUS);
			return UnaryExpression.PLUS;
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			return UnaryExpression.NONE;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <MoreSumExpr>
	 */
	private Expression tryMoreSumExpr(Expression exp1, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE };
		Expression exp = null;

		try
		{
			exp = parseMoreSumExpr(exp1,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;
	}
	
	/**
	 * Analiza el s�mbolo <MoreSumExpr>
	 * @throws SintaxException
	 */
	private Expression parseMoreSumExpr(Expression exp1, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { PLUS, MINUS, EQ, NE, GT, GE, LT, LE, 
				AND, OR, COMMA, RPAREN, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case PLUS:
		case MINUS:
			Token tk = nextToken;
			int op = parseSumOp();
			Expression exp2 = tryProdExpr(symtab);
			Expression exp = actionSumExpression(tk, op, exp1,exp2);
			exp = tryMoreSumExpr(exp,symtab);
			return exp;
		case EQ:
		case NE:
		case GT:
		case GE:
		case LT:
		case LE:
		case AND:
		case OR:
		case COMMA:
		case RPAREN:
		case SEMICOLON:
			return exp1;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <SumOp>
	 * @throws SintaxException
	 */
	private int parseSumOp() throws SintaxException 
	{
		int[] expected = { MINUS, PLUS };
		switch(nextToken.getKind()) 
		{
		case MINUS:
			match(MINUS);
			return BinaryExpression.MINUS;
		case PLUS:
			match(PLUS);
			return BinaryExpression.PLUS;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ProdExpr>
	 */
	private Expression tryProdExpr(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS };
		Expression exp = null;

		try
		{
			exp = parseProdExpr(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		{
			return exp;
		}
	}
		
	/**
	 * Analiza el s�mbolo <ProdExpr>
	 * @throws SintaxException
	 */
	private Expression parseProdExpr(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN };
		switch(nextToken.getKind()) 
		{
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			Expression exp1 = tryFactor(symtab);
			Expression exp = tryMoreProdExpr(exp1,symtab);
			return exp;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <MoreProdExpr>
	 */
	private Expression tryMoreProdExpr(Expression exp1, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS };
		Expression exp = null;

		try
		{
			exp = parseMoreProdExpr(exp1,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		{
			return exp;
		}
	}
	
	/**
	 * Analiza el s�mbolo <MoreProdExpr>
	 * @throws SintaxException
	 */
	private Expression parseMoreProdExpr(Expression exp1, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { PROD, DIV, MOD, PLUS, MINUS, EQ, NE, GT, GE, LT, LE, 
				AND, OR, COMMA, RPAREN, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case PROD:
		case DIV:
		case MOD:
			Token tk = nextToken;
			int op = parseProdOp();
			Expression exp2 = tryFactor(symtab);
			Expression exp = actionProdExpression(tk, op, exp1,exp2);
			exp = tryMoreProdExpr(exp,symtab);
			return exp;
		case PLUS:
		case MINUS:
		case EQ:
		case NE:
		case GT:
		case GE:
		case LT:
		case LE:
		case AND:
		case OR:
		case COMMA:
		case RPAREN:
		case SEMICOLON:
			return exp1;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ProdOp>
	 * @throws SintaxException
	 */
	private int parseProdOp() throws SintaxException 
	{
		int[] expected = { PROD, DIV, MOD };
		switch(nextToken.getKind()) 
		{
		case PROD:
			match(PROD);
			return BinaryExpression.PROD;
		case DIV:
			match(DIV);
			return BinaryExpression.DIV;
		case MOD:
			match(MOD);
			return BinaryExpression.MOD;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <Factor>
	 */
	private Expression tryFactor(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS, PROD, DIV, MOD };
		Expression exp = null;

		try
		{
			exp = parseFactor(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;	
	}
	
	/**
	 * Analiza el s�mbolo <Factor>
	 * @throws SintaxException
	 */
	private Expression parseFactor(SymbolTable symtab) throws SintaxException 
	{
		Expression exp;
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN };
		switch(nextToken.getKind()) 
		{
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
			exp = tryLiteral(symtab);
			break;
		case IDENTIFIER:
			exp = tryReference(symtab);
			break;
		case LPAREN:
			match(LPAREN);
			exp = tryExpr(symtab);
			match(RPAREN);
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
		return exp;
	}

	/**
	 * Analiza el s�mbolo <Literal>
	 */
	private Expression tryLiteral(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS, PROD, DIV, MOD };
		Expression exp = null;

		try
		{
			exp = parseLiteral(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;	
	}
	
	/**
	 * Analiza el s�mbolo <Literal>
	 * @throws SintaxException
	 */
	private Expression parseLiteral(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, FALSE };
		switch(nextToken.getKind()) 
		{
		case INTEGER_LITERAL:
			Token ti = match(INTEGER_LITERAL);
			return actionIntegerLiteral(ti);
		case CHAR_LITERAL:
			Token tc = match(CHAR_LITERAL);
			return new CharLiteralExpression(tc.getLexeme());
		case TRUE:
			match(TRUE);
			return new BooleanLiteralExpression(true);
		case FALSE:
			match(FALSE);
			return new BooleanLiteralExpression(false);
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <Reference>
	 */
	private Expression tryReference(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS, PROD, DIV, MOD };
		Expression exp = null;

		try
		{
			exp = parseReference(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;	
	}
	
	/**
	 * Analiza el s�mbolo <Reference>
	 * @throws SintaxException
	 */
	private Expression parseReference(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { IDENTIFIER };
		switch(nextToken.getKind()) 
		{
		case IDENTIFIER:
			Token tid = match(IDENTIFIER);
			Expression exp = tryReferenceContinue(tid,symtab);
			return exp;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ReferenceContinue>
	 */
	private Expression tryReferenceContinue(Token tid, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS, PROD, DIV, MOD };
		Expression exp = null;

		try
		{
			exp = parseReferenceContinue(tid,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return exp;	
	}
	
	/**
	 * Analiza el s�mbolo <ReferenceContinue>
	 * @throws SintaxException
	 */
	private Expression parseReferenceContinue(Token tid, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { LPAREN, DOT, PROD, DIV, MOD, PLUS, MINUS, EQ, 
				NE, GT, GE, LT, LE, AND, OR, COMMA, RPAREN, SEMICOLON };
		switch(nextToken.getKind()) 
		{
		case LPAREN:
			CallParameters params = tryFunctionCall(symtab);
			return actionReferenceExpression(tid,null,params,symtab);
		case DOT:
			match(DOT);
			Token tid2 = match(IDENTIFIER);
			CallParameters params2 = tryFunctionCall(symtab);
			return actionReferenceExpression(tid,tid2,params2,symtab);
		case PROD:
		case DIV:
		case MOD:
		case PLUS:
		case MINUS:
		case EQ:
		case NE:
		case GT:
		case GE:
		case LT:
		case LE:
		case AND:
		case OR:
		case COMMA:
		case RPAREN:
		case SEMICOLON:
			return actionReferenceExpression(tid,null,null,symtab);
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <FunctionCall>
	 */
	private CallParameters tryFunctionCall(SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS, PROD, DIV, MOD };
		CallParameters params = null;

		try
		{
			params = parseFunctionCall(symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		return params;	
	}
	
	/**
	 * Analiza el s�mbolo <FunctionCall>
	 * @throws SintaxException
	 */
	private CallParameters parseFunctionCall(SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { LPAREN };
		switch(nextToken.getKind()) 
		{
		case LPAREN:
			match(LPAREN);
			CallParameters param = new CallParameters();
			tryExprList(param,symtab);
			match(RPAREN);
			return param;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <ExprList>
	 */
	private void tryExprList(CallParameters param, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { RPAREN };

		try
		{
			parseExprList(param,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}
	
	/**
	 * Analiza el s�mbolo <ExprList>
	 * @throws SintaxException
	 */
	private void parseExprList(CallParameters param, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { INTEGER_LITERAL, CHAR_LITERAL, TRUE, 
				FALSE, IDENTIFIER, LPAREN, NOT, MINUS, PLUS , RPAREN };
		switch(nextToken.getKind()) 
		{
		case NOT:
		case MINUS:
		case PLUS:
		case INTEGER_LITERAL:
		case CHAR_LITERAL:
		case TRUE:
		case FALSE:
		case IDENTIFIER:
		case LPAREN:
			Expression exp = tryExpr(symtab);
			param.addParameter(exp);
			tryMoreExpr(param,symtab);
			break;
		case RPAREN:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el s�mbolo <MoreExpr>
	 */
	private void tryMoreExpr(CallParameters param, SymbolTable symtab) 
	{
		int[] lsync = { };
		int[] rsync = { RPAREN };

		try
		{
			parseMoreExpr(param,symtab);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}
	
	/**
	 * Analiza el s�mbolo <MoreExpr>
	 * @throws SintaxException
	 */
	private void parseMoreExpr(CallParameters param, SymbolTable symtab) throws SintaxException 
	{
		int[] expected = { COMMA, RPAREN };
		switch(nextToken.getKind()) 
		{
		case COMMA:
			match(COMMA);
			Expression exp = tryExpr(symtab);
			param.addParameter(exp);
			tryMoreExpr(param,symtab);
			break;
		case RPAREN:
			break;
		default:
			throw new SintaxException(nextToken,expected);
		}
	}
}
