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

// 

package tinto.parser;

public class TintoParser implements TintoParserConstants {

/**************************************************************/
/*         EL AN�LISIS DE LA CABECERA COMIENZA AQU�           */
/**************************************************************/

/**
 * Reconoce el contenido completo de un archivo ".tinto"
 *
 * TokenList -> (TokenElement)* 
 */
  final public void TokenList() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
      case CHAR:
      case ELSE:
      case FALSE:
      case IF:
      case IMPORT:
      case INT:
      case LIBRARY:
      case NATIVE:
      case PRIVATE:
      case PUBLIC:
      case RETURN:
      case TRUE:
      case VOID:
      case WHILE:
      case INTEGER_LITERAL:
      case CHAR_LITERAL:
      case IDENTIFIER:
      case LPAREN:
      case RPAREN:
      case LBRACE:
      case RBRACE:
      case SEMICOLON:
      case COMMA:
      case DOT:
      case ASSIGN:
      case EQ:
      case LE:
      case GT:
      case LT:
      case GE:
      case NE:
      case OR:
      case AND:
      case NOT:
      case PLUS:
      case MINUS:
      case PROD:
      case DIV:
      case MOD:
      case TILDE:
      case BIT_AND:
      case BIT_OR:
      case XOR:
      case RS_SHIFT:
      case RU_SHIFT:
      case L_SHIFT:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      TokenElement();
    }
  }

  final public void TokenElement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BOOLEAN:
      jj_consume_token(BOOLEAN);
      break;
    case CHAR:
      jj_consume_token(CHAR);
      break;
    case ELSE:
      jj_consume_token(ELSE);
      break;
    case FALSE:
      jj_consume_token(FALSE);
      break;
    case IF:
      jj_consume_token(IF);
      break;
    case IMPORT:
      jj_consume_token(IMPORT);
      break;
    case INT:
      jj_consume_token(INT);
      break;
    case LIBRARY:
      jj_consume_token(LIBRARY);
      break;
    case NATIVE:
      jj_consume_token(NATIVE);
      break;
    case PRIVATE:
      jj_consume_token(PRIVATE);
      break;
    case PUBLIC:
      jj_consume_token(PUBLIC);
      break;
    case RETURN:
      jj_consume_token(RETURN);
      break;
    case TRUE:
      jj_consume_token(TRUE);
      break;
    case VOID:
      jj_consume_token(VOID);
      break;
    case WHILE:
      jj_consume_token(WHILE);
      break;
    case INTEGER_LITERAL:
      jj_consume_token(INTEGER_LITERAL);
      break;
    case CHAR_LITERAL:
      jj_consume_token(CHAR_LITERAL);
      break;
    case IDENTIFIER:
      jj_consume_token(IDENTIFIER);
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      break;
    case RPAREN:
      jj_consume_token(RPAREN);
      break;
    case LBRACE:
      jj_consume_token(LBRACE);
      break;
    case RBRACE:
      jj_consume_token(RBRACE);
      break;
    case SEMICOLON:
      jj_consume_token(SEMICOLON);
      break;
    case COMMA:
      jj_consume_token(COMMA);
      break;
    case DOT:
      jj_consume_token(DOT);
      break;
    case ASSIGN:
      jj_consume_token(ASSIGN);
      break;
    case EQ:
      jj_consume_token(EQ);
      break;
    case LE:
      jj_consume_token(LE);
      break;
    case GT:
      jj_consume_token(GT);
      break;
    case LT:
      jj_consume_token(LT);
      break;
    case GE:
      jj_consume_token(GE);
      break;
    case NE:
      jj_consume_token(NE);
      break;
    case OR:
      jj_consume_token(OR);
      break;
    case AND:
      jj_consume_token(AND);
      break;
    case NOT:
      jj_consume_token(NOT);
      break;
    case PLUS:
      jj_consume_token(PLUS);
      break;
    case MINUS:
      jj_consume_token(MINUS);
      break;
    case PROD:
      jj_consume_token(PROD);
      break;
    case DIV:
      jj_consume_token(DIV);
      break;
    case MOD:
      jj_consume_token(MOD);
      break;
    case TILDE:
      jj_consume_token(TILDE);
      break;
    case BIT_AND:
      jj_consume_token(BIT_AND);
      break;
    case BIT_OR:
      jj_consume_token(BIT_OR);
      break;
    case XOR:
      jj_consume_token(XOR);
      break;
    case RS_SHIFT:
      jj_consume_token(RS_SHIFT);
      break;
    case RU_SHIFT:
      jj_consume_token(RU_SHIFT);
      break;
    case L_SHIFT:
      jj_consume_token(L_SHIFT);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
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
  final private int[] jj_la1 = new int[2];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xf0ffff00,0xf0ffff00,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x7ffffff,0x7ffffff,};
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
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public TintoParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new TintoParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public TintoParser(TintoParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(TintoParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 2; i++) {
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
