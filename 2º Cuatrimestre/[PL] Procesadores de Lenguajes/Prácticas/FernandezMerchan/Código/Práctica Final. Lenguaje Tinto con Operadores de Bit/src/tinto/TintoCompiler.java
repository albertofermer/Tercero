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
	 */
	public static void main(String[] args) 
	{
		TintoCompilerOptions options = new TintoCompilerOptions(args);
		
		SymbolTable symtab = parseHeaders(options);
		if(symtab == null) return;

		boolean result = parseBodies(options,symtab);
		if(!result) return;

		// Crea el archivo de salida
		PrintStream stream = createApplicationFile(options);
		if(stream == null) return;

		// Genera el código de cada biblioteca incluida en la tabla de símbolos
		LibraryDeclaration[] libraries = symtab.getLibraries();
		CodeGenerator generator = new CodeGenerator();
		for(int i=0; i< libraries.length; i++) 
		{
			File asmfile = options.getAsmFile(libraries[i].getName());
			if (asmfile != null)
			{
				appendFile(stream, asmfile);
			}
			else 
			{  
				createCode(options, generator, libraries[i]);
				File newAsmFile = options.getAsmFile(libraries[i].getName());
				appendFile(stream, newAsmFile);
				newAsmFile.delete();
			}
		}

	}

	/**
	 * Crea la tabla de símbolos analizando la cabecera de los archivos ".tinto"
	 */
	private static SymbolTable parseHeaders(TintoCompilerOptions options) 
	{
		SymbolTable symtab = new SymbolTable();

		LibraryDeclaration mainlib = execHeaderParser(options, "Main");
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
			LibraryDeclaration library = execHeaderParser(options, libName);
			if(library == null) return null;
			symtab.addLibrary(library);
			String[] imp = library.getImported();
			for (int i = 0; i < imp.length; i++) stack.push(imp[i]);
		}

		return symtab;
	}

	/**
	 * Analiza un fichero Tinto y extrae la información de cabecera
	 */
	private static LibraryDeclaration execHeaderParser(TintoCompilerOptions options, String filename) 
	{
		try
		{
			File file = options.getTintoFile(filename);
			FileInputStream fis = new FileInputStream(file);
			TintoHeaderParser header = new TintoHeaderParser(fis);
			LibraryDeclaration library = header.parse(filename);
			if(header.getErrorCount() >0) 
			{
				printError(options.getWorkingDir(),filename, header.getErrorCount(), header.getErrorMsg());
				return null;
			}
			return library;
		}
		catch (FileNotFoundException ex1) 
		{
			printError(options.getWorkingDir(), filename, 1, "File "+filename+".tinto doesn't exist.");
			return null;
		}
	}

	/**
	 * Completa el análisis de las funciones de cada biblioteca
	 */
	private static boolean parseBodies(TintoCompilerOptions options, SymbolTable symtab)
	{
		LibraryDeclaration[] libs = symtab.getLibraries();

		for(LibraryDeclaration lib: libs) 
		{
			if(lib.isNative()) continue;
			String libname = lib.getName();
			try 
			{
				File file = options.getTintoFile(libname);
				FileInputStream fis = new FileInputStream(file);
				TintoParser parser = new TintoParser(fis);
				parser.parse(libname, symtab);
				if (parser.getErrorCount() > 0) 
				{
					printError(options.getWorkingDir(),libname,parser.getErrorCount(), parser.getErrorMsg());
					return false;
				}
			} 
			catch(FileNotFoundException ex) 
			{	
				printError(options.getWorkingDir(), libname, 1, "File "+libname+".tinto doesn't exist.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Genera el fichero de error
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
			ex.printStackTrace();
		}
	}

	/**
	 * Genera el fichero de código intermedio asociado a la biblioteca 
	 */
	private static void createCode(TintoCompilerOptions options, CodeGenerator generator, LibraryDeclaration library)  
	{
		try
		{
			LibraryCodification codif = generator.generateLibraryCodification(library);
			
			if(options.verboseMode()) 
			{
				String libname = library.getName();
				File ticfile = new File(options.getWorkingDir(), libname+ ".tic");
				FileOutputStream ticfos = new FileOutputStream(ticfile);
				PrintStream ticstream = new PrintStream(ticfos);
				codif.print(ticstream);
				ticstream.close();
			}
		
			LibraryAssembler assembler = new LibraryAssembler(codif);			
			assembler.generateFile();
		}
		catch(Exception ex)
		{
			printError(options.getWorkingDir(), library.getName(), 1, ex.toString());
		}
	}	
	
	/**
	 * Genera la parte común del fichero de salida
	 */
	private static PrintStream createApplicationFile(TintoCompilerOptions options)
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream(new File(options.getWorkingDir(), options.getOutputName()+".s"));
			PrintStream stream = new PrintStream(fos);
			ApplicationAssembler.printCommonCode(stream);
			return stream;
		}
		catch (FileNotFoundException ex) 
		{
			printError(options.getWorkingDir(), "Main", 1, "Can't create output file");
			return null;
		}
	}
	
	/**
	 * Añade el contenido del archivo file al resultado final del proceso de
	 * compilación
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
