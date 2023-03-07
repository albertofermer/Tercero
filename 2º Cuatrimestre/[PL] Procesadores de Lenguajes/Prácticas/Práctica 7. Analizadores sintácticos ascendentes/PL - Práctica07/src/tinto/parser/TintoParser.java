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

import java.io.*;

/**
 * Analizador sintáctico ascendente del lenguaje Tinto
 * 
 * R0 ... X  ::= CompilationUnit
 * R1 ... CompilationUnit  ::=  ImportClauseList  LibraryDecl
 * R2 ... ImportClauseList  ::=  ImportClauseList  ImportClause
 * R3 ... ImportClauseList  ::=  lambda
 * R4 ... ImportClause  ::=  import  identifier  semicolon
 * R5 ... LibraryDecl  ::=  library  identifier  lbrace  FunctionList  rbrace
 * R6 ... FunctionList  ::=  FunctionList  FunctionDecl
 * R7 ... FunctionList  ::=  lambda
 * R8 ... FunctionDecl  ::=  Access  FunctionType   identifier   ArgumentDecl  FunctionBody
 * R9 ... Access ::= public
 * R10 .. Access ::= private
 * R11 .. FunctionType  ::=  Type
 * R12 .. FunctionType  ::=  void
 * R13 .. Type ::= int
 * R14 .. Type ::=  char
 * R15 .. Type ::=  boolean
 * R16 .. ArgumentDecl  ::=  lparen  rparen
 * R17 .. ArgumentDecl  ::=  lparen  ArgumentList  rparen
 * R18 .. ArgumentList  ::=  Argument
 * R19 .. ArgumentList  ::=  ArgumentList  comma  Argument
 * R20 .. Argument  ::=  Type  identifier
 * R21 .. FunctionBody  ::= lbrace StatementList rbrace
 * R22 .. StatementList  ::=  StatementList  Statement
 * R23 .. StatementList  ::=  lambda
 * R24 .. Statement  ::=  Decl
 * R25 .. Statement  ::=  IdStm
 * R26 .. Statement  ::=  IfStm
 * R27 .. Statement  ::=  WhileStm
 * R28 .. Statement  ::=  ReturnStm
 * R29 .. Statement  ::=  NoStm
 * R30 .. Statement  ::=  BlockStm
 * R31 .. Decl  ::=  Type  IdList  semicolon
 * R32 .. IdList  ::=   identifier
 * R33 .. IdList  ::=   identifier   assign   Expr
 * R34 .. IdList  ::=  IdList   comma  identifier
 * R35 .. IdList  ::=  IdList   comma  identifier   assign   Expr
 * R36 .. IfStm  ::=  if   lparen   Expr  rparen   Statement
 * R37 .. IfStm  ::=  if   lparen   Expr  rparen   Statement  else  Statement
 * R38 .. WhileStm  ::=  while   lparen  Expr   rparen   Statement
 * R39 .. ReturnStm  ::=  return  Expr  semicolon
 * R40 .. ReturnStm  ::=  return  semicolon
 * R41 .. NoStm  ::=  semicolon
 * R42 .. IdStm  ::=  identifier   assign   Expr  semicolon
 * R43 .. IdStm  ::=  identifier  FunctionCall   semicolon
 * R44 .. IdStm  ::=  identifier  dot   identifier  FunctionCall  semicolon
 * R45 .. BlockStm  ::=  lbrace  StatementList  rbrace
 * R46 .. Expr  ::=  AndExpr
 * R47 .. Expr  ::=  Expr   or  AndExpr
 * R48 .. AndExpr  ::=  RelExpr
 * R49 .. AndExpr  ::=  AndExpr  and  RelExpr
 * R50 .. RelExpr  ::=  SumExpr
 * R51 .. RelExpr  ::=   SumExpr  eq  SumExpr
 * R52 .. RelExpr  ::=   SumExpr  ne  SumExpr
 * R53 .. RelExpr  ::=   SumExpr  gt  SumExpr
 * R54 .. RelExpr  ::=   SumExpr  ge  SumExpr
 * R55 .. RelExpr  ::=   SumExpr  lt  SumExpr
 * R56 .. RelExpr  ::=   SumExpr  le  SumExpr
 * R57 .. SumExpr  ::=  not  ProdExpr
 * R58 .. SumExpr  ::=  minus  ProdExpr
 * R59 .. SumExpr  ::=  plus  ProdExpr
 * R60 .. SumExpr  ::=  ProdExpr
 * R61 .. SumExpr  ::=  SumExpr  minus  ProdExpr
 * R62 .. SumExpr  ::=  SumExpr  plus  ProdExpr
 * R63 .. ProdExpr  ::=  Factor
 * R64 .. ProdExpr  ::=  ProdExpr  prod  Factor
 * R65 .. ProdExpr  ::=  ProdExpr  div   Factor
 * R66 .. ProdExpr  ::=  ProdExpr  mod  Factor
 * R67 .. Factor  ::=  Literal
 * R68 .. Factor  ::=  Reference
 * R69 .. Factor  ::=  lparen   Expr   rparen
 * R70 .. Literal  ::=  integer_literal
 * R71 .. Literal  ::=  char_literal
 * R72 .. Literal  ::=  true
 * R73 .. Literal  ::=  false
 * R74 .. Reference  ::=  identifier
 * R75 .. Reference  ::=  identifier  FunctionCall
 * R76 .. Reference  ::=  identifier  dot   identifier  FunctionCall
 * R77 .. FunctionCall  ::=  lparen  rparen
 * R78 .. FunctionCall  ::=  lparen  ExprList  rparen
 * R79 .. ExprList  ::=  Expr
 * R80 .. ExprList  ::=  ExprList  comma  Expr
 * 
 * @author Francisco José Moreno Velo
 */
public class TintoParser extends SLRParser implements TokenConstants, SymbolConstants {

	/**
	 * Constructor
	 *
	 */
	public TintoParser() 
	{
		initRules();
		initActionTable();
		initGotoTable();
	}
	
	/**
	 * Método de análisis de un fichero
	 * @param filename
	 * @return
	 * @throws IOException 
	 */
	public boolean parse(File file) throws IOException, SintaxException
	{
		return parse(new TintoLexer(file));
	}
	

