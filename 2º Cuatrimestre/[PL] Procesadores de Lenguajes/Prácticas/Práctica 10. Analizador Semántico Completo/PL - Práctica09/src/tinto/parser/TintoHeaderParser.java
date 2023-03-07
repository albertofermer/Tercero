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
//           Departamento de Tecnologías de la Información          //
//   Área de Ciencias de la Computación e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//

package tinto.parser;

import tinto.ast.*;
import tinto.ast.struct.*;
import java.io.FileInputStream;
import java.util.Vector;

/**
 * Analizador sintáctico de Tinto basado en una gramática BNF y LL(1)
 *  
 * @author Francisco José Moreno Velo
 *
 */
public class TintoHeaderParser implements TokenConstants {

	//----------------------------------------------------------------//
	//           Miembros privados dedicados al análisis              //
	//----------------------------------------------------------------//
	
	/**
	 * Analizador léxico
	 */
	private TintoLexer lexer;

	/**
	 * Último token analizado
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
	//              Métodos relacionados con el análisis              //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 */
	public TintoHeaderParser(FileInputStream fis) 
	{
		this.lexer = new TintoLexer(fis);
		this.prevToken = null;
		this.nextToken = lexer.getNextToken();
	}
	
	/**
	 * Método de análisis de un fichero
	 * 
	 * @param file Fichero a analizar
	 * @return Resultado del análisis sintáctico
	 */
	public LibraryDeclaration parse(String libname) 
	{
		this.errorCount = 0;
		this.errorMsg = "";
		try 
		{
			LibraryDeclaration library = tryCompilationUnit(libname);
			return library;
		}
		catch(Throwable er)
		{
			catchError(er);
			return null;
		}
	}

	//----------------------------------------------------------------//
	//       Métodos relacionados con el tratamiento de errores       //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el número de errores del análisis
	 * @return
	 */
	public int getErrorCount()
	{
		return this.errorCount;
	}
	
	/**
	 * Obtiene el mensaje de error del análisis
	 * @return
	 */
	public String getErrorMsg()
	{
		return this.errorMsg;
	}
	
	/**
	 * Almacena un error de análisis
	 * @param ex
	 */
	private void catchError(Throwable ex)
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
	//                Métodos de verificación semántica               //
	//----------------------------------------------------------------//

