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

package tinto.ast.struct;

import java.util.Vector;

import tinto.ast.TypeSystem;
import tinto.ast.statement.BlockStatement;

/**
 * Clase que almacena la información sobre una función
 * 
 * @author Francisco José Moreno Velo
 */
public class Function  {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Identificador de la biblioteca a la que pertenece la función
	 */
	private String libname;
	
	/**
	 * Identificador de la función
	 */
	private String name;
	
	/**
	 * Modificador de acceso
	 */
	private int access;
	
	/**
	 * Tipo de dato que devuelve la función
	 */
	private int type;
	
	/**
	 * Lista de argumentos de la función
	 */
	private Variable[] argument;
	
	/**
	 * Lista de variables locales definidas en la función
	 */
	private Variable[] localVar;
	
	/**
	 * Conjunto de instrucciones del cuerpo de la función
	 */
	private BlockStatement body;
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 */
	public Function(int access, int type, String name, String libname) 
	{
		this.access = access;
		this.type = type;
		this.name = name.toString();
		this.libname = libname.toString();
		this.argument = new Variable[0];
		this.localVar = new Variable[0];
		this.body = null;
	}
	
	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el nombre de la función
	 */
	public String getName() 
	{
		return this.name;
	}
	
	/**
	 * Obtiene el modificador de acceso de la función
	 */
	public int getAccess() {
		return this.access;
	}
	
	/**
	 * Obtiene el tipo de dato que devuelve la función
	 */
	public int getType() 
	{
		return this.type;
	}
	
	/**
	 * Añade un argumento a la función
	 */
	public void addArgument(Variable var) 
	{
		Variable[] aux = new Variable[argument.length+1];
		System.arraycopy(argument,0,aux,0,argument.length);
		aux[argument.length] = var;
		argument = aux;
	}
	
	/**
	 * Introduce la lista de argumentos
	 */
	public void addArgumentList(Vector<Variable> vector) 
	{
		Variable[] varg = new Variable[vector.size()];
		vector.copyInto(varg);
		
		Variable[] aux = new Variable[argument.length + varg.length];
		System.arraycopy(argument, 0, aux, 0, argument.length);
		System.arraycopy(varg, 0, aux, argument.length, varg.length);
		this.argument = aux;
	}
	
	/**
	 * Obtiene la lista de argumentos de la función
	 */
	public Variable[] getArguments() 
	{
		return this.argument;
	}
	
	/**
	 * Obtiene la lista de tipos de argumentos
	 */	
	public int[] getArgumentTypes() 
	{
		int[] type = new int[argument.length];
		for (int i = 0; i < argument.length; i++) 
		{
			type[i] = argument[i].getType();
		}
		return type;
	}
	
	/**
	 * Asigna la lista de argumentos (sólo los tipos)
	 */
	public void setArgumentTypes(int[] argTypes) 
	{
		int size = argTypes.length;
		this.argument = new Variable[size];
		for(int i=0; i<size; i++) 
		{
			argument[i] = new Variable(argTypes[i],"arg"+i);
		}
	}
	
	/**
	 * Compara los tipos de datos con los de la función,
	 * devolviendo true si los tipos de datos coinciden
	 */
	public boolean match(String name, int[] argTypes) 
	{
		if(argTypes.length != argument.length) return false;
		if(!this.name.equals(name)) return false;
		for(int i=0; i<argument.length; i++) 
		{
			if(argument[i].getType() != argTypes[i]) return false;
		}
		return true;
	}
	
	/**
	 * Añade una declaración de variable local a la función
	 */
	public void addLocalVariable(Variable var) 
	{
		Variable[] aux = new Variable[localVar.length+1];
		System.arraycopy(localVar,0,aux,0,localVar.length);
		aux[localVar.length] = var;
		localVar = aux;
	}
	
	/**
	 * Obtiene la lista de variables locales
	 */
	public Variable[] getLocalVariables() 
	{
		return this.localVar;
	}
	
	/**
	 * Asigna el bloque de instrucciones de la función
	 */
	public void setBody(BlockStatement blockInst) 
	{
		this.body = blockInst;
	}
	
	/**
	 * Obtiene el bloque de instrucciones de la función
	 */
	public BlockStatement getBody() 
	{
		return this.body;
	}
	
	/**
	 * Obtiene el nombre de la etiqueta que identifica a la función 
	 * en el código intermedio
	 */
	public String getLabel() 
	{
		String label = libname+"."+name;
		for(int i=0; i<argument.length;i++) 
		{
			label += "."+TypeSystem.getLabel(argument[i].getType());
		}
		return label;
	}
}
