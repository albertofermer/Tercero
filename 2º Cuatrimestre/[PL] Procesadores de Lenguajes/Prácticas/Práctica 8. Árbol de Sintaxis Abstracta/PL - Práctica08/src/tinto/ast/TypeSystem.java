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

package tinto.ast;

/**
 * Clase que describe el sistema de tipos
 * 
 * @author Francisco José Moreno Velo
 */
public class TypeSystem {

	/**
	 * Verifica que un tipo sea entero
	 */
	public static boolean isInteger(int type) 
	{
		switch(type) 
		{
			case Type.INT_TYPE:
				return true;
			case Type.CHAR_TYPE:
			case Type.BOOLEAN_TYPE:
			case Type.VOID_TYPE:
				return false;	
			default:
				return false;
		}
	}
	
	/**
	 * Verifica que un tipo sea booleano
	 */
	public static boolean isBoolean(int type) 
	{
		if(type == Type.BOOLEAN_TYPE) return true;
		return false;
	}
	
	/**
	 * Verifica que un tipo sea carácter
	 * @param type
	 * @return
	 */
	public static boolean isChar(int type) 
	{
		if(type == Type.CHAR_TYPE) return true;
		return false;
	}
	
	/**
	 * Verifica que un tipo sea void
	 * @param type
	 * @return
	 */
	public static boolean isVoid(int type) 
	{
		if(type == Type.VOID_TYPE) return true;
		return false;
	}
	
	/**
	 * Verifica que un tipo sea numérico
	 */
	public static boolean isNumeric(int type) 
	{
		switch(type) 
		{
			case Type.INT_TYPE:
				return true;
			case Type.CHAR_TYPE:
			case Type.BOOLEAN_TYPE:
			case Type.VOID_TYPE:
				return false;
			default:
				return false;
		}
	}

	/**
	 * Verifica que dos tipos se pueden ordenar con '>' o '<'
	 */
	public static boolean isOrderable(int type1, int type2) 
	{
		if(isNumeric(type1) && isNumeric(type2)) return true;
		if(isChar(type1) && isChar(type2)) return true;
		return false;
	}
	
	/**
	 * Estudia si dos tipos de datos pueden compararse con
	 * los operadores '=' o '!='
	 * @param type1
	 * @param type2
	 * @return
	 */
	public static boolean isComparable(int type1, int type2) 
	{
		if(isNumeric(type1) && isNumeric(type2)) return true;
		if(isBoolean(type1) && isBoolean(type2)) return true;
		if(isChar(type1) && isChar(type2)) return true;
		return false;
	}
	
	/**
	 * Verifica que un tipo se represente en un registro de
	 * propósito general de 32 bits
	 * @param type
	 * @return
	 */
	public static boolean isRegularWord(int type) 
	{
		switch(type) 
		{
			case Type.INT_TYPE:
			case Type.CHAR_TYPE:	
			case Type.BOOLEAN_TYPE:
			case Type.VOID_TYPE:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Obtiene el tamaño del tipo de datos (en bytes)
	 * @param type
	 * @return
	 */
	public static int getSize(int type) 
	{
		switch(type) 
		{
			case Type.INT_TYPE:
			case Type.CHAR_TYPE:
			case Type.BOOLEAN_TYPE:
				return 4;
			case Type.VOID_TYPE:	
				return 0;
			default:
				return 0;
		}
	}
}
