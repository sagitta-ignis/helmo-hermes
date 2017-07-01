/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.format.abnf.parser;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class TokenType {
    final static int UNKNOWN = -9;
    final static int KEYWORD = -4;
    final static int DIGIT = -3;
    final static int RULE = -2;
    final static int LITERAL = -1;
    final static int END_OF_READER = 0;
    final static int OPEN_BRACE = 1;
    final static int CLOSED_BRACE = 2;    
    final static int OPEN_PARENTHESE = 3;
    final static int CLOSED_PARENTHESE = 4;
    final static int CR = 5;
    final static int LF = 6;
    final static int SEMICOLON = 7;
    final static int PIPE = 8;
    final static int ASTERISK = 9;
    final static int MINUS = 10;
    final static int PERCENT = 11;
    final static int EQUAL = 12;
    final static int DOUBLE_QUOTES = 13;
    final static int SPACE = 14;
}
