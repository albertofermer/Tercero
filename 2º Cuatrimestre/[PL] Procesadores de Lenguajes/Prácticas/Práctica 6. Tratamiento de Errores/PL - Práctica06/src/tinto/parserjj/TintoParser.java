/* Generated By:JavaCC: Do not edit this line. TintoParser.java */
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

public class TintoParser implements TintoParserConstants {

        //----------------------------------------------------------------//
        //                        Miembros privados                       //
        //----------------------------------------------------------------//

        /**
	 * Contador de errores
	 */
        private int errorCount;

        /**
	 * Mensaje de errores
	 */
        private String errorMsg;

        //----------------------------------------------------------------//
        //       M�todos relacionados con el tratamiento de errores       //
        //----------------------------------------------------------------//

        /**
	 * Obtiene el n�mero de errores del an�lisis
	 * @return
	 */
        public int getErrorCount()
        {
                return this.errorCount;
        }

        /**
	 * Obtiene el mensaje de error del an�lisis
	 * @return
	 */
        public String getErrorMsg()
        {
                return this.errorMsg;
        }

        /**
	 * Almacena un error de an�lisis
	 * @param ex
	 */
        private void catchError(Exception ex)
        {
                ex.printStackTrace();
                this.errorCount++;
                this.errorMsg += ex.toString();
        }

/**************************************************************/
/*         EL AN�LISIS DE LA CABECERA COMIENZA AQU�           */
/**************************************************************/

/**
 * Reconoce el contenido completo de un archivo ".tinto"
 *
 * CompilationUnit ::= (InportClause)* TintoDecl
 */
  final public void CompilationUnit() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IMPORT:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      ImportClause();
    }
    TintoDecl();
  }

