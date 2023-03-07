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
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//

package tinto.code;

import java.io.PrintStream;

/**
 * Clase que describe toda la información asociada a la codificación
 * de una biblioteca de funciones.
 * 
 * @author Francisco José Moreno Velo
 */
public class LibraryCodification  {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Nombre de la biblioteca
	 */
	private String name;

	/**
	 * Lista de bibliotecas importadas
	 */
	private String[] imported;

	/**
	 * Lista de funciones de la clase
	 */
	private FunctionCodification[] function;

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 */
	public LibraryCodification(String name, String[] imported, int funcount) 
	{
		this.name = name;
		this.imported = imported;
		this.function = new FunctionCodification[funcount];
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Asigna la codificación de una función en la posición indicada
	 */
	public void setFunctionCodification(int index, FunctionCodification mc) 
	{
		this.function[index] = mc;
	}

	/**
	 * Obtiene el nombre de la biblioteca
	 */
	public String getName() 
	{
		return this.name;
	}

	/**
	 * Obtiene la lista de bibliotecas importadas
	 */
	public String[] getImported() 
	{
		return this.imported;
	}

	/**
	 * Obtiene la lista de funciones codificadas
	 */
	public FunctionCodification[] getFunctionCodifications() 
	{
		return this.function;
	}

	/**
	 * Escribe el código completo de la biblioteca sobre el flujo
	 */
	public void print(PrintStream stream) 
	{
		for(int i=0; i<function.length; i++) 
		{
			stream.println("; %%%%%%%%%%%%%%%");
			stream.println("; "+function[i].getFunctionLabel());
			stream.println("; %%%%%%%%%%%%%%%");
			function[i].print(stream);
			stream.println(";");
			stream.println(";");
		}
	}
}
