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
import java.util.Stack;
import tinto.ast.struct.*;
import tinto.parser.*;
import tinto.code.*;
import tinto.mips.*;

/**
 * Clase que desarrolla el punto de entrada al compilador.
 * 
 * @author Francisco José Moreno Velo
 * 
 */
public class TintoCompiler {

	/**
	 * Punto de entrada de la aplicación
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String path = (args.length == 0 ? System.getProperty("user.dir") : args[0]);
		File workingdir = new File(path);

		SymbolTable symtab = parseHeaders(workingdir);
		if(symtab == null) return;

		boolean result = parseBodies(workingdir,symtab);
		if(!result) return;

		// Crea el archivo 'Application.s'
		PrintStream stream = createApplicationFile(workingdir);
		if(stream == null) return;

		// Genera el código de cada biblioteca incluida en la tabla de símbolos
		LibraryDeclaration[] libraries = symtab.getLibraries();
		CodeGenerator generator = new CodeGenerator();
		for(int i=0; i< libraries.length; i++) 
		{
			File asmfile = new File(workingdir, libraries[i].getName() + ".s");
			if (asmfile.exists())
			{
				appendFile(stream, asmfile);
			}
			else 
			{  
				createCode(workingdir, generator,libraries[i]);
				appendFile(stream, asmfile); 
			}
		}

	}

	/**
	 * Crea la tabla de símbolos analizando la cabecera de los archivos ".tinto"
	 * @param mainfile
	 * @return
	 */
	private static SymbolTable parseHeaders(File workingdir) 
	{
		SymbolTable symtab = new SymbolTable();

		LibraryDeclaration mainlib = execHeaderParser(workingdir, "Main");
		if(mainlib == null) return null;
		symtab.addLibrary(mainlib);

		Stack<String> stack = new Stack<String>();
		String[] imported_name = mainlib.getImported();
		for (int i = 0; i < imported_name.length; i++) 
		{
			stack.push(imported_name[i]);
		}
		while (!stack.empty()) 
		{
			String libName = (String) stack.pop();
			if (symtab.getLibrary(libName) != null) continue;
			LibraryDeclaration library = execHeaderParser(workingdir, libName);
			if(library == null) return null;
			symtab.addLibrary(library);
			String[] imp = library.getImported();
			for (int i = 0; i < imp.length; i++) stack.push(imp[i]);
		}

		return symtab;
	}

	/**
	 * Analiza un fichero Tinto y extrae la información de cabecera
	 * @param file
	 * @return
	 */
	private static LibraryDeclaration execHeaderParser(File workingdir, String filename) 
	{
		try
		{
			File file = new File(workingdir,filename+".tinto");
			FileInputStream fis = new FileInputStream(file);
			TintoHeaderParser header = new TintoHeaderParser(fis);
			LibraryDeclaration library = header.parse(filename);
			if(header.getErrorCount() >0) 
			{
				printError(workingdir,filename, header.getErrorCount(), header.getErrorMsg());
				return null;
			}
			return library;
		}
		catch (FileNotFoundException ex1) 
		{
			printError(workingdir, filename, 1, "File "+filename+".tinto doesn't exist.");
			return null;
		}
	}

	/**
	 * Completa el análisis de las funciones de cada biblioteca
	 * @param workingdir
	 * @param symtab
	 * @return
	 */
	private static boolean parseBodies(File workingdir, SymbolTable symtab)
	{
		LibraryDeclaration[] libs = symtab.getLibraries();

		for(LibraryDeclaration lib: libs) 
		{
			String libname = lib.getName();
			File file = new File(workingdir, libname+".tinto");
			try 
			{
				FileInputStream fis = new FileInputStream(file);
				TintoParser parser = new TintoParser(fis);
				parser.parse(libname, symtab);
				if (parser.getErrorCount() > 0) 
				{
					printError(workingdir,libname,parser.getErrorCount(), parser.getErrorMsg());
					return false;
				}
			} 
			catch(FileNotFoundException ex) 
			{	
				printError(workingdir, libname, 1, "File "+libname+".tinto doesn't exist.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Genera el fichero de error
	 * @param workingdir Directorio de trabajo
	 * @param e Error a mostrar
	 */
	private static void printError(File workingdir, String filename, int count, String msg) 
	{
		try 
		{
			FileOutputStream errorfile =  new FileOutputStream(new File(workingdir, "TintocErrors.txt"));
			PrintStream errorStream = new PrintStream(errorfile);
			errorStream.println("[File "+filename+".tinto] "+count+" error"+(count>0?"s":"")+" found:");
			errorStream.println(msg);
			errorStream.close();
		}
		catch(Exception ex)
		{
		}
	}

	/**
	 * Genera el fichero de código intermedio asociado a la biblioteca 
	 */
	private static void createCode(File workingdir, CodeGenerator generator, LibraryDeclaration library)  
	{
		try
		{
			String libname = library.getName();
			LibraryCodification codif = generator.generateLibraryCodification(library);
			File tmcfile = new File(workingdir, libname+ ".tmc");
			FileOutputStream tmcfos = new FileOutputStream(tmcfile);
			PrintStream tmcstream = new PrintStream(tmcfos);
			codif.print(tmcstream);
			tmcstream.close();
			
			LibraryAssembler assembler = new LibraryAssembler(codif);
			assembler.generateFile();
		}
		catch(Exception ex)
		{
			printError(workingdir, library.getName(), 1, ex.toString());
		}
	}	
	
	/**
	 * Genera la parte común del fichero de salida
	 * @param workingdir
	 */
	private static PrintStream createApplicationFile(File workingdir)
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream(new File(workingdir, "Application.s"));
			PrintStream stream = new PrintStream(fos);
			ApplicationAssembler.printCommonCode(stream);
			return stream;
		}
		catch (FileNotFoundException ex) 
		{
			printError(workingdir, "Main", 1, "Can't create file 'Application.s'");
			return null;
		}
	}
	
	/**
	 * Añade el contenido del archivo file al resultado final del proceso de
	 * compilación
	 * 
	 * @param stream
	 * @param file
	 */
	private static void appendFile(PrintStream stream, File file) 
	{
		try 
		{
			FileInputStream fis = new FileInputStream(file);
			byte[] content = new byte[fis.available()];
			fis.read(content);
			fis.close();
			stream.println();
			stream.write(content);
		} 
		catch (Exception ex) 
		{
		}
	}
}
