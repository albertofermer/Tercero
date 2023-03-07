/* Generated By:JavaCC: Do not edit this line. TintoParserConstants.java */
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
//          Departamento de Tecnolog�as de la Informaci�n           //
//   �rea de Ciencias de la Computaci�n e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//


package tinto.parserjj;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface TintoParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 6;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 7;
  /** RegularExpression Id. */
  int BOOLEAN = 8;
  /** RegularExpression Id. */
  int CHAR = 9;
  /** RegularExpression Id. */
  int ELSE = 10;
  /** RegularExpression Id. */
  int FALSE = 11;
  /** RegularExpression Id. */
  int IF = 12;
  /** RegularExpression Id. */
  int IMPORT = 13;
  /** RegularExpression Id. */
  int INT = 14;
  /** RegularExpression Id. */
  int LIBRARY = 15;
  /** RegularExpression Id. */
  int PRIVATE = 16;
  /** RegularExpression Id. */
  int PUBLIC = 17;
  /** RegularExpression Id. */
  int RETURN = 18;
  /** RegularExpression Id. */
  int TRUE = 19;
  /** RegularExpression Id. */
  int VOID = 20;
  /** RegularExpression Id. */
  int WHILE = 21;
  /** RegularExpression Id. */
  int INTEGER_LITERAL = 22;
  /** RegularExpression Id. */
  int DECIMAL_LITERAL = 23;
  /** RegularExpression Id. */
  int HEX_LITERAL = 24;
  /** RegularExpression Id. */
  int OCTAL_LITERAL = 25;
  /** RegularExpression Id. */
  int BINARY_LITERAL = 26;
  /** RegularExpression Id. */
  int CHAR_LITERAL = 27;
  /** RegularExpression Id. */
  int IDENTIFIER = 28;
  /** RegularExpression Id. */
  int LPAREN = 29;
  /** RegularExpression Id. */
  int RPAREN = 30;
  /** RegularExpression Id. */
  int LBRACE = 31;
  /** RegularExpression Id. */
  int RBRACE = 32;
  /** RegularExpression Id. */
  int SEMICOLON = 33;
  /** RegularExpression Id. */
  int COMMA = 34;
  /** RegularExpression Id. */
  int DOT = 35;
  /** RegularExpression Id. */
  int ASSIGN = 36;
  /** RegularExpression Id. */
  int EQ = 37;
  /** RegularExpression Id. */
  int LE = 38;
  /** RegularExpression Id. */
  int GT = 39;
  /** RegularExpression Id. */
  int LT = 40;
  /** RegularExpression Id. */
  int GE = 41;
  /** RegularExpression Id. */
  int NE = 42;
  /** RegularExpression Id. */
  int OR = 43;
  /** RegularExpression Id. */
  int AND = 44;
  /** RegularExpression Id. */
  int NOT = 45;
  /** RegularExpression Id. */
  int PLUS = 46;
  /** RegularExpression Id. */
  int MINUS = 47;
  /** RegularExpression Id. */
  int PROD = 48;
  /** RegularExpression Id. */
  int DIV = 49;
  /** RegularExpression Id. */
  int MOD = 50;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "<SINGLE_LINE_COMMENT>",
    "<MULTI_LINE_COMMENT>",
    "\"boolean\"",
    "\"char\"",
    "\"else\"",
    "\"false\"",
    "\"if\"",
    "\"import\"",
    "\"int\"",
    "\"library\"",
    "\"private\"",
    "\"public\"",
    "\"return\"",
    "\"true\"",
    "\"void\"",
    "\"while\"",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<BINARY_LITERAL>",
    "<CHAR_LITERAL>",
    "<IDENTIFIER>",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\"==\"",
    "\"<=\"",
    "\">\"",
    "\"<\"",
    "\">=\"",
    "\"!=\"",
    "\"||\"",
    "\"&&\"",
    "\"!\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
  };

}
