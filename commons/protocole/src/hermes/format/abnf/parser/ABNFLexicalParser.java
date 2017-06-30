/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.format.abnf.parser;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
class ABNFLexicalParser {
    private final static Map<Character, Integer> separators = new HashMap<>();
    private static final int END_OF_STRING = 0;

    private Reader reader;
    private int charCount;
    private int lastToken;
    private int charRead;
    private String parsedString;

    ABNFLexicalParser() {
        lastToken = TokenType.UNKNOWN;
        initSeparators();
    }
    
    private void initSeparators() {
        separators.put('[', TokenType.OPEN_BRACE);
        separators.put(']', TokenType.CLOSED_BRACE);
        separators.put('(', TokenType.OPEN_PARENTHESE);
        separators.put(')', TokenType.CLOSED_PARENTHESE);
        separators.put('|', TokenType.PIPE);
        separators.put('*', TokenType.ASTERISK);
        separators.put('%', TokenType.PERCENT);
        separators.put(' ', TokenType.SPACE);
        separators.put('=', TokenType.EQUAL);
        separators.put('"', TokenType.DOUBLE_QUOTES);
        separators.put('\r', TokenType.CR);
        separators.put('\n', TokenType.LF);
    }

    void setReader(Reader reader) throws Exception {
        if (!reader.markSupported()) {
            throw new Exception("Le flux de lecture '" + reader.getClass().getSimpleName() + "' ne convient pas pour ce parser (ex: BufferedReader)");
        }
        this.reader = reader;
        charCount = 0;
    }

    int nextToken() throws IOException, ParseException {
        charRead = reader.read();
        ++charCount;
        switch (charRead) {
            case END_OF_STRING:
                lastToken = TokenType.END_OF_READER;
                break;
            case '[':
                lastToken = TokenType.OPEN_BRACE;
                break;
            case ']':
                lastToken = TokenType.CLOSED_BRACE;
                break;
            case '(':
                lastToken = TokenType.OPEN_PARENTHESE;
                break;
            case ')':
                lastToken = TokenType.CLOSED_PARENTHESE;
                break;
            case '\r':
                lastToken = TokenType.CR;
                break;
            case '\n':
                lastToken = TokenType.LF;
                break;
            case ';':
                lastToken = TokenType.SEMICOLON;
                break;
            case '|':
                lastToken = TokenType.PIPE;
                break;
            case '*':
                lastToken = TokenType.ASTERISK;
                break;
            case '-':
                lastToken = TokenType.MINUS;
                break;
            case '%':
                lastToken = TokenType.PERCENT;
                break;
            case '=':
                lastToken = TokenType.EQUAL;
                break;
            case '"':
                parseLiteral();
                lastToken = TokenType.LITERAL;
                break;
            case ' ':
                lastToken = TokenType.SPACE;
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
                parseDigit(charRead);
                lastToken = TokenType.DIGIT;
                break;
            default:
                parseString(charRead);
                //System.out.print(parsedString + " ");
                lastToken = TokenType.KEYWORD;
                break;
        }
        //System.out.println(lastToken);
        return lastToken;
    }
    
    private void parseLiteral() throws IOException, ParseException {
        StringBuilder builder = new StringBuilder();
        int character = reader.read();
        char c = (char) character;
        do {
            if (character == END_OF_STRING) {
                throw new ParseException("Fin de flux rencontré pendant analyse d'un String", 0);
            } else {
                builder.append((char) character);
            }
            character = reader.read();
            c = (char) character;
            ++charCount;
        } while (character != '"');
        parsedString = builder.toString();
    }

    private void parseString(int firstCharRead) throws IOException, ParseException {
        StringBuilder builder = new StringBuilder();
        int character = firstCharRead;
        char c = (char) character;
        do {
            if (character == END_OF_STRING) {
                throw new ParseException("Fin de flux rencontré pendant analyse d'un String", 0);
            } else {
                builder.append((char) character);
            }
            reader.mark(2);
            character = reader.read();
            c = (char) character;
            ++charCount;
        //} while (character != ' ' && character != '-' && character != '\r');
        } while (!separators.containsKey((char)character));
        reader.reset();
        parsedString = builder.toString();
    }

    private void parseDigit(int firstDigitRead) throws IOException, ParseException {
        StringBuilder builder = new StringBuilder();
        int digit = firstDigitRead;
        do {
            if (digit == END_OF_STRING) {
                throw new ParseException("Fin de flux rencontré pendant analyse d'un Digit", 0);
            } else {
                builder.append((char) digit);
            }
            reader.mark(2);
            digit = reader.read();
        } while (Character.isDigit(digit));
        reader.reset();
        parsedString = builder.toString();
    }

    int getCharCount() {
        return charCount;
    }

    int getLastToken() {
        return lastToken;
    }

    String getParsedString() {
        return parsedString;
    }
    
    int getCharRead() {
        return charRead;
    }
}