	/**
	 * Crea la matriz de reglas
	 *
	 */
	private void initRules() {
		int[][] initRule = {
				{ 0, 0 } ,
				{ S_COMPILATION_UNIT, 2 },
				{ S_IMPORT_CLAUSE_LIST, 2 },
				{ S_IMPORT_CLAUSE_LIST, 0 },
				{ S_IMPORT_CLAUSE, 3 },
				{ S_LIBRARY_DECL, 5 },
				{ S_FUNCTION_LIST, 2 },
				{ S_FUNCTION_LIST, 0 },
				{ S_FUNCTION_DECL, 5 },
				{ S_ACCESS, 1},
				{ S_ACCESS, 1},
				{ S_FUNCTION_TYPE, 1 },
				{ S_FUNCTION_TYPE, 1 },
				{ S_TYPE, 1 },
				{ S_TYPE, 1 },
				{ S_TYPE, 1 },
				{ S_ARGUMENT_DECL, 2 },
				{ S_ARGUMENT_DECL, 3 },
				{ S_ARGUMENT_LIST, 1 },
				{ S_ARGUMENT_LIST, 3 },
				{ S_ARGUMENT, 2 },
				{ S_FUNCTION_BODY, 3 },
				{ S_STATEMENT_LIST, 2 },
				{ S_STATEMENT_LIST, 0 },
				{ S_STATEMENT, 1 },
				{ S_STATEMENT, 1 },
				{ S_STATEMENT, 1 },
				{ S_STATEMENT, 1 },
				{ S_STATEMENT, 1 },
				{ S_STATEMENT, 1 },
				{ S_STATEMENT, 1 },
				{ S_DECL, 3 },
				{ S_ID_LIST, 1 },
				{ S_ID_LIST, 3 },
				{ S_ID_LIST, 3 },
				{ S_ID_LIST, 5 },
				{ S_IF_STM, 5 },
				{ S_IF_STM, 7 },
				{ S_WHILE_STM, 5 },
				{ S_RETURN_STM, 3 },
				{ S_RETURN_STM, 2 },
				{ S_NO_STM, 1 },
				{ S_ID_STM, 4 },
				{ S_ID_STM, 3 },
				{ S_ID_STM, 5 },
				{ S_BLOCK_STM, 3 },
				{ S_EXPR, 1 },
				{ S_EXPR, 3 },
				{ S_AND_EXPR, 1 },
				{ S_AND_EXPR, 3 },
				{ S_REL_EXPR, 1 },
				{ S_REL_EXPR, 3 },
				{ S_REL_EXPR, 3 },
				{ S_REL_EXPR, 3 },
				{ S_REL_EXPR, 3 },
				{ S_REL_EXPR, 3 },
				{ S_REL_EXPR, 3 },
				{ S_SUM_EXPR, 2 },
				{ S_SUM_EXPR, 2 },
				{ S_SUM_EXPR, 2 },
				{ S_SUM_EXPR, 1 },
				{ S_SUM_EXPR, 3 },
				{ S_SUM_EXPR, 3 },
				{ S_PROD_EXPR, 1 },
				{ S_PROD_EXPR, 3 },
				{ S_PROD_EXPR, 3 },
				{ S_PROD_EXPR, 3 },
				{ S_FACTOR, 1 },
				{ S_FACTOR, 1 },
				{ S_FACTOR, 3 },
				{ S_LITERAL, 1 },
				{ S_LITERAL, 1 },
				{ S_LITERAL, 1 },
				{ S_LITERAL, 1 },
				{ S_REFERENCE, 1 },
				{ S_REFERENCE, 2 },
				{ S_REFERENCE, 4 },
				{ S_FUNCTION_CALL, 2 },
				{ S_FUNCTION_CALL, 3 },
				{ S_EXPR_LIST, 1 },
				{ S_EXPR_LIST, 3 }
		};

		this.rule = initRule;
	}
	
