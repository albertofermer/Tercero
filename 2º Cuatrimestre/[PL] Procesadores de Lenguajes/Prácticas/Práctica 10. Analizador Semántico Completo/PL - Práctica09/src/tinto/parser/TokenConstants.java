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

/**
 * Interfaz que define los códigos de las diferentes categorías léxicas
 *  
 * * @author Francisco José Moreno Velo
 *
 */
public interface TokenConstants {

	/**
	 * Final de fichero
	 */
	public int EOF = 0;
	
	//--------------------------------------------------------------//
	// Palabras clave
	//--------------------------------------------------------------//
	
	/**
	 * Palabra clave "boolean"
	 */
	public int BOOLEAN = 1;
	
	/**
	 * Palabra clave "char"
	 */
	public int CHAR = 2;
	
	/**
	 * Palabra clave "else"
	 */
	public int ELSE = 3;
	
	/**
	 * Palabra clave "false"
	 */
	public int FALSE = 4;
	
	/**
	 * Palabra clave "if"
	 */
	public int IF = 5;
	
	/**
	 * Palabra clave "import"
	 */
	public int IMPORT = 6;
	
	/**
	 * Palabra clave "int"
	 */
	public int INT = 7;
	
	/**
	 * Palabra clave "library"
	 */
	public int LIBRARY = 8;
	
	/**
	 * Palabra clave "private"
	 */
	public int PRIVATE = 9;
	
	/**
	 * Palabra clave "public"
	 */
	public int PUBLIC = 10;
	
	/**
	 * Palabra clave "return"
	 */
	public int RETURN = 11;
	
	/**
	 * Palabra clave "true"
	 */
	public int TRUE = 12;
	
	/**
	 * Palabra clave "void"
	 */
	public int VOID = 13;
	
	/**
	 * Palabra clave "while"
	 */
	public int WHILE = 14;
	
	//--------------------------------------------------------------//
	// Identificadores y literales
	//--------------------------------------------------------------//

	/**
	 * Identificador
	 */
	public int IDENTIFIER = 15;

	/**
	 * Literal de tipo int
	 */
	public int INTEGER_LITERAL = 16;
	
	/**
	 * Literal de tipo char
	 */
	public int CHAR_LITERAL = 17;
	
	//--------------------------------------------------------------//
	// Separadores
	//--------------------------------------------------------------//
	
	/**
	 * Paréntesis abierto "("
	 */
	public int LPAREN = 18;
	
	/**
	 * Paréntesis cerrado ")"
	 */
	public int RPAREN = 19;
	
	/**
	 * Llave abierta "{"
	 */
	public int LBRACE = 20;
	
	/**
	 * Llave cerrada "}"
	 */
	public int RBRACE = 21;
	
	/**
	 * Punto y coma ";"
	 */
	public int SEMICOLON = 22;
	
	/**
	 * Coma ","
	 */
	public int COMMA = 23;
	
	/**
	 * Punto "."
	 */
	public int DOT = 24;
	
	//--------------------------------------------------------------//
	// Operadores
	//--------------------------------------------------------------//

	/**
	 * Asignación "="
	 */
	public int ASSIGN = 25;

	/**
	 * Igualdad "=="
	 */
	public int EQ = 26;
	
	/**
	 * Menor "<"
	 */
	public int LT = 27;
	
	/**
	 * Menor o igual "<="
	 */
	public int LE = 28;
	
	/**
	 * Mayor ">"
	 */
	public int GT = 29;
	
	/**
	 * Mayor o igual ">="
	 */
	public int GE = 30;
	
	/**
	 * Distinto "!="
	 */
	public int NE = 31;
	
	/**
	 * Disyunción "||"
	 */
	public int OR = 32;
	
	/**
	 * Conjunción "&&"
	 */
	public int AND = 33;
	
	/**
	 * Negación "!"
	 */
	public int NOT = 34;
	
	/**
	 * Suma "+"
	 */
	public int PLUS = 35;
	
	/**
	 * Resta "-"
	 */
	public int MINUS = 36;
	
	/**
	 * Producto "*"
	 */
	public int PROD = 37;
	
	/**
	 * División "/"
	 */
	public int DIV = 38;
	
	/**
	 * Módulo "%"
	 */
	public int MOD = 39;
}
