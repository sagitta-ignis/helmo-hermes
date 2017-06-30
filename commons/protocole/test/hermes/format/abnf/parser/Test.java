/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.format.abnf.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Test {
    private static final int CR = 100;
    private static final int LF = 101;
    
    public static void main(String[] args) {
        Test t = new Test();
        try {
            t.testSyntaxicalParser("user = 4*8(letter|digit)");
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean testLexicalParser(String input) throws Exception {
        ABNFLexicalParser lexicalParser = new ABNFLexicalParser();
        lexicalParser.setReader(new BufferedReader(new StringReader(input)));
        int token;
        do {
            token = lexicalParser.nextToken();
        } while (token != TokenType.LF);
        return true;
    }
    
    private boolean testSyntaxicalParser(String input) throws Exception {
        ABNFSyntaxicalParser syntaxicalParser = new ABNFSyntaxicalParser();
        //syntaxicalParser.getRule(ABNFSyntaxicalParser.DIGIT).setCapturable(true);
        //syntaxicalParser.getRule(ABNFSyntaxicalParser.LETTER).setCapturable(true);
        syntaxicalParser.parseFrom(input);
        return true;
    } 
}