/**
 * Reconoce una clausula de importaci�n
 *
 * ImportClause ::= <IMPORT> <IDENTIFIER> <SEMICOLON> 
 */
  final public void ImportClause() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { IMPORT, LIBRARY };
    try {
      jj_consume_token(IMPORT);
      jj_consume_token(IDENTIFIER);
      jj_consume_token(SEMICOLON);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una declaraci�n
 *
 * TintoDecl ::=  LibraryDecl | NativeDecl 
 */
  final public void TintoDecl() throws ParseException {
  int[] lsync = { };
  int[] rsync = { };
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LIBRARY:
        LibraryDecl();
        break;
      case NATIVE:
        NativeDecl();
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la declaraci�n de una biblioteca
 *
 * LibraryDecl ::=  <LIBRARY> <IDENTIFIER> <LBRACE> ( FunctionDecl )* <RBRACE> 
 */
  final public void LibraryDecl() throws ParseException {
  int[] lsync = { };
  int[] rsync = { };
    try {
      jj_consume_token(LIBRARY);
      jj_consume_token(IDENTIFIER);
      jj_consume_token(LBRACE);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PRIVATE:
        case PUBLIC:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_2;
        }
        FunctionDecl();
      }
      jj_consume_token(RBRACE);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la declaraci�n de una funci�n
 *
 * FunctionDecl ::= Access FunctionType <IDENTIFIER> ArgumentDecl FunctionBody
 */
  final public void FunctionDecl() throws ParseException {
  int[] lsync = { };
  int[] rsync = { PUBLIC, PRIVATE };
    try {
      Access();
      FunctionType();
      jj_consume_token(IDENTIFIER);
      ArgumentDecl();
      FunctionBody();
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la declaraci�n de una biblioteca nativa
 *
 * NativeDecl ::=  <NATIVE> <IDENTIFIER> <LBRACE> ( NativeFunctionDecl )* <RBRACE> 
 */
  final public void NativeDecl() throws ParseException {
  int[] lsync = { };
  int[] rsync = { };
    try {
      jj_consume_token(NATIVE);
      jj_consume_token(IDENTIFIER);
      jj_consume_token(LBRACE);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PRIVATE:
        case PUBLIC:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_3;
        }
        NativeFunctionDecl();
      }
      jj_consume_token(RBRACE);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la declaraci�n de una funci�n nativa
 *
 * NativeFunctionDecl ::= Access FunctionType <IDENTIFIER> ArgumentDecl <SEMICOLON>
 */
  final public void NativeFunctionDecl() throws ParseException {
  int[] lsync = { };
  int[] rsync = { PUBLIC, PRIVATE };
    try {
      Access();
      FunctionType();
      jj_consume_token(IDENTIFIER);
      ArgumentDecl();
      jj_consume_token(SEMICOLON);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un modificador de acceso
 *
 * Access ::= <PUBLIC> | <PRIVATE>
 */
  final public void Access() throws ParseException {
  int[] lsync = { };
  int[] rsync = { VOID, INT, CHAR, BOOLEAN };
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PUBLIC:
        jj_consume_token(PUBLIC);
        break;
      case PRIVATE:
        jj_consume_token(PRIVATE);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce el tipo de datos que devuelve una funci�n
 *
 * FunctionType ::= Type | <VOID>
 */
  final public void FunctionType() throws ParseException {
  int[] lsync = { };
  int[] rsync = { IDENTIFIER };
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
      case CHAR:
      case INT:
        Type();
        break;
      case VOID:
        jj_consume_token(VOID);
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un tipo de datos b�sico
 *
 * Type ::= <INT> | <CHAR> | <BOOLEAN>
 */
  final public void Type() throws ParseException {
  int[] lsync = { };
  int[] rsync = { IDENTIFIER };
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
        jj_consume_token(INT);
        break;
      case CHAR:
        jj_consume_token(CHAR);
        break;
      case BOOLEAN:
        jj_consume_token(BOOLEAN);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la declaraci�n de argumentos de una funci�n
 *
 * ArgumentDecl ::= <LPAREN> ( Argument ( <COMMA> Argument )* )? <RPAREN>
 */
  final public void ArgumentDecl() throws ParseException {
  int[] lsync = { RPAREN };
  int[] rsync = { LBRACE };
    try {
      jj_consume_token(LPAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
      case CHAR:
      case INT:
        Argument();
        label_4:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case COMMA:
            ;
            break;
          default:
            jj_la1[7] = jj_gen;
            break label_4;
          }
          jj_consume_token(COMMA);
          Argument();
        }
        break;
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      jj_consume_token(RPAREN);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la declaraci�n de un argumento
 *
 * Argument ::= Type <IDENTIFIER>
 */
  final public void Argument() throws ParseException {
  int[] lsync = { };
  int[] rsync = { COMMA, RPAREN };
    try {
      Type();
      jj_consume_token(IDENTIFIER);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce el cuerpo de una funci�n
 *
 * FunctionBody ::= <LBRACE> ( Statement )* <RBRACE>
 */
  final public void FunctionBody() throws ParseException {
  int[] lsync = { };
  int[] rsync = { PUBLIC, PRIVATE };
    try {
      jj_consume_token(LBRACE);
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOLEAN:
        case CHAR:
        case IF:
        case INT:
        case RETURN:
        case WHILE:
        case IDENTIFIER:
        case LBRACE:
        case SEMICOLON:
          ;
          break;
        default:
          jj_la1[9] = jj_gen;
          break label_5;
        }
        Statement();
      }
      jj_consume_token(RBRACE);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una sentencia del lenguaje
 *
 * Statement ::= Decl | IdStm | IfStm | WhileStm | ReturnStm | NoStm | BlockStm
 */
  final public void Statement() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
      case CHAR:
      case INT:
        Decl();
        break;
      case IDENTIFIER:
        IdStm();
        break;
      case IF:
        IfStm();
        break;
      case WHILE:
        WhileStm();
        break;
      case RETURN:
        ReturnStm();
        break;
      case SEMICOLON:
        NoStm();
        break;
      case LBRACE:
        BlockStm();
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la declaraci�n de una variable (o una lista de variables)
 *
 * Decl ::= Type <IDENTIFIER> Assignement ( <COMMA> <IDENTIFIER> Assignement )* <SEMICOLON>
 */
  final public void Decl() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      Type();
      jj_consume_token(IDENTIFIER);
      Assignement();
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[11] = jj_gen;
          break label_6;
        }
        jj_consume_token(COMMA);
        jj_consume_token(IDENTIFIER);
        Assignement();
      }
      jj_consume_token(SEMICOLON);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce la asignaci�n inicial de una variable
 *
 * Assignement ::= ( <ASSIGN>  Expr )? 
 */
  final public void Assignement() throws ParseException {
  int[] lsync = { };
  int[] rsync = { COMMA, SEMICOLON };
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASSIGN:
        jj_consume_token(ASSIGN);
        Expr();
        break;
      default:
        jj_la1[12] = jj_gen;
        ;
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una instrucci�n "if".
 *
 * IfStm ::= <IF> <LPAREN> Expr <RPAREN> Stm  ( <ELSE> Stm )?
 */
  final public void IfStm() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      jj_consume_token(IF);
      jj_consume_token(LPAREN);
      Expr();
      jj_consume_token(RPAREN);
      Statement();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ELSE:
        jj_consume_token(ELSE);
        Statement();
        break;
      default:
        jj_la1[13] = jj_gen;
        ;
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una instrucci�n "while".
 *
 * WhileStm ::= <WHILE> <LPAREN> Expr <RPAREN> Stm
 */
  final public void WhileStm() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      jj_consume_token(WHILE);
      jj_consume_token(LPAREN);
      Expr();
      jj_consume_token(RPAREN);
      Statement();
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una instrucci�n "return".
 *
 * ReturnStm ::= <RETURN> ( Expr )? <SEMICOLON>
 */
  final public void ReturnStm() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      jj_consume_token(RETURN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case FALSE:
      case TRUE:
      case INTEGER_LITERAL:
      case CHAR_LITERAL:
      case IDENTIFIER:
      case LPAREN:
      case NOT:
      case PLUS:
      case MINUS:
      case TILDE:
        Expr();
        break;
      default:
        jj_la1[14] = jj_gen;
        ;
      }
      jj_consume_token(SEMICOLON);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una instrucci�n vac�a.
 *
 * NoStm ::= <SEMICOLON>
 */
  final public void NoStm() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      jj_consume_token(SEMICOLON);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una instrucci�n que comienza por "id"
 *
 * IdStm ::= <ID> ( Assignement | MethodCall | <DOT>  <ID>  MethodCall )  <SEMICOLON>
 */
  final public void IdStm() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      jj_consume_token(IDENTIFIER);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASSIGN:
        jj_consume_token(ASSIGN);
        Expr();
        break;
      case LPAREN:
        FunctionCall();
        break;
      case DOT:
        jj_consume_token(DOT);
        jj_consume_token(IDENTIFIER);
        FunctionCall();
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(SEMICOLON);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un bloque de instrucciones entre llaves.
 *
 * BlockStm ::= <LBRACE> ( Stm )* <RBRACE>
 */
  final public void BlockStm() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { INT, CHAR, BOOLEAN, IF, WHILE, RETURN, RBRACE};
    try {
      jj_consume_token(LBRACE);
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOLEAN:
        case CHAR:
        case IF:
        case INT:
        case RETURN:
        case WHILE:
        case IDENTIFIER:
        case LBRACE:
        case SEMICOLON:
          ;
          break;
        default:
          jj_la1[16] = jj_gen;
          break label_7;
        }
        Statement();
      }
      jj_consume_token(RBRACE);
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**************************************************************/
/*       EL AN�LISIS DE LAS EXPRESIONES COMIENZA AQU�         */
/**************************************************************/

/**
 * Reconoce una expresi�n (considerando el operador OR como el de menor prioridad)
 *
 * Expr ::= AndExpr ( <OR> AndExpr )*
 */
  final public void Expr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON, COMMA, RPAREN };
    try {
      AndExpr();
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OR:
          ;
          break;
        default:
          jj_la1[17] = jj_gen;
          break label_8;
        }
        jj_consume_token(OR);
        AndExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una expresi�n en el nivel de prioridad del operador AND
 *
 * AndExpr ::= BitOrExpr ( <AND> BitOrExpr )*
 */
  final public void AndExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON, COMMA, RPAREN, OR };
    try {
      BitOrExpr();
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
          ;
          break;
        default:
          jj_la1[18] = jj_gen;
          break label_9;
        }
        jj_consume_token(AND);
        BitOrExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

  final public void BitOrExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON };
    try {
      BitXORExpr();
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BIT_OR:
          ;
          break;
        default:
          jj_la1[19] = jj_gen;
          break label_10;
        }
        jj_consume_token(BIT_OR);
        BitXORExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

  final public void BitXORExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON };
    try {
      BitAndExpr();
      label_11:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case XOR:
          ;
          break;
        default:
          jj_la1[20] = jj_gen;
          break label_11;
        }
        jj_consume_token(XOR);
        BitAndExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

  final public void BitAndExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON };
    try {
      RelExpr();
      label_12:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BIT_AND:
          ;
          break;
        default:
          jj_la1[21] = jj_gen;
          break label_12;
        }
        jj_consume_token(BIT_AND);
        RelExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce una expresi�n en el nivel de prioridad de los comparadores
 *
 * RelExpr ::= SumExpr ( RelOp SumExpr )?
 */
  final public void RelExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND };
    try {
      ShiftExpr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQ:
      case LE:
      case GT:
      case LT:
      case GE:
      case NE:
        RelOp();
        ShiftExpr();
        break;
      default:
        jj_la1[22] = jj_gen;
        ;
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un operador de relaci�n
 *
 * RelOp ::= <EQ> | <NE> | <GT> | <GE> | <LT> | <LE>
 */
  final public void RelOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EQ:
      jj_consume_token(EQ);
      break;
    case NE:
      jj_consume_token(NE);
      break;
    case GT:
      jj_consume_token(GT);
      break;
    case GE:
      jj_consume_token(GE);
      break;
    case LT:
      jj_consume_token(LT);
      break;
    case LE:
      jj_consume_token(LE);
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void ShiftExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON };
    try {
      SumExpr();
      label_13:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case RS_SHIFT:
        case RU_SHIFT:
        case L_SHIFT:
          ;
          break;
        default:
          jj_la1[24] = jj_gen;
          break label_13;
        }
        ShiftOp();
        SumExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

  final public void ShiftOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case L_SHIFT:
      jj_consume_token(L_SHIFT);
      break;
    case RS_SHIFT:
      jj_consume_token(RS_SHIFT);
      break;
    case RU_SHIFT:
      jj_consume_token(RU_SHIFT);
      break;
    default:
      jj_la1[25] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

/**
 * Reconoce una expresi�n en el nivel de prioridad de la suma
 * (una expresi�n aritm�tica)
 *
 * SumExpr ::= UnOp ProdExpr ( SumOp ProdExpr )
 */
  final public void SumExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE };
    try {
      UnOp();
      ProdExpr();
      label_14:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
        case MINUS:
          ;
          break;
        default:
          jj_la1[26] = jj_gen;
          break label_14;
        }
        SumOp();
        ProdExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un operador unario
 *
 * UnOp ::= ( <NOT> | <MINUS>  | <PLUS>  )?
 */
  final public void UnOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
    case PLUS:
    case MINUS:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NOT:
        jj_consume_token(NOT);
        break;
      case MINUS:
        jj_consume_token(MINUS);
        break;
      case PLUS:
        jj_consume_token(PLUS);
        break;
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[28] = jj_gen;
      ;
    }
  }

/**
 * Reconoce un operador del nivel de prioridad de la suma
 *
 * SumOp ::= <MINUS> | <PLUS>
 */
  final public void SumOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
      break;
    case PLUS:
      jj_consume_token(PLUS);
      break;
    default:
      jj_la1[29] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

/**
 * Reconoce una expresi�n en el nivel de prioridad del producto
 * (un t�rmino aritm�tico)
 *
 * ProdExpr ::= Factor ( MultOp Factor )*
 */
  final public void ProdExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS };
    try {
      CompExpr();
      label_15:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PROD:
        case DIV:
        case MOD:
          ;
          break;
        default:
          jj_la1[30] = jj_gen;
          break label_15;
        }
        MultOp();
        CompExpr();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un operador del nivel de prioridad del producto
 *
 * MultOp ::= <PROD> | <DIV> | <MOD>
 */
  final public void MultOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PROD:
      jj_consume_token(PROD);
      break;
    case DIV:
      jj_consume_token(DIV);
      break;
    case MOD:
      jj_consume_token(MOD);
      break;
    default:
      jj_la1[31] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void CompExpr() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON };
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TILDE:
        jj_consume_token(TILDE);
        break;
      default:
        jj_la1[32] = jj_gen;
        ;
      }
      Factor();
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un factor
 *
 * Factor ::= Literal | Reference | <LPAREN> Expr <RPAREN>
 */
  final public void Factor() throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON, COMMA, RPAREN, OR, AND, EQ, NE, GT, GE, LT, LE, PLUS, MINUS, PROD, DIV, MOD };
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case FALSE:
      case TRUE:
      case INTEGER_LITERAL:
      case CHAR_LITERAL:
        Literal();
        break;
      case IDENTIFIER:
        Reference();
        break;
      case LPAREN:
        jj_consume_token(LPAREN);
        Expr();
        jj_consume_token(RPAREN);
        break;
      default:
        jj_la1[33] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Exception ex) {
    catchError(ex);
    skipTo(lsync,rsync);
    }
  }

