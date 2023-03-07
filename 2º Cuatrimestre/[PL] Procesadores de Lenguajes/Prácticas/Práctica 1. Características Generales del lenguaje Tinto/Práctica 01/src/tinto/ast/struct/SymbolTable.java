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

import java.util.*;

/**
 * Clase que desarrolla la tabla de símbolos
 * 
 * @author Francisco José Moreno Velo
 */
public class SymbolTable {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Tabla de bibliotecas almacenadas en la tabla de símbolos
	 */
	private Hashtable<String, LibraryDeclaration> libraries;
	
	/**
	 * Biblioteca activa
	 */
	private LibraryDeclaration activeLibrary;
	
	/**
	 * Función activa
	 */
	private Function activeFunction;
	
	/**
	 * Pila de tablas hash
	 */
	private Stack<Hashtable<String, Variable>> stack;

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 */
	public SymbolTable() 
	{
		this.libraries = new Hashtable<String, LibraryDeclaration>();
		this.stack = new Stack<Hashtable<String, Variable>>();
		this.activeLibrary = null;
		this.activeFunction = null;
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Añade una biblioteca a la tabla de símbolos
	 */
	public void addLibrary(LibraryDeclaration lib) 
	{
		String libname = lib.getName();
		if(!libraries.containsKey(libname)) 
		{
			this.libraries.put(libname, lib);
		}
	}
	
	/**
	 * Obtiene la biblioteca indicada
	 */
	public LibraryDeclaration getLibrary(String libname) 
	{
		if(libraries.containsKey(libname)) return libraries.get(libname);
		return null;
	}
	
	/**
	 * Obtiene la biblioteca activa
	 */
	public LibraryDeclaration getActiveLibrary() 
	{
		return this.activeLibrary;
	}
	
	/**
	 * Asigna la biblioteca activa
	 */
	public void setActiveLibrary(String libname) 
	{
		if(libraries.containsKey(libname)) this.activeLibrary = libraries.get(libname);
		else this.activeLibrary = null;
	}
	
	/**
	 * Obtiene la lista de bibliotecas en forma de array
	 */
	public LibraryDeclaration[] getLibraries() 
	{
		Object[] objects = libraries.values().toArray();
		LibraryDeclaration[] libs = new LibraryDeclaration[objects.length];
		for(int i=0; i<objects.length; i++) libs[i] = (LibraryDeclaration) objects[i];
		return libs;
	}
	
	/**
	 * Obtiene la función activa
	 */
	public Function getActiveFunction() {
		return this.activeFunction;
	}
	
	/**
	 * Asigna la función activa
	 */
	public void setActiveFunction(String name, int[] type) 
	{
		if(activeLibrary == null) return;
		this.activeFunction = activeLibrary.getAnyFunction(name, type);
		if(this.activeFunction == null) return;
		
		// Crea el primer ámbito con los argumentos del método activo
		this.stack.clear();
		Hashtable<String,Variable> scope0 = new Hashtable<String, Variable>();
		Variable[] args = this.activeFunction.getArguments();
		for(Variable arg: args) scope0.put(arg.getName(), arg);
		this.stack.push(scope0);
	}
	
	/**
	 * Crea un nuevo ámbito de declaraciones
	 */
	public void createScope() 
	{
		this.stack.push(new Hashtable<String, Variable>());
	}

	/**
	 * Elimina un ámbito de declaraciones
	 */
	public void deleteScope() 
	{
		this.stack.pop();
	}

	/**
	 * Añade una declaración de variable en la función activa
	 */
	public void addLocalVariable(Variable var) 
	{
		if(activeFunction == null) return;
		this.activeFunction.addLocalVariable(var);
		this.stack.lastElement().put(var.getName(), var);
	}

	/**
	 * Añade un argumento a la función activa
	 */
	public void addArgument(Variable var) 
	{
		if(activeFunction == null) return;
		this.activeFunction.addArgument(var);
		this.stack.lastElement().put(var.getName(), var);
	}
	
	/**
	 * Obtiene una declaración de variable a partir de su identificador
	 */
	public Variable getVariable(String name) 
	{
		int size = this.stack.size();
		for (int i = size - 1; i >= 0; i--) 
		{
			Hashtable<String, Variable> table = this.stack.elementAt(i);
			if (table.containsKey(name)) return (Variable) table.get(name);
		}
		return null;
	}

	/**
	 * Obtiene una declaración de variable a partir de su identificador buscando
	 * sólo en el último ámbito.
	 */
	public Variable getVariableInScope(String name) 
	{
		Hashtable<String, Variable> table = this.stack.lastElement();
		if (table.containsKey(name))
		{
			return (Variable) table.get(name);
		}
		else
		{
			return null;
		}
	}
}
