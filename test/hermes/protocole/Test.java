/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.protocole;

import hermes.format.abnf.ABNF;
import hermes.format.abnf.Lexique;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Test {
     public static void main(String[] args) {
        Test t = new Test();
        if(t.testPattern()) {
            System.err.println("test pattern ok");
        }
        if(t.testLexique()) {
            System.err.println("test lexique ok");
        }
    }
     
    public boolean testPattern() {
        // ([0-8]+)*([0-8]+)\\(([a-zA-Z]+)\\)
        // HELLO\\s([a-zA-Z_0-9]){4,8}+\\s([\\p{Print}&&[^ \\t\\n\\x0B\\f\\r]]){4,8}+\\r\\n
        // ([[a-zA-Z][[0-9]]]){4,8}+
        // 
        Pattern p = Pattern.compile("HELLO ([[a-zA-Z][[0-9]]]){4,8}+ ([\\p{Print}&&[^ ]]){4,10}+\\r\\n");
        Matcher m = p.matcher("HELLO alice monpass\r\n");
        return m.matches();
    }
    
    public boolean testLexique() {
        Lexique l = new Lexique();
        // 4*8(letter|digit)
        ABNF user = l.compiler("user", "([letter[digit]]){4,8}+");
        ABNF pass = l.compiler("pass", "(passchar){4,10}+");
        ABNF message = l.compiler("message", "(character){1,500}+");
        l.ajouter(user);
        l.ajouter(pass);
        l.ajouter(message);
        
        ABNF regle = l.compiler("response", "digit (space message)? crlf");
        Pattern p = Pattern.compile(regle.getPattern());
        Matcher m = p.matcher("1\r\n");
        return m.matches();
    }
}