	/**
	 * Verifica que el nombre de la biblioteca corresponde al nombre del
	 * fichero ".tinto" 
	 */
	private void verifyLibraryName(Token tk, LibraryDeclaration library)
	{
		if(!tk.getLexeme().equals(library.getName()))
		{
			int errorcode = SemanticException.LIBRARY_NAME_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que una cierta función no haya sido definido previamente
	 * en la biblioteca
	 */
	private void verifyDuplicatedFunction(Token tk, Function function, LibraryDeclaration library)
	{
		int[] type = function.getArgumentTypes();
		Function dup = library.getAnyFunction(tk.getLexeme(),type);
		if(dup != null)
		{
			int errorcode = SemanticException.DUPLICATED_FUNCTION_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	/**
	 * Verifica que el nombre de un argumento no esté duplicado
	 */
	private void verifyDuplicatedArgument(Token tk, Vector<Variable> arguments)
	{
		if(arguments.contains(tk.getLexeme()))
		{	
			int errorcode = SemanticException.DUPLICATED_ARGUMENT_EXCEPTION;
			catchError(new SemanticException(errorcode,tk));
		}
	}

	//----------------------------------------------------------------//
	//                       Acciones semánticas                      //
	//----------------------------------------------------------------//

    /**
     * Acción semántica que crea una función de una biblioteca
     */
	private void actionLibraryFunction(int acc, int type, Token tid, Vector<Variable> arguments, LibraryDeclaration library)
	{
	    Function function = new Function(acc, type, tid.getLexeme(), library.getName());
	    function.addArgumentList(arguments);
		verifyDuplicatedFunction(tid,function,library);
		library.addFunction(function);  
	}

	/**
     * Acción semántica que añade un argumento a una lista de argumentos
     */
	private void actionAddArgument(int type, Token tk, Vector<Variable> arguments)
	{
		verifyDuplicatedArgument(tk,arguments);
		arguments.add(new Variable(type, tk.getLexeme()) );
	}
	
	//----------------------------------------------------------------//
	//                Análisis sintáctico descendente                 //
	//----------------------------------------------------------------//
	
	/**
	 * Método que consume un token de la cadena de entrada
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
	 * Analiza el símbolo <CompilationUnit>
	 */
	private LibraryDeclaration tryCompilationUnit(String name) 
	{
		int[] lsync = { };
		int[] rsync = { EOF };
		LibraryDeclaration library = null;

		try
		{
			library = parseCompilationUnit(name);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		
		return library;
	}
	
	/**
	 * Analiza el símbolo <CompilationUnit>
	 * @throws SintaxException
	 */
	private LibraryDeclaration parseCompilationUnit(String name) throws SintaxException 
	{
		Vector<String> imported = new Vector<String>();
		LibraryDeclaration library;
		  
		int[] expected = { IMPORT, LIBRARY };
		switch(nextToken.getKind()) 
		{
			case IMPORT:
			case LIBRARY:
				tryImportClauseList(imported);
				library = tryLibraryDecl(name,imported);
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
		
		return library;
	}

	/**
	 * Analiza el símbolo <ImportClauseList>
	 */
	private void tryImportClauseList(Vector<String> imported) 
	{
		int[] lsync = { };
		int[] rsync = { LIBRARY };

		try
		{
			parseImportClauseList(imported);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}
	
	/**
	 * Analiza el símbolo <ImportClauseList>
	 * @throws SintaxException
	 */
	private void parseImportClauseList(Vector<String> imported) throws SintaxException 
	{
		int[] expected = { IMPORT, LIBRARY };
		switch(nextToken.getKind()) 
		{
			case IMPORT:
				tryImportClause(imported);
				tryImportClauseList(imported);
				break;
			case LIBRARY:
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <ImportClause>
	 */
	private void tryImportClause(Vector<String> imported) 
	{
		int[] lsync = { SEMICOLON };
		int[] rsync = { IMPORT, LIBRARY };
		
		try
		{
			parseImportClause(imported);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}
	
	/**
	 * Analiza el símbolo <ImportClause>
	 * @throws SintaxException
	 */
	private void parseImportClause(Vector<String> imported) throws SintaxException 
	{
		int[] expected = { IMPORT };
		switch(nextToken.getKind()) 
		{
			case IMPORT:
				match(IMPORT);
				Token tid = match(IDENTIFIER);
				match(SEMICOLON);
				imported.addElement(tid.getLexeme());
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <LibraryDecl>
	 */
	private LibraryDeclaration tryLibraryDecl(String name, Vector<String> imported)  
	{
		int[] lsync = { };
		int[] rsync = { };
		LibraryDeclaration library = new LibraryDeclaration(name);
		library.setImportedList(imported);
		
		try
		{
			parseLibraryDecl(library);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		
		return library;
	}
	
	/**
	 * Analiza el símbolo <LibraryDecl>
	 * @throws SintaxException
	 */
	private void parseLibraryDecl(LibraryDeclaration library) throws SintaxException 
	{
		int[] expected = { LIBRARY };
		switch(nextToken.getKind()) 
		{
			case LIBRARY:
				match(LIBRARY);
				Token tid = match(IDENTIFIER);
				verifyLibraryName(tid,library);
				match(LBRACE);
				tryFunctionList(library);
				match(RBRACE);
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <FunctionList>
	 */
	private void tryFunctionList(LibraryDeclaration library) 
	{
		int[] lsync = { };
		int[] rsync = { RBRACE }; 

		try
		{
			parseFunctionList(library);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		} 
	}
	
	/**
	 * Analiza el símbolo <FunctionList>
	 * @throws SintaxException
	 */
	private void parseFunctionList(LibraryDeclaration library) throws SintaxException 
	{
		int[] expected = { PUBLIC, PRIVATE };
		switch(nextToken.getKind()) 
		{
			case PUBLIC:
			case PRIVATE:
				tryFunctionDecl(library);
				tryFunctionList(library);
				break;
			case RBRACE:
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <FunctionDecl>
	 */
	private void tryFunctionDecl(LibraryDeclaration library) 
	{
		int[] lsync = { };
		int[] rsync = { PUBLIC, PRIVATE }; 
		try
		{
			parseFunctionDecl(library);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}  
	}
	
	/**
	 * Analiza el símbolo <FunctionDecl>
	 * @throws SintaxException
	 */
	private void parseFunctionDecl(LibraryDeclaration library) throws SintaxException 
	{
		int[] expected = { PUBLIC, PRIVATE };
		switch(nextToken.getKind()) 
		{
			case PUBLIC:
			case PRIVATE:
				int acc = tryAccess();
				int type = tryFunctionType();
				Token tid = match(IDENTIFIER);
				Vector<Variable> args = tryArgumentDecl();
				parseFunctionBody();
				actionLibraryFunction(acc,type,tid,args,library);
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <Access>
	 */
	private int tryAccess() 
	{
		int[] lsync = { };
		int[] rsync = { VOID, INT, CHAR, BOOLEAN };
		int acc = Access.PUBLIC_ACCESS; 

		try
		{
			acc = parseAccess();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		
		return acc;
	}
	
	/**
	 * Analiza el símbolo <Access>
	 * @throws SintaxException
	 */
	private int parseAccess() throws SintaxException 
	{
		int acc;
		int[] expected = { PUBLIC, PRIVATE };
		switch(nextToken.getKind()) 
		{
			case PUBLIC:
				match(PUBLIC);
				acc = Access.PUBLIC_ACCESS;
				break;
			case PRIVATE:
				match(PRIVATE);
				acc = Access.PRIVATE_ACCESS;
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
		return acc;
	}
	
	/**
	 * Analiza el símbolo <FunctionType>
	 */
	private int tryFunctionType() 
	{
		int[] lsync = { };
		int[] rsync = { IDENTIFIER };
		int type = Type.MISMATCH_TYPE;

		try
		{
			type = parseFunctionType();
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
		
		return type;
	}
	
	/**
	 * Analiza el símbolo <FunctionType>
	 * @throws SintaxException
	 */
	private int parseFunctionType() throws SintaxException 
	{
		int type;
		int[] expected = { VOID, INT, CHAR, BOOLEAN };
		switch(nextToken.getKind()) 
		{
			case VOID:
				match(VOID);
				type = Type.VOID_TYPE;
				break;
			case INT:
			case CHAR:
			case BOOLEAN:
				type = tryType();
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
		return type;
	}
	
	/**
	 * Analiza el símbolo <Type>
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
	 * Analiza el símbolo <Type>
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
	 * Analiza el símbolo <ArgumentDecl>
	 */
	private Vector<Variable> tryArgumentDecl() 
	{
		int[] lsync = { RPAREN };
		int[] rsync = { LBRACE };
		Vector<Variable> args = null;
		
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
	 * Analiza el símbolo <ArgumentDecl>
	 * @throws SintaxException
	 */
	private Vector<Variable> parseArgumentDecl() throws SintaxException 
	{
		Vector<Variable> args = new Vector<Variable>();
		int[] expected = { LPAREN };
		switch(nextToken.getKind()) 
		{
			case LPAREN:
				match(LPAREN);
				tryArgumentList(args);
				match(RPAREN);
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
		return args;
	}

	/**
	 * Analiza el símbolo <ArgumentList>
	 */
	private void tryArgumentList(Vector<Variable> args) 
	{
		int[] lsync = { };
		int[] rsync = { RPAREN };
		
		try
		{
			parseArgumentList(args);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}
	
	/**
	 * Analiza el símbolo <ArgumentList>
	 * @throws SintaxException
	 */
	private void parseArgumentList(Vector<Variable> args) throws SintaxException 
	{
		int[] expected = { INT, CHAR, BOOLEAN, RPAREN };
		switch(nextToken.getKind()) 
		{
			case INT:
			case CHAR:
			case BOOLEAN:
				tryArgument(args);
				tryMoreArguments(args);
				break;
			case RPAREN:
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <Argument>
	 */
	private void tryArgument(Vector<Variable> args) 
	{
		int[] lsync = { };
		int[] rsync = { COMMA, RPAREN };
		
		try
		{
			parseArgument(args);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}
	
	/**
	 * Analiza el símbolo <Argument>
	 * @throws SintaxException
	 */
	private void parseArgument(Vector<Variable> args) throws SintaxException 
	{
		int[] expected = { INT, CHAR, BOOLEAN };
		switch(nextToken.getKind()) 
		{
			case INT:
			case CHAR:
			case BOOLEAN:
				int type = tryType();
				Token tid = match(IDENTIFIER);
				actionAddArgument(type,tid,args);
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <MoreArguments>
	 */
	private void tryMoreArguments(Vector<Variable> args) 
	{
		int[] lsync = { };
		int[] rsync = { RPAREN };
		
		try
		{
			parseMoreArguments(args);
		}
		catch(Exception ex)
		{
			catchError(ex);
			skipTo(lsync,rsync);
		}
	}
	
	/**
	 * Analiza el símbolo <MoreArguments>
	 * @throws SintaxException
	 */
	private void parseMoreArguments(Vector<Variable> args) throws SintaxException 
	{
		int[] expected = { COMMA, RPAREN };
		switch(nextToken.getKind()) 
		{
			case COMMA:
				match(COMMA);
				tryArgument(args);
				tryMoreArguments(args);
				break;
			case RPAREN:
				break;
			default:
				throw new SintaxException(nextToken,expected);
		}
	}

	/**
	 * Analiza el símbolo <FunctionBody>
	 * @throws SintaxException
	 */
	private void parseFunctionBody() throws SintaxException 
	{
		match(LBRACE);
		int level = 1;
		while(level != 0) 
		{
			if(nextToken.getKind() == EOF) break;
			else if(nextToken.getKind() == LBRACE) level ++;
			else if(nextToken.getKind() == RBRACE) level --;
			prevToken = nextToken;
			nextToken = lexer.getNextToken();			
		}
	}
}