/**
 * Reconoce un literal
 *
 * Literal ::= <INTEGER_LITERAL> | <CHAR_LITERAL>  | <TRUE>  | <FALSE>
 */
  final public void Literal() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
      jj_consume_token(INTEGER_LITERAL);
      break;
    case CHAR_LITERAL:
      jj_consume_token(CHAR_LITERAL);
      break;
    case TRUE:
      jj_consume_token(TRUE);
      break;
    case FALSE:
      jj_consume_token(FALSE);
      break;
    default:
      jj_la1[34] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

/**
 * Reconoce una referencia a una variable o a una funci�n
 *
 * Reference ::= <IDENTIFIER> ( FunctionCall | <DOT> <IDENTIFIER> FunctionCall )?
 */
  final public void Reference() throws ParseException {
    jj_consume_token(IDENTIFIER);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAREN:
    case DOT:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LPAREN:
        FunctionCall();
        break;
      case DOT:
        jj_consume_token(DOT);
        jj_consume_token(IDENTIFIER);
        FunctionCall();
        break;
      default:
        jj_la1[35] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[36] = jj_gen;
      ;
    }
  }

/**
 * Reconoce los par�metros de llamada a una funci�n
 *
 * FunctionCall ::= <LPAREN> ( Expr ( <COMMA> Expr )* )? <RPAREN>
 */
  final public void FunctionCall() throws ParseException {
    jj_consume_token(LPAREN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case FALSE:
    case TRUE:
    case INTEGER_LITERAL:
    case CHAR_LITERAL:
    case IDENTIFIER:
    case LPAREN:
    case NOT:
    case PLUS:
    case MINUS:
    case TILDE:
      Expr();
      label_16:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[37] = jj_gen;
          break label_16;
        }
        jj_consume_token(COMMA);
        Expr();
      }
      break;
    default:
      jj_la1[38] = jj_gen;
      ;
    }
    jj_consume_token(RPAREN);
  }

  void skipTo(int[] left, int[] right) throws ParseException {
  Token prev = getToken(0);
  Token next = getToken(1);
  boolean flag = false;
  if(prev.kind == EOF || next.kind == EOF) flag = true;
  for(int i=0; i<left.length; i++) if(prev.kind == left[i]) flag = true;
  for(int i=0; i<right.length; i++) if(next.kind == right[i]) flag = true;

  while(!flag)
  {
    getNextToken();
    prev = getToken(0);
    next = getToken(1);
    if(prev.kind == EOF || next.kind == EOF) flag = true;
    for(int i=0; i<left.length; i++) if(prev.kind == left[i]) flag = true;
    for(int i=0; i<right.length; i++) if(next.kind == right[i]) flag = true;
  }
  }

  /** Generated Token Manager. */
  public TintoParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[39];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x2000,0x18000,0x60000,0x60000,0x60000,0x204300,0x4300,0x0,0x4300,0x20485300,0x20485300,0x0,0x0,0x400,0x70900800,0x40000000,0x20485300,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x70900800,0x10900800,0x40000000,0x40000000,0x0,0x70900800,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8,0x0,0x5,0x5,0x8,0x20,0x0,0x11c000,0x30,0x5,0x1000,0x2000,0x400000,0x800000,0x200000,0xfc0,0xfc0,0x7000000,0x7000000,0x18000,0x1c000,0x1c000,0x18000,0xe0000,0xe0000,0x100000,0x0,0x0,0x10,0x10,0x8,0x11c000,};
   }

  /** Constructor with InputStream. */
  public TintoParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public TintoParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new TintoParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 39; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 39; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public TintoParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new TintoParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 39; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 39; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public TintoParser(TintoParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 39; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(TintoParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 39; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[59];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 39; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 59; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
