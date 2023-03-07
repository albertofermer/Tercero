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


package tinto;

import java.io.*;
import java.util.Vector;


/**
 * Clase que almacenas las opciones de compilación
 * 
 * @author Francisco José Moreno Velo
 * 
 */
public class TintoCompilerOptions {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//
	
	/**
	 * Directorio de trabajo
	 */
	private File workingdir;
	
	/**
	 * Directorios de bibliotecas importadas
	 */
	private Vector<File> includeDir;
	
	/**
	 * Flag para generar el código intermedio en modo texto
	 */
	private boolean verbose;
	
	/**
	 * Nombre del fichero de salida
	 */
	private String output;
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//
	
	/**
	 * Constructor
	 */
	public TintoCompilerOptions(String[] args)
	{
		this.workingdir = new File(System.getProperty("user.dir"));
		this.includeDir = new Vector<File>();
		this.verbose = false;
		this.output = "Application";
		
		for(int i=0; i<args.length; i++)
		{
			if(args[i].equals("-I") && i+1<args.length) { this.includeDir.addElement(new File(args[i+1])); i++; }
			else if(args[i].equals("-o") && i+1<args.length) { this.output = args[i+1]; i++; }
			else if(args[i].equals("-v")) this.verbose = true;
			else if(!args[i].startsWith("-")) this.workingdir = new File(args[i]);
		}
		
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//
	
	/**
	 * Obtiene el directorio de trabajo
	 */
	public File getWorkingDir()
	{
		return this.workingdir;
	}
	
	/**
	 * Añade un directorio a la lista de directorios de inclusión
	 */
	public void addIncludeDir(File f)
	{
		this.includeDir.addElement(f);
	}
	
	/**
	 * Busca un fichero ".tinto" en todos los directorios 
	 */
	public File getTintoFile(String name) throws FileNotFoundException
	{
		File file;
		
		file = new File(this.workingdir,name+".tinto");
		if(file.exists()) return file;
		
		for(int i=0; i<includeDir.size(); i++)
		{
			File dir = includeDir.elementAt(i);
			file = new File(dir,name+".tinto");
			if(file.exists()) return file;			
		}
		
		throw new FileNotFoundException();
	}
	
	/**
	 * Busca un fichero ".s" en todos los directorios 
	 */
	public File getAsmFile(String name)
	{
		File file;
		
		file = new File(this.workingdir,name+".s");
		if(file.exists()) return file;
		
		for(int i=0; i<includeDir.size(); i++)
		{
			File dir = includeDir.elementAt(i);
			file = new File(dir,name+".s");
			if(file.exists()) return file;			
		}
		
		return null;
	}
	
	/**
	 * Indica si debe generar el código intermedio en modo texto
	 */
	public boolean verboseMode()
	{
		return this.verbose;
	}
	
	/**
	 * Obtiene el nombre del ficehro de salida
	 */
	public String getOutputName()
	{
		return this.output;
	}
}