	/**
	 * Inicializa la tabla de acciones
	 *
	 */
	private void initActionTable() {
		actionTable = new ActionElement[143][40]; // 143 estados, 40 tokens
		
		actionTable[0][IMPORT] = new ActionElement(ActionElement.REDUCE,3);
		actionTable[0][LIBRARY] = new ActionElement(ActionElement.REDUCE,3);

		actionTable[1][EOF] = new ActionElement(ActionElement.ACCEPT,0);

		actionTable[2][IMPORT] = new ActionElement(ActionElement.SHIFT,5);
		actionTable[2][LIBRARY] = new ActionElement(ActionElement.SHIFT,6);

		actionTable[3][EOF] = new ActionElement(ActionElement.REDUCE,1);

		actionTable[4][IMPORT] = new ActionElement(ActionElement.REDUCE,2);
		actionTable[4][LIBRARY] = new ActionElement(ActionElement.REDUCE,2);

		actionTable[5][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,7);

		actionTable[6][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,8);

		actionTable[7][SEMICOLON] = new ActionElement(ActionElement.SHIFT,9);

		actionTable[8][LBRACE] = new ActionElement(ActionElement.SHIFT,10);

		actionTable[9][IMPORT] = new ActionElement(ActionElement.REDUCE,4);
		actionTable[9][LIBRARY] = new ActionElement(ActionElement.REDUCE,4);

		actionTable[10][RBRACE] = new ActionElement(ActionElement.REDUCE,7);
		actionTable[10][PUBLIC] = new ActionElement(ActionElement.REDUCE,7);
		actionTable[10][PRIVATE] = new ActionElement(ActionElement.REDUCE,7);

		actionTable[11][RBRACE] = new ActionElement(ActionElement.SHIFT,12);
		actionTable[11][PUBLIC] = new ActionElement(ActionElement.SHIFT,15);
		actionTable[11][PRIVATE] = new ActionElement(ActionElement.SHIFT,16);

		actionTable[12][EOF] = new ActionElement(ActionElement.REDUCE,5);

		actionTable[13][RBRACE] = new ActionElement(ActionElement.REDUCE,6);
		actionTable[13][PUBLIC] = new ActionElement(ActionElement.REDUCE,6);
		actionTable[13][PRIVATE] = new ActionElement(ActionElement.REDUCE,6);

		actionTable[14][VOID] = new ActionElement(ActionElement.SHIFT,19);
		actionTable[14][INT] = new ActionElement(ActionElement.SHIFT,20);
		actionTable[14][CHAR] = new ActionElement(ActionElement.SHIFT,21);
		actionTable[14][BOOLEAN] = new ActionElement(ActionElement.SHIFT,22);

		actionTable[15][VOID] = new ActionElement(ActionElement.REDUCE,9);
		actionTable[15][INT] = new ActionElement(ActionElement.REDUCE,9);
		actionTable[15][CHAR] = new ActionElement(ActionElement.REDUCE,9);
		actionTable[15][BOOLEAN] = new ActionElement(ActionElement.REDUCE,9);

		actionTable[16][VOID] = new ActionElement(ActionElement.REDUCE,10);
		actionTable[16][INT] = new ActionElement(ActionElement.REDUCE,10);
		actionTable[16][CHAR] = new ActionElement(ActionElement.REDUCE,10);
		actionTable[16][BOOLEAN] = new ActionElement(ActionElement.REDUCE,10);
		
		actionTable[17][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,23);

		actionTable[18][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,11);

		actionTable[19][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,12);

		actionTable[20][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,13);

		actionTable[21][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,14);

		actionTable[22][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,15);

		actionTable[23][LPAREN] = new ActionElement(ActionElement.SHIFT,25);

		actionTable[24][LBRACE] = new ActionElement(ActionElement.SHIFT,27);

		actionTable[25][RPAREN] = new ActionElement(ActionElement.SHIFT,28);
		actionTable[25][INT] = new ActionElement(ActionElement.SHIFT,20);
		actionTable[25][CHAR] = new ActionElement(ActionElement.SHIFT,21);
		actionTable[25][BOOLEAN] = new ActionElement(ActionElement.SHIFT,22);

		actionTable[26][RBRACE] = new ActionElement(ActionElement.REDUCE,8);
		actionTable[26][PUBLIC] = new ActionElement(ActionElement.REDUCE,8);
		actionTable[26][PRIVATE] = new ActionElement(ActionElement.REDUCE,8);

		actionTable[27][RBRACE] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][INT] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][CHAR] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][BOOLEAN] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][IF] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][WHILE] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][RETURN] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][SEMICOLON] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[27][LBRACE] = new ActionElement(ActionElement.REDUCE,23);

		actionTable[28][LBRACE] = new ActionElement(ActionElement.REDUCE,16);

		actionTable[29][RPAREN] = new ActionElement(ActionElement.SHIFT,33);
		actionTable[29][COMMA] = new ActionElement(ActionElement.SHIFT,34);
		
		actionTable[30][RPAREN] = new ActionElement(ActionElement.REDUCE,18);
		actionTable[30][COMMA] = new ActionElement(ActionElement.REDUCE,18);

		actionTable[31][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,35);

		actionTable[32][RBRACE] = new ActionElement(ActionElement.SHIFT,36);
		actionTable[32][INT] = new ActionElement(ActionElement.SHIFT,20);
		actionTable[32][CHAR] = new ActionElement(ActionElement.SHIFT,21);
		actionTable[32][BOOLEAN] = new ActionElement(ActionElement.SHIFT,22);
		actionTable[32][IF] = new ActionElement(ActionElement.SHIFT,46);
		actionTable[32][WHILE] = new ActionElement(ActionElement.SHIFT,47);
		actionTable[32][RETURN] = new ActionElement(ActionElement.SHIFT,48);
		actionTable[32][SEMICOLON] = new ActionElement(ActionElement.SHIFT,49);
		actionTable[32][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,50);
		actionTable[32][LBRACE] = new ActionElement(ActionElement.SHIFT,51);

		actionTable[33][LBRACE] = new ActionElement(ActionElement.REDUCE,17);

		actionTable[34][INT] = new ActionElement(ActionElement.SHIFT,20);
		actionTable[34][CHAR] = new ActionElement(ActionElement.SHIFT,21);
		actionTable[34][BOOLEAN] = new ActionElement(ActionElement.SHIFT,22);

		actionTable[35][RPAREN] = new ActionElement(ActionElement.REDUCE,20);
		actionTable[35][COMMA] = new ActionElement(ActionElement.REDUCE,20);

		actionTable[36][RBRACE] = new ActionElement(ActionElement.REDUCE,21);
		actionTable[36][PUBLIC] = new ActionElement(ActionElement.REDUCE,21);
		actionTable[36][PRIVATE] = new ActionElement(ActionElement.REDUCE,21);

		actionTable[37][RBRACE] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][INT] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][CHAR] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][BOOLEAN] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][IF] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][WHILE] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][RETURN] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][SEMICOLON] = new ActionElement(ActionElement.REDUCE,22);
		actionTable[37][LBRACE] = new ActionElement(ActionElement.REDUCE,22);

		actionTable[38][RBRACE] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][INT] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][CHAR] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][BOOLEAN] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][IF] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][WHILE] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][RETURN] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][SEMICOLON] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][LBRACE] = new ActionElement(ActionElement.REDUCE,24);
		actionTable[38][ELSE] = new ActionElement(ActionElement.REDUCE,24);

		actionTable[39][RBRACE] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][INT] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][CHAR] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][BOOLEAN] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][IF] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][WHILE] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][RETURN] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][SEMICOLON] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][LBRACE] = new ActionElement(ActionElement.REDUCE,25);
		actionTable[39][ELSE] = new ActionElement(ActionElement.REDUCE,25);

		actionTable[40][RBRACE] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][INT] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][CHAR] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][BOOLEAN] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][IF] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][WHILE] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][RETURN] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][SEMICOLON] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][LBRACE] = new ActionElement(ActionElement.REDUCE,26);
		actionTable[40][ELSE] = new ActionElement(ActionElement.REDUCE,26);

		actionTable[41][RBRACE] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][INT] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][CHAR] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][BOOLEAN] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][IF] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][WHILE] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][RETURN] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][SEMICOLON] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][LBRACE] = new ActionElement(ActionElement.REDUCE,27);
		actionTable[41][ELSE] = new ActionElement(ActionElement.REDUCE,27);

		actionTable[42][RBRACE] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][INT] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][CHAR] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][BOOLEAN] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][IF] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][WHILE] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][RETURN] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][SEMICOLON] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][LBRACE] = new ActionElement(ActionElement.REDUCE,28);
		actionTable[42][ELSE] = new ActionElement(ActionElement.REDUCE,28);

		actionTable[43][RBRACE] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][INT] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][CHAR] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][BOOLEAN] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][IF] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][WHILE] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][RETURN] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][SEMICOLON] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][LBRACE] = new ActionElement(ActionElement.REDUCE,29);
		actionTable[43][ELSE] = new ActionElement(ActionElement.REDUCE,29);

		actionTable[44][RBRACE] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][INT] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][CHAR] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][BOOLEAN] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][IF] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][WHILE] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][RETURN] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][SEMICOLON] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][LBRACE] = new ActionElement(ActionElement.REDUCE,30);
		actionTable[44][ELSE] = new ActionElement(ActionElement.REDUCE,30);

		actionTable[45][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,54);
		
		actionTable[46][LPAREN] = new ActionElement(ActionElement.SHIFT,55);

		actionTable[47][LPAREN] = new ActionElement(ActionElement.SHIFT,56);

		actionTable[48][SEMICOLON] = new ActionElement(ActionElement.SHIFT,58);
		actionTable[48][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[48][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[48][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[48][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[48][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[48][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[48][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[48][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[48][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[49][RBRACE] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][INT] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][CHAR] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][BOOLEAN] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][IF] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][WHILE] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][RETURN] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][SEMICOLON] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][LBRACE] = new ActionElement(ActionElement.REDUCE,41);
		actionTable[49][ELSE] = new ActionElement(ActionElement.REDUCE,41);

		actionTable[50][ASSIGN] = new ActionElement(ActionElement.SHIFT,75);
		actionTable[50][DOT] = new ActionElement(ActionElement.SHIFT,77);
		actionTable[50][LPAREN] = new ActionElement(ActionElement.SHIFT,78);

		actionTable[51][RBRACE] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][INT] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][CHAR] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][BOOLEAN] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][IF] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][WHILE] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][RETURN] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][SEMICOLON] = new ActionElement(ActionElement.REDUCE,23);
		actionTable[51][LBRACE] = new ActionElement(ActionElement.REDUCE,23);

		actionTable[52][RPAREN] = new ActionElement(ActionElement.REDUCE,19);
		actionTable[52][COMMA] = new ActionElement(ActionElement.REDUCE,19);

		actionTable[53][SEMICOLON] = new ActionElement(ActionElement.SHIFT,80);
		actionTable[53][COMMA] = new ActionElement(ActionElement.SHIFT,81);

		actionTable[54][ASSIGN] = new ActionElement(ActionElement.SHIFT,82);
		actionTable[54][SEMICOLON] = new ActionElement(ActionElement.REDUCE,32);
		actionTable[54][COMMA] = new ActionElement(ActionElement.REDUCE,32);

		actionTable[55][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[55][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[55][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[55][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[55][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[55][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[55][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[55][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[55][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[56][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[56][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[56][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[56][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[56][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[56][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[56][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[56][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[56][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[57][SEMICOLON] = new ActionElement(ActionElement.SHIFT,85);
		actionTable[57][OR] = new ActionElement(ActionElement.SHIFT,86);

		actionTable[58][RBRACE] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][INT] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][CHAR] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][BOOLEAN] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][IF] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][WHILE] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][RETURN] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][SEMICOLON] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][LBRACE] = new ActionElement(ActionElement.REDUCE,40);
		actionTable[58][ELSE] = new ActionElement(ActionElement.REDUCE,40);
		
		actionTable[59][AND] = new ActionElement(ActionElement.SHIFT,87);
		actionTable[59][COMMA] = new ActionElement(ActionElement.REDUCE,46);
		actionTable[59][SEMICOLON] = new ActionElement(ActionElement.REDUCE,46);
		actionTable[59][RPAREN] = new ActionElement(ActionElement.REDUCE,46);
		actionTable[59][OR] = new ActionElement(ActionElement.REDUCE,46);
	
		actionTable[60][COMMA] = new ActionElement(ActionElement.REDUCE,48);
		actionTable[60][SEMICOLON] = new ActionElement(ActionElement.REDUCE,48);
		actionTable[60][RPAREN] = new ActionElement(ActionElement.REDUCE,48);
		actionTable[60][OR] = new ActionElement(ActionElement.REDUCE,48);
		actionTable[60][AND] = new ActionElement(ActionElement.REDUCE,48);

		actionTable[61][COMMA] = new ActionElement(ActionElement.REDUCE,50);
		actionTable[61][SEMICOLON] = new ActionElement(ActionElement.REDUCE,50);
		actionTable[61][RPAREN] = new ActionElement(ActionElement.REDUCE,50);
		actionTable[61][OR] = new ActionElement(ActionElement.REDUCE,50);
		actionTable[61][AND] = new ActionElement(ActionElement.REDUCE,50);
		actionTable[61][EQ] = new ActionElement(ActionElement.SHIFT,88);
		actionTable[61][NE] = new ActionElement(ActionElement.SHIFT,89);
		actionTable[61][GT] = new ActionElement(ActionElement.SHIFT,90);
		actionTable[61][GE] = new ActionElement(ActionElement.SHIFT,91);
		actionTable[61][LT] = new ActionElement(ActionElement.SHIFT,92);
		actionTable[61][LE] = new ActionElement(ActionElement.SHIFT,93);
		actionTable[61][MINUS] = new ActionElement(ActionElement.SHIFT,94);
		actionTable[61][PLUS] = new ActionElement(ActionElement.SHIFT,95);

		actionTable[62][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[62][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[62][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[62][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[62][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[62][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[63][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[63][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[63][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[63][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[63][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[63][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);
	
		actionTable[64][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[64][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[64][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[64][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[64][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[64][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[65][COMMA] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][SEMICOLON] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][RPAREN] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][OR] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][AND] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][EQ] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][NE] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][GT] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][GE] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][LT] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][LE] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][MINUS] = new ActionElement(ActionElement.REDUCE,60);
		actionTable[65][PLUS] = new ActionElement(ActionElement.REDUCE,60);	
		actionTable[65][PROD] = new ActionElement(ActionElement.SHIFT,99);
		actionTable[65][DIV] = new ActionElement(ActionElement.SHIFT,100);
		actionTable[65][MOD] = new ActionElement(ActionElement.SHIFT,101);

		actionTable[66][COMMA] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][SEMICOLON] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][RPAREN] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][OR] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][AND] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][EQ] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][NE] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][GT] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][GE] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][LT] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][LE] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][MINUS] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][PLUS] = new ActionElement(ActionElement.REDUCE,63);	
		actionTable[66][PROD] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][DIV] = new ActionElement(ActionElement.REDUCE,63);
		actionTable[66][MOD] = new ActionElement(ActionElement.REDUCE,63);	
		
		actionTable[67][COMMA] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][SEMICOLON] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][RPAREN] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][OR] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][AND] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][EQ] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][NE] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][GT] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][GE] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][LT] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][LE] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][MINUS] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][PLUS] = new ActionElement(ActionElement.REDUCE,67);	
		actionTable[67][PROD] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][DIV] = new ActionElement(ActionElement.REDUCE,67);
		actionTable[67][MOD] = new ActionElement(ActionElement.REDUCE,67);	
		
		actionTable[68][COMMA] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][SEMICOLON] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][RPAREN] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][OR] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][AND] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][EQ] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][NE] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][GT] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][GE] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][LT] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][LE] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][MINUS] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][PLUS] = new ActionElement(ActionElement.REDUCE,68);	
		actionTable[68][PROD] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][DIV] = new ActionElement(ActionElement.REDUCE,68);
		actionTable[68][MOD] = new ActionElement(ActionElement.REDUCE,68);	

		actionTable[69][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[69][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[69][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[69][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[69][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[69][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[69][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[69][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[69][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);
		
		actionTable[70][COMMA] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][SEMICOLON] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][RPAREN] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][OR] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][AND] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][EQ] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][NE] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][GT] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][GE] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][LT] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][LE] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][MINUS] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][PLUS] = new ActionElement(ActionElement.REDUCE,70);	
		actionTable[70][PROD] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][DIV] = new ActionElement(ActionElement.REDUCE,70);
		actionTable[70][MOD] = new ActionElement(ActionElement.REDUCE,70);	

		actionTable[71][COMMA] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][SEMICOLON] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][RPAREN] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][OR] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][AND] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][EQ] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][NE] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][GT] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][GE] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][LT] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][LE] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][MINUS] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][PLUS] = new ActionElement(ActionElement.REDUCE,71);	
		actionTable[71][PROD] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][DIV] = new ActionElement(ActionElement.REDUCE,71);
		actionTable[71][MOD] = new ActionElement(ActionElement.REDUCE,71);			
		
		actionTable[72][COMMA] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][SEMICOLON] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][RPAREN] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][OR] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][AND] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][EQ] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][NE] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][GT] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][GE] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][LT] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][LE] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][MINUS] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][PLUS] = new ActionElement(ActionElement.REDUCE,72);	
		actionTable[72][PROD] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][DIV] = new ActionElement(ActionElement.REDUCE,72);
		actionTable[72][MOD] = new ActionElement(ActionElement.REDUCE,72);
		
		actionTable[73][COMMA] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][SEMICOLON] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][RPAREN] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][OR] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][AND] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][EQ] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][NE] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][GT] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][GE] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][LT] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][LE] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][MINUS] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][PLUS] = new ActionElement(ActionElement.REDUCE,73);	
		actionTable[73][PROD] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][DIV] = new ActionElement(ActionElement.REDUCE,73);
		actionTable[73][MOD] = new ActionElement(ActionElement.REDUCE,73);
		
		actionTable[74][COMMA] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][SEMICOLON] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][RPAREN] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][OR] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][AND] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][EQ] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][NE] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][GT] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][GE] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][LT] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][LE] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][MINUS] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][PLUS] = new ActionElement(ActionElement.REDUCE,74);	
		actionTable[74][PROD] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][DIV] = new ActionElement(ActionElement.REDUCE,74);
		actionTable[74][MOD] = new ActionElement(ActionElement.REDUCE,74);	
		actionTable[74][DOT] = new ActionElement(ActionElement.SHIFT,104);	
		actionTable[74][LPAREN] = new ActionElement(ActionElement.SHIFT,78);	
		
		actionTable[75][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[75][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[75][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[75][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[75][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[75][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[75][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[75][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[75][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[76][SEMICOLON] = new ActionElement(ActionElement.SHIFT,106);

		actionTable[77][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,107);

		actionTable[78][RPAREN] = new ActionElement(ActionElement.SHIFT,108);
		actionTable[78][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[78][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[78][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[78][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[78][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[78][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[78][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[78][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[78][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[79][RBRACE] = new ActionElement(ActionElement.SHIFT,111);
		actionTable[79][INT] = new ActionElement(ActionElement.SHIFT,20);
		actionTable[79][CHAR] = new ActionElement(ActionElement.SHIFT,21);
		actionTable[79][BOOLEAN] = new ActionElement(ActionElement.SHIFT,22);
		actionTable[79][IF] = new ActionElement(ActionElement.SHIFT,46);
		actionTable[79][WHILE] = new ActionElement(ActionElement.SHIFT,47);
		actionTable[79][RETURN] = new ActionElement(ActionElement.SHIFT,48);
		actionTable[79][SEMICOLON] = new ActionElement(ActionElement.SHIFT,49);
		actionTable[79][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,50);
		actionTable[79][LBRACE] = new ActionElement(ActionElement.SHIFT,51);

		actionTable[80][RBRACE] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][INT] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][CHAR] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][BOOLEAN] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][IF] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][WHILE] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][RETURN] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][SEMICOLON] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][LBRACE] = new ActionElement(ActionElement.REDUCE,31);
		actionTable[80][ELSE] = new ActionElement(ActionElement.REDUCE,31);
		
		actionTable[81][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,112);

		actionTable[82][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[82][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[82][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[82][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[82][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[82][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[82][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[82][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[82][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[83][RPAREN] = new ActionElement(ActionElement.SHIFT,114);
		actionTable[83][OR] = new ActionElement(ActionElement.SHIFT,86);

		actionTable[84][RPAREN] = new ActionElement(ActionElement.SHIFT,115);
		actionTable[84][OR] = new ActionElement(ActionElement.SHIFT,86);
		
		actionTable[85][RBRACE] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][INT] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][CHAR] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][BOOLEAN] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][IF] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][WHILE] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][RETURN] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][SEMICOLON] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][LBRACE] = new ActionElement(ActionElement.REDUCE,39);
		actionTable[85][ELSE] = new ActionElement(ActionElement.REDUCE,39);
		
		actionTable[86][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[86][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[86][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[86][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[86][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[86][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[86][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[86][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[86][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[87][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[87][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[87][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[87][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[87][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[87][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[87][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[87][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[87][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[88][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[88][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[88][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[88][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[88][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[88][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[88][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[88][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[88][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[89][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[89][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[89][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[89][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[89][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[89][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[89][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[89][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[89][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[90][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[90][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[90][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[90][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[90][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[90][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[90][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[90][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[90][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[91][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[91][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[91][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[91][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[91][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[91][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[91][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[91][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[91][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[92][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[92][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[92][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[92][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[92][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[92][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[92][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[92][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[92][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);
		
		actionTable[93][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[93][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[93][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[93][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[93][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[93][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[93][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[93][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[93][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);
		
		actionTable[94][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[94][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[94][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[94][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[94][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[94][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[95][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[95][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[95][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[95][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[95][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[95][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[96][COMMA] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][SEMICOLON] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][RPAREN] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][OR] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][AND] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][EQ] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][NE] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][GT] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][GE] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][LT] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][LE] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][MINUS] = new ActionElement(ActionElement.REDUCE,57);
		actionTable[96][PLUS] = new ActionElement(ActionElement.REDUCE,57);	
		actionTable[96][PROD] = new ActionElement(ActionElement.SHIFT,99);
		actionTable[96][DIV] = new ActionElement(ActionElement.SHIFT,100);
		actionTable[96][MOD] = new ActionElement(ActionElement.SHIFT,101);
		
		actionTable[97][COMMA] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][SEMICOLON] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][RPAREN] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][OR] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][AND] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][EQ] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][NE] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][GT] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][GE] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][LT] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][LE] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][MINUS] = new ActionElement(ActionElement.REDUCE,58);
		actionTable[97][PLUS] = new ActionElement(ActionElement.REDUCE,58);	
		actionTable[97][PROD] = new ActionElement(ActionElement.SHIFT,99);
		actionTable[97][DIV] = new ActionElement(ActionElement.SHIFT,100);
		actionTable[97][MOD] = new ActionElement(ActionElement.SHIFT,101);
		
		actionTable[98][COMMA] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][SEMICOLON] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][RPAREN] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][OR] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][AND] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][EQ] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][NE] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][GT] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][GE] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][LT] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][LE] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][MINUS] = new ActionElement(ActionElement.REDUCE,59);
		actionTable[98][PLUS] = new ActionElement(ActionElement.REDUCE,59);	
		actionTable[98][PROD] = new ActionElement(ActionElement.SHIFT,99);
		actionTable[98][DIV] = new ActionElement(ActionElement.SHIFT,100);
		actionTable[98][MOD] = new ActionElement(ActionElement.SHIFT,101);
	
		actionTable[99][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[99][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[99][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[99][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[99][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[99][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);
		
		actionTable[100][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[100][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[100][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[100][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[100][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[100][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[101][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[101][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[101][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[101][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[101][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[101][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[102][RPAREN] = new ActionElement(ActionElement.SHIFT,129);
		actionTable[102][OR] = new ActionElement(ActionElement.SHIFT,86);

		actionTable[103][COMMA] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][SEMICOLON] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][RPAREN] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][OR] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][AND] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][EQ] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][NE] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][GT] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][GE] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][LT] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][LE] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][MINUS] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][PLUS] = new ActionElement(ActionElement.REDUCE,75);	
		actionTable[103][PROD] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][DIV] = new ActionElement(ActionElement.REDUCE,75);
		actionTable[103][MOD] = new ActionElement(ActionElement.REDUCE,75);

		actionTable[104][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,130);

		actionTable[105][SEMICOLON] = new ActionElement(ActionElement.SHIFT,131);
		actionTable[105][OR] = new ActionElement(ActionElement.SHIFT,86);
		
		actionTable[106][RBRACE] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][INT] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][CHAR] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][BOOLEAN] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][IF] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][WHILE] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][RETURN] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][SEMICOLON] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][LBRACE] = new ActionElement(ActionElement.REDUCE,43);
		actionTable[106][ELSE] = new ActionElement(ActionElement.REDUCE,43);
	
		actionTable[107][LPAREN] = new ActionElement(ActionElement.SHIFT,78);

		actionTable[108][COMMA] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][SEMICOLON] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][RPAREN] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][OR] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][AND] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][EQ] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][NE] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][GT] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][GE] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][LT] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][LE] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][MINUS] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][PLUS] = new ActionElement(ActionElement.REDUCE,77);	
		actionTable[108][PROD] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][DIV] = new ActionElement(ActionElement.REDUCE,77);
		actionTable[108][MOD] = new ActionElement(ActionElement.REDUCE,77);
		
		actionTable[109][RPAREN] = new ActionElement(ActionElement.SHIFT,133);
		actionTable[109][COMMA] = new ActionElement(ActionElement.SHIFT,134);

		actionTable[110][RPAREN] = new ActionElement(ActionElement.REDUCE,79);
		actionTable[110][COMMA] = new ActionElement(ActionElement.REDUCE,79);
		actionTable[110][OR] = new ActionElement(ActionElement.SHIFT,86);

		actionTable[111][RBRACE] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][INT] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][CHAR] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][BOOLEAN] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][IF] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][WHILE] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][RETURN] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][SEMICOLON] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][LBRACE] = new ActionElement(ActionElement.REDUCE,45);
		actionTable[111][ELSE] = new ActionElement(ActionElement.REDUCE,45);

		actionTable[112][SEMICOLON] = new ActionElement(ActionElement.REDUCE,34);
		actionTable[112][COMMA] = new ActionElement(ActionElement.REDUCE,34);
		actionTable[112][ASSIGN] = new ActionElement(ActionElement.SHIFT,135);

		actionTable[113][SEMICOLON] = new ActionElement(ActionElement.REDUCE,33);
		actionTable[113][COMMA] = new ActionElement(ActionElement.REDUCE,33);
		actionTable[113][OR] = new ActionElement(ActionElement.SHIFT,86);
		
		actionTable[114][INT] = new ActionElement(ActionElement.SHIFT,20);
		actionTable[114][CHAR] = new ActionElement(ActionElement.SHIFT,21);
		actionTable[114][BOOLEAN] = new ActionElement(ActionElement.SHIFT,22);
		actionTable[114][IF] = new ActionElement(ActionElement.SHIFT,46);
		actionTable[114][WHILE] = new ActionElement(ActionElement.SHIFT,47);
		actionTable[114][RETURN] = new ActionElement(ActionElement.SHIFT,48);
		actionTable[114][SEMICOLON] = new ActionElement(ActionElement.SHIFT,49);
		actionTable[114][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,50);
		actionTable[114][LBRACE] = new ActionElement(ActionElement.SHIFT,51);

		actionTable[115][INT] = new ActionElement(ActionElement.SHIFT,20);
		actionTable[115][CHAR] = new ActionElement(ActionElement.SHIFT,21);
		actionTable[115][BOOLEAN] = new ActionElement(ActionElement.SHIFT,22);
		actionTable[115][IF] = new ActionElement(ActionElement.SHIFT,46);
		actionTable[115][WHILE] = new ActionElement(ActionElement.SHIFT,47);
		actionTable[115][RETURN] = new ActionElement(ActionElement.SHIFT,48);
		actionTable[115][SEMICOLON] = new ActionElement(ActionElement.SHIFT,49);
		actionTable[115][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,50);
		actionTable[115][LBRACE] = new ActionElement(ActionElement.SHIFT,51);

		actionTable[116][COMMA] = new ActionElement(ActionElement.REDUCE,47);
		actionTable[116][SEMICOLON] = new ActionElement(ActionElement.REDUCE,47);
		actionTable[116][RPAREN] = new ActionElement(ActionElement.REDUCE,47);
		actionTable[116][OR] = new ActionElement(ActionElement.REDUCE,47);		
		actionTable[116][AND] = new ActionElement(ActionElement.SHIFT,87);

		actionTable[117][COMMA] = new ActionElement(ActionElement.REDUCE,49);
		actionTable[117][SEMICOLON] = new ActionElement(ActionElement.REDUCE,49);
		actionTable[117][RPAREN] = new ActionElement(ActionElement.REDUCE,49);
		actionTable[117][OR] = new ActionElement(ActionElement.REDUCE,49);		
		actionTable[117][AND] = new ActionElement(ActionElement.REDUCE,49);
		
		actionTable[118][COMMA] = new ActionElement(ActionElement.REDUCE,51);
		actionTable[118][SEMICOLON] = new ActionElement(ActionElement.REDUCE,51);
		actionTable[118][RPAREN] = new ActionElement(ActionElement.REDUCE,51);
		actionTable[118][OR] = new ActionElement(ActionElement.REDUCE,51);		
		actionTable[118][AND] = new ActionElement(ActionElement.REDUCE,51);		
		actionTable[118][MINUS] = new ActionElement(ActionElement.SHIFT,94);
		actionTable[118][PLUS] = new ActionElement(ActionElement.SHIFT,95);

		actionTable[119][COMMA] = new ActionElement(ActionElement.REDUCE,52);
		actionTable[119][SEMICOLON] = new ActionElement(ActionElement.REDUCE,52);
		actionTable[119][RPAREN] = new ActionElement(ActionElement.REDUCE,52);
		actionTable[119][OR] = new ActionElement(ActionElement.REDUCE,52);		
		actionTable[119][AND] = new ActionElement(ActionElement.REDUCE,52);		
		actionTable[119][MINUS] = new ActionElement(ActionElement.SHIFT,94);
		actionTable[119][PLUS] = new ActionElement(ActionElement.SHIFT,95);
		
		actionTable[120][COMMA] = new ActionElement(ActionElement.REDUCE,53);
		actionTable[120][SEMICOLON] = new ActionElement(ActionElement.REDUCE,53);
		actionTable[120][RPAREN] = new ActionElement(ActionElement.REDUCE,53);
		actionTable[120][OR] = new ActionElement(ActionElement.REDUCE,53);		
		actionTable[120][AND] = new ActionElement(ActionElement.REDUCE,53);		
		actionTable[120][MINUS] = new ActionElement(ActionElement.SHIFT,94);
		actionTable[120][PLUS] = new ActionElement(ActionElement.SHIFT,95);
		
		actionTable[121][COMMA] = new ActionElement(ActionElement.REDUCE,54);
		actionTable[121][SEMICOLON] = new ActionElement(ActionElement.REDUCE,54);
		actionTable[121][RPAREN] = new ActionElement(ActionElement.REDUCE,54);
		actionTable[121][OR] = new ActionElement(ActionElement.REDUCE,54);		
		actionTable[121][AND] = new ActionElement(ActionElement.REDUCE,54);		
		actionTable[121][MINUS] = new ActionElement(ActionElement.SHIFT,94);
		actionTable[121][PLUS] = new ActionElement(ActionElement.SHIFT,95);
		
		actionTable[122][COMMA] = new ActionElement(ActionElement.REDUCE,55);
		actionTable[122][SEMICOLON] = new ActionElement(ActionElement.REDUCE,55);
		actionTable[122][RPAREN] = new ActionElement(ActionElement.REDUCE,55);
		actionTable[122][OR] = new ActionElement(ActionElement.REDUCE,55);		
		actionTable[122][AND] = new ActionElement(ActionElement.REDUCE,55);		
		actionTable[122][MINUS] = new ActionElement(ActionElement.SHIFT,94);
		actionTable[122][PLUS] = new ActionElement(ActionElement.SHIFT,95);
		
		actionTable[123][COMMA] = new ActionElement(ActionElement.REDUCE,56);
		actionTable[123][SEMICOLON] = new ActionElement(ActionElement.REDUCE,56);
		actionTable[123][RPAREN] = new ActionElement(ActionElement.REDUCE,56);
		actionTable[123][OR] = new ActionElement(ActionElement.REDUCE,56);		
		actionTable[123][AND] = new ActionElement(ActionElement.REDUCE,56);		
		actionTable[123][MINUS] = new ActionElement(ActionElement.SHIFT,94);
		actionTable[123][PLUS] = new ActionElement(ActionElement.SHIFT,95);

		actionTable[124][COMMA] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][SEMICOLON] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][RPAREN] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][OR] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][AND] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][EQ] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][NE] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][GT] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][GE] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][LT] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][LE] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][MINUS] = new ActionElement(ActionElement.REDUCE,61);
		actionTable[124][PLUS] = new ActionElement(ActionElement.REDUCE,61);	
		actionTable[124][PROD] = new ActionElement(ActionElement.SHIFT,99);
		actionTable[124][DIV] = new ActionElement(ActionElement.SHIFT,100);
		actionTable[124][MOD] = new ActionElement(ActionElement.SHIFT,101);

		actionTable[125][COMMA] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][SEMICOLON] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][RPAREN] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][OR] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][AND] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][EQ] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][NE] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][GT] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][GE] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][LT] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][LE] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][MINUS] = new ActionElement(ActionElement.REDUCE,62);
		actionTable[125][PLUS] = new ActionElement(ActionElement.REDUCE,62);	
		actionTable[125][PROD] = new ActionElement(ActionElement.SHIFT,99);
		actionTable[125][DIV] = new ActionElement(ActionElement.SHIFT,100);
		actionTable[125][MOD] = new ActionElement(ActionElement.SHIFT,101);
		
		actionTable[126][COMMA] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][SEMICOLON] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][RPAREN] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][OR] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][AND] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][EQ] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][NE] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][GT] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][GE] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][LT] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][LE] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][MINUS] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][PLUS] = new ActionElement(ActionElement.REDUCE,64);	
		actionTable[126][PROD] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][DIV] = new ActionElement(ActionElement.REDUCE,64);
		actionTable[126][MOD] = new ActionElement(ActionElement.REDUCE,64);

		actionTable[127][COMMA] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][SEMICOLON] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][RPAREN] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][OR] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][AND] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][EQ] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][NE] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][GT] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][GE] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][LT] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][LE] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][MINUS] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][PLUS] = new ActionElement(ActionElement.REDUCE,65);	
		actionTable[127][PROD] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][DIV] = new ActionElement(ActionElement.REDUCE,65);
		actionTable[127][MOD] = new ActionElement(ActionElement.REDUCE,65);
		
		actionTable[128][COMMA] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][SEMICOLON] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][RPAREN] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][OR] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][AND] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][EQ] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][NE] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][GT] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][GE] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][LT] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][LE] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][MINUS] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][PLUS] = new ActionElement(ActionElement.REDUCE,66);	
		actionTable[128][PROD] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][DIV] = new ActionElement(ActionElement.REDUCE,66);
		actionTable[128][MOD] = new ActionElement(ActionElement.REDUCE,66);
		
		actionTable[129][COMMA] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][SEMICOLON] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][RPAREN] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][OR] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][AND] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][EQ] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][NE] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][GT] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][GE] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][LT] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][LE] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][MINUS] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][PLUS] = new ActionElement(ActionElement.REDUCE,69);	
		actionTable[129][PROD] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][DIV] = new ActionElement(ActionElement.REDUCE,69);
		actionTable[129][MOD] = new ActionElement(ActionElement.REDUCE,69);

		actionTable[130][LPAREN] = new ActionElement(ActionElement.SHIFT,78);

		actionTable[131][RBRACE] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][INT] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][CHAR] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][BOOLEAN] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][IF] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][WHILE] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][RETURN] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][SEMICOLON] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][LBRACE] = new ActionElement(ActionElement.REDUCE,42);
		actionTable[131][ELSE] = new ActionElement(ActionElement.REDUCE,42);

		actionTable[132][SEMICOLON] = new ActionElement(ActionElement.SHIFT,139);

		actionTable[133][COMMA] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][SEMICOLON] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][RPAREN] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][OR] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][AND] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][EQ] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][NE] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][GT] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][GE] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][LT] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][LE] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][MINUS] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][PLUS] = new ActionElement(ActionElement.REDUCE,78);	
		actionTable[133][PROD] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][DIV] = new ActionElement(ActionElement.REDUCE,78);
		actionTable[133][MOD] = new ActionElement(ActionElement.REDUCE,78);

		actionTable[134][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[134][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[134][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[134][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[134][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[134][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[134][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[134][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[134][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);
		
		actionTable[135][NOT] = new ActionElement(ActionElement.SHIFT,62);
		actionTable[135][MINUS] = new ActionElement(ActionElement.SHIFT,63);
		actionTable[135][PLUS] = new ActionElement(ActionElement.SHIFT,64);
		actionTable[135][LPAREN] = new ActionElement(ActionElement.SHIFT,69);
		actionTable[135][INTEGER_LITERAL] = new ActionElement(ActionElement.SHIFT,70);
		actionTable[135][CHAR_LITERAL] = new ActionElement(ActionElement.SHIFT,71);
		actionTable[135][TRUE] = new ActionElement(ActionElement.SHIFT,72);
		actionTable[135][FALSE] = new ActionElement(ActionElement.SHIFT,73);
		actionTable[135][IDENTIFIER] = new ActionElement(ActionElement.SHIFT,74);

		actionTable[136][RBRACE] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][INT] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][CHAR] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][BOOLEAN] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][IF] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][WHILE] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][RETURN] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][SEMICOLON] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][LBRACE] = new ActionElement(ActionElement.REDUCE,36);
		actionTable[136][ELSE] = new ActionElement(ActionElement.SHIFT,142);
		
		actionTable[137][RBRACE] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][INT] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][CHAR] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][BOOLEAN] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][IF] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][WHILE] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][RETURN] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][SEMICOLON] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][LBRACE] = new ActionElement(ActionElement.REDUCE,38);
		actionTable[137][ELSE] = new ActionElement(ActionElement.REDUCE,38);
		
		actionTable[138][COMMA] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][SEMICOLON] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][RPAREN] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][OR] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][AND] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][EQ] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][NE] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][GT] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][GE] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][LT] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][LE] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][MINUS] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][PLUS] = new ActionElement(ActionElement.REDUCE,76);	
		actionTable[138][PROD] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][DIV] = new ActionElement(ActionElement.REDUCE,76);
		actionTable[138][MOD] = new ActionElement(ActionElement.REDUCE,76);

		actionTable[139][RBRACE] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][INT] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][CHAR] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][BOOLEAN] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][IF] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][WHILE] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][RETURN] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][SEMICOLON] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][LBRACE] = new ActionElement(ActionElement.REDUCE,44);
		actionTable[139][ELSE] = new ActionElement(ActionElement.REDUCE,44);

		actionTable[140][RPAREN] = new ActionElement(ActionElement.REDUCE,80);
		actionTable[140][COMMA] = new ActionElement(ActionElement.REDUCE,80);
		actionTable[140][OR] = new ActionElement(ActionElement.SHIFT,86);

		actionTable[141][SEMICOLON] = new ActionElement(ActionElement.REDUCE,35);
		actionTable[141][COMMA] = new ActionElement(ActionElement.REDUCE,35);
		actionTable[141][OR] = new ActionElement(ActionElement.SHIFT,86);

		actionTable[142][RBRACE] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][INT] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][CHAR] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][BOOLEAN] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][IDENTIFIER] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][IF] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][WHILE] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][RETURN] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][SEMICOLON] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][LBRACE] = new ActionElement(ActionElement.REDUCE,37);
		actionTable[142][ELSE] = new ActionElement(ActionElement.REDUCE,37);
	}
	
	private void initGotoTable() {
		gotoTable = new int[143][33];  // 143 estados, 33 símbolos no terminales
		
		gotoTable[0][S_COMPILATION_UNIT] = 1;
		gotoTable[0][S_IMPORT_CLAUSE_LIST] = 2;
		
		gotoTable[2][S_LIBRARY_DECL] = 3;
		gotoTable[2][S_IMPORT_CLAUSE] = 4;

		gotoTable[10][S_FUNCTION_LIST] = 11;

		gotoTable[11][S_FUNCTION_DECL] = 13;
		gotoTable[11][S_ACCESS] = 14;

		gotoTable[14][S_FUNCTION_TYPE] = 17;
		gotoTable[14][S_TYPE] = 18;


		gotoTable[23][S_ARGUMENT_DECL] = 24;

		gotoTable[24][S_FUNCTION_BODY] = 26;

		gotoTable[25][S_ARGUMENT_LIST] = 29;
		gotoTable[25][S_ARGUMENT] = 30;
		gotoTable[25][S_TYPE] = 31;

		gotoTable[27][S_STATEMENT_LIST] = 32;

		gotoTable[32][S_STATEMENT] = 37;
		gotoTable[32][S_DECL] = 38;
		gotoTable[32][S_ID_STM] = 39;
		gotoTable[32][S_IF_STM] = 40;
		gotoTable[32][S_WHILE_STM] = 41;
		gotoTable[32][S_RETURN_STM] = 42;
		gotoTable[32][S_NO_STM] = 43;
		gotoTable[32][S_BLOCK_STM] = 44;
		gotoTable[32][S_TYPE] = 45;

		gotoTable[34][S_ARGUMENT] = 52;
		gotoTable[34][S_TYPE] = 31;

		gotoTable[45][S_ID_LIST] = 53;

		gotoTable[48][S_EXPR] = 57;
		gotoTable[48][S_AND_EXPR] = 59;
		gotoTable[48][S_REL_EXPR] = 60;
		gotoTable[48][S_SUM_EXPR] = 61;
		gotoTable[48][S_PROD_EXPR] = 65;
		gotoTable[48][S_FACTOR] = 66;
		gotoTable[48][S_LITERAL] = 67;
		gotoTable[48][S_REFERENCE] = 68;

		gotoTable[50][S_FUNCTION_CALL] = 76;

		gotoTable[51][S_STATEMENT_LIST] = 79;

		gotoTable[55][S_EXPR] = 83;
		gotoTable[55][S_AND_EXPR] = 59;
		gotoTable[55][S_REL_EXPR] = 60;
		gotoTable[55][S_SUM_EXPR] = 61;
		gotoTable[55][S_PROD_EXPR] = 65;
		gotoTable[55][S_FACTOR] = 66;
		gotoTable[55][S_LITERAL] = 67;
		gotoTable[55][S_REFERENCE] = 68;

		gotoTable[56][S_EXPR] = 84;
		gotoTable[56][S_AND_EXPR] = 59;
		gotoTable[56][S_REL_EXPR] = 60;
		gotoTable[56][S_SUM_EXPR] = 61;
		gotoTable[56][S_PROD_EXPR] = 65;
		gotoTable[56][S_FACTOR] = 66;
		gotoTable[56][S_LITERAL] = 67;
		gotoTable[56][S_REFERENCE] = 68;

		gotoTable[62][S_PROD_EXPR] = 96;
		gotoTable[62][S_FACTOR] = 66;
		gotoTable[62][S_LITERAL] = 67;
		gotoTable[62][S_REFERENCE] = 68;

		gotoTable[63][S_PROD_EXPR] = 97;
		gotoTable[63][S_FACTOR] = 66;
		gotoTable[63][S_LITERAL] = 67;
		gotoTable[63][S_REFERENCE] = 68;
		
		gotoTable[64][S_PROD_EXPR] = 98;
		gotoTable[64][S_FACTOR] = 66;
		gotoTable[64][S_LITERAL] = 67;
		gotoTable[64][S_REFERENCE] = 68;
		
		gotoTable[69][S_EXPR] = 102;
		gotoTable[69][S_AND_EXPR] = 59;
		gotoTable[69][S_REL_EXPR] = 60;
		gotoTable[69][S_SUM_EXPR] = 61;
		gotoTable[69][S_PROD_EXPR] = 65;
		gotoTable[69][S_FACTOR] = 66;
		gotoTable[69][S_LITERAL] = 67;
		gotoTable[69][S_REFERENCE] = 68;

		gotoTable[74][S_FUNCTION_CALL] = 103;

		gotoTable[75][S_EXPR] = 105;
		gotoTable[75][S_AND_EXPR] = 59;
		gotoTable[75][S_REL_EXPR] = 60;
		gotoTable[75][S_SUM_EXPR] = 61;
		gotoTable[75][S_PROD_EXPR] = 65;
		gotoTable[75][S_FACTOR] = 66;
		gotoTable[75][S_LITERAL] = 67;
		gotoTable[75][S_REFERENCE] = 68;

		gotoTable[78][S_EXPR_LIST] = 109;
		gotoTable[78][S_EXPR] = 110;
		gotoTable[78][S_AND_EXPR] = 59;
		gotoTable[78][S_REL_EXPR] = 60;
		gotoTable[78][S_SUM_EXPR] = 61;
		gotoTable[78][S_PROD_EXPR] = 65;
		gotoTable[78][S_FACTOR] = 66;
		gotoTable[78][S_LITERAL] = 67;
		gotoTable[78][S_REFERENCE] = 68;
		
		gotoTable[79][S_STATEMENT] = 37;
		gotoTable[79][S_DECL] = 38;
		gotoTable[79][S_ID_STM] = 39;
		gotoTable[79][S_IF_STM] = 40;
		gotoTable[79][S_WHILE_STM] = 41;
		gotoTable[79][S_RETURN_STM] = 42;
		gotoTable[79][S_NO_STM] = 43;
		gotoTable[79][S_BLOCK_STM] = 44;
		gotoTable[79][S_TYPE] = 45;

		gotoTable[82][S_EXPR] = 113;
		gotoTable[82][S_AND_EXPR] = 59;
		gotoTable[82][S_REL_EXPR] = 60;
		gotoTable[82][S_SUM_EXPR] = 61;
		gotoTable[82][S_PROD_EXPR] = 65;
		gotoTable[82][S_FACTOR] = 66;
		gotoTable[82][S_LITERAL] = 67;
		gotoTable[82][S_REFERENCE] = 68;

		gotoTable[86][S_AND_EXPR] = 116;
		gotoTable[86][S_REL_EXPR] = 60;
		gotoTable[86][S_SUM_EXPR] = 61;
		gotoTable[86][S_PROD_EXPR] = 65;
		gotoTable[86][S_FACTOR] = 66;
		gotoTable[86][S_LITERAL] = 67;
		gotoTable[86][S_REFERENCE] = 68;
		
		gotoTable[87][S_REL_EXPR] = 117;
		gotoTable[87][S_SUM_EXPR] = 61;
		gotoTable[87][S_PROD_EXPR] = 65;
		gotoTable[87][S_FACTOR] = 66;
		gotoTable[87][S_LITERAL] = 67;
		gotoTable[87][S_REFERENCE] = 68;
		
		gotoTable[88][S_SUM_EXPR] = 118;
		gotoTable[88][S_PROD_EXPR] = 65;
		gotoTable[88][S_FACTOR] = 66;
		gotoTable[88][S_LITERAL] = 67;
		gotoTable[88][S_REFERENCE] = 68;
		
		gotoTable[89][S_SUM_EXPR] = 119;
		gotoTable[89][S_PROD_EXPR] = 65;
		gotoTable[89][S_FACTOR] = 66;
		gotoTable[89][S_LITERAL] = 67;
		gotoTable[89][S_REFERENCE] = 68;
		
		gotoTable[90][S_SUM_EXPR] = 120;
		gotoTable[90][S_PROD_EXPR] = 65;
		gotoTable[90][S_FACTOR] = 66;
		gotoTable[90][S_LITERAL] = 67;
		gotoTable[90][S_REFERENCE] = 68;
		
		gotoTable[91][S_SUM_EXPR] = 121;
		gotoTable[91][S_PROD_EXPR] = 65;
		gotoTable[91][S_FACTOR] = 66;
		gotoTable[91][S_LITERAL] = 67;
		gotoTable[91][S_REFERENCE] = 68;
		
		gotoTable[92][S_SUM_EXPR] = 122;
		gotoTable[92][S_PROD_EXPR] = 65;
		gotoTable[92][S_FACTOR] = 66;
		gotoTable[92][S_LITERAL] = 67;
		gotoTable[92][S_REFERENCE] = 68;
		
		gotoTable[93][S_SUM_EXPR] = 123;
		gotoTable[93][S_PROD_EXPR] = 65;
		gotoTable[93][S_FACTOR] = 66;
		gotoTable[93][S_LITERAL] = 67;
		gotoTable[93][S_REFERENCE] = 68;
		
		gotoTable[94][S_PROD_EXPR] = 124;
		gotoTable[94][S_FACTOR] = 66;
		gotoTable[94][S_LITERAL] = 67;
		gotoTable[94][S_REFERENCE] = 68;
	
		gotoTable[95][S_PROD_EXPR] = 125;
		gotoTable[95][S_FACTOR] = 66;
		gotoTable[95][S_LITERAL] = 67;
		gotoTable[95][S_REFERENCE] = 68;
		
		gotoTable[99][S_FACTOR] = 126;
		gotoTable[99][S_LITERAL] = 67;
		gotoTable[99][S_REFERENCE] = 68;

		gotoTable[100][S_FACTOR] = 127;
		gotoTable[100][S_LITERAL] = 67;
		gotoTable[100][S_REFERENCE] = 68;
		
		gotoTable[101][S_FACTOR] = 128;
		gotoTable[101][S_LITERAL] = 67;
		gotoTable[101][S_REFERENCE] = 68;

		gotoTable[107][S_FUNCTION_CALL] = 132;

		gotoTable[114][S_STATEMENT] = 136;
		gotoTable[114][S_DECL] = 38;
		gotoTable[114][S_ID_STM] = 39;
		gotoTable[114][S_IF_STM] = 40;
		gotoTable[114][S_WHILE_STM] = 41;
		gotoTable[114][S_RETURN_STM] = 42;
		gotoTable[114][S_NO_STM] = 43;
		gotoTable[114][S_BLOCK_STM] = 44;
		gotoTable[114][S_TYPE] = 45;
		
		gotoTable[115][S_STATEMENT] = 137;
		gotoTable[115][S_DECL] = 38;
		gotoTable[115][S_ID_STM] = 39;
		gotoTable[115][S_IF_STM] = 40;
		gotoTable[115][S_WHILE_STM] = 41;
		gotoTable[115][S_RETURN_STM] = 42;
		gotoTable[115][S_NO_STM] = 43;
		gotoTable[115][S_BLOCK_STM] = 44;
		gotoTable[115][S_TYPE] = 45;
		
		gotoTable[130][S_FUNCTION_CALL] = 138;
		
		gotoTable[134][S_EXPR] = 140;
		gotoTable[134][S_AND_EXPR] = 59;
		gotoTable[134][S_REL_EXPR] = 60;
		gotoTable[134][S_SUM_EXPR] = 61;
		gotoTable[134][S_PROD_EXPR] = 65;
		gotoTable[134][S_FACTOR] = 66;
		gotoTable[134][S_LITERAL] = 67;
		gotoTable[134][S_REFERENCE] = 68;

		gotoTable[135][S_EXPR] = 141;
		gotoTable[135][S_AND_EXPR] = 59;
		gotoTable[135][S_REL_EXPR] = 60;
		gotoTable[135][S_SUM_EXPR] = 61;
		gotoTable[135][S_PROD_EXPR] = 65;
		gotoTable[135][S_FACTOR] = 66;
		gotoTable[135][S_LITERAL] = 67;
		gotoTable[135][S_REFERENCE] = 68;
	}
}
