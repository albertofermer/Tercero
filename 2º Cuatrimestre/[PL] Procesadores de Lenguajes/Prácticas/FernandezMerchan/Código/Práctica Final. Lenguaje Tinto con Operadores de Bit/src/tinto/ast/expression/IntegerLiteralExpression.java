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

package tinto.ast.expression;

import tinto.ast.Type;

/**
 * Clase que describe una constante de tipo entero
 * 
 * @author Francisco José Moreno Velo
 */
public class IntegerLiteralExpression extends LiteralExpression {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Valor del literal
	 */
	private int value;
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor basado en el lexema
	 */
	public IntegerLiteralExpression(String lexeme) 
	{
		super(Type.INT_TYPE);
    	if(lexeme.startsWith("0x") || lexeme.startsWith("0X")) this.value = parseHexInt(lexeme.substring(2));
    	else if(lexeme.startsWith("0b") || lexeme.startsWith("0B")) this.value = parseBinaryInt(lexeme.substring(2));
    	else if(lexeme.startsWith("0")) this.value = Integer.parseInt(lexeme,8);
    	else this.value = Integer.parseInt(lexeme);
	}
	
	/**
	 * Contrutor basado en el valor
	 */
	public IntegerLiteralExpression(int value) 
	{
		super(Type.INT_TYPE);
		this.value = value;
	}
		
	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el valor del literal int
	 */
	public int getValue() 
	{
		return this.value;
	}

	/**
	 * Obtiene el valor int a partir de la descripción hexadecimal
	 * evitando el error del signo.
	 */
	public static int parseHexInt(String hex) 
	{
		if(hex.length() !=8 ) return Integer.parseInt(hex,16);
		char first = hex.charAt(0);
		if(first >= '0' && first <= '7') return Integer.parseInt(hex,16);
		int hex7 = Integer.parseInt(hex.substring(1),16);
		int hex8 = Integer.parseInt(""+first,16);
		return (hex8<<28 | hex7);
	}
	
	/**
	 * Obtiene el valor int a partir de la descripción binaria
	 * evitando el error del signo.
	 */
	public static int parseBinaryInt(String bin) 
	{
		if(bin.length() !=32 ) return Integer.parseInt(bin,2);
		char first = bin.charAt(0);
		if(first == '0') return Integer.parseInt(bin,2);
		int bin31 = Integer.parseInt(bin.substring(1),2);
		return (1<<31 | bin31);
	}
}
