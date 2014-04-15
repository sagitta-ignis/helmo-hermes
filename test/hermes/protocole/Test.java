/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.protocole;

import com.sun.org.apache.xerces.internal.xs.StringList;
import hermes.protocole.message.MessageProtocole;
import hermes.format.abnf.ABNF;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Test {
    
    public static void main(String[] args) {
        Test t = new Test();
        if (t.testPattern()) {
            System.err.println("test pattern ok");
        }
        if (t.testMessageProtocole()) {
            System.err.println("test message protocole ok");
        }
        if (t.testProtocoleSwinen()) {
            System.err.println("test protocole swinen ok");
        }
    }

    public boolean testPattern() {
        // ([0-8]+)*([0-8]+)\\(([a-zA-Z]+)\\)
        // HELLO\\s([a-zA-Z_0-9]){4,8}+\\s([\\p{Print}&&[^ \\t\\n\\x0B\\f\\r]]){4,8}+\\r\\n
        // HELLO ([[a-zA-Z][[0-9]]]){4,8}+ ([\\p{Print}&&[^ ]]){4,10}+\\r\\n
        // ([[a-zA-Z][[0-9]]]){4,8}+
        // SUSERS( ([a-zA-Z_0-9]){4,8}+)*\r\n
        // [\\x41-\\x5A\\x61-\\x7A]+
        // [[\x41-\x5A][\x61-\x7A]]
        // (?<digit>\x30-\x39)(?:\x20(?<letter>[\x41-\x5A\x61-\x7A]))?\x0D\x0A
        // (?<digit>[\x30-\x39])(?:\x20(?<letter>[[\x41-\x5A][\x61-\x7A]]))?\x0D\x0A
        // (?:[[[\x41-\x5A][\x61-\x7A]][\x30-\x39]]){4,8}?
        // [\x20-\xFF]{1,500}?
        // SUSERS(?:\x20(?<user>(?:[[a-zA-Z][0-9]]){4,8}?))+?\x0D\x0A
        // 
        Pattern p = Pattern.compile("SUSERS(?:\\x20(?<user>(?:[[a-zA-Z][0-9]]){4,8}))+\\x0D\\x0A");
        Matcher m = p.matcher("SUSERS alice bobby carl\r\n");
        //Matcher m = p.matcher("abcdefghijklmnopqrstuvwxyz"+("abcdefghijklmnopqrstuvwxyz").toUpperCase());
        Pattern p2 = Pattern.compile("((?:[[a-zA-Z][0-9]]){4,8})");
        // ((?:[[a-zA-Z][0-9]]){4,8})
        Matcher m2 = p2.matcher("SUSERS Alice bobby2 carl7\r\n");
        List<String> list = new ArrayList<>();
        String element;
        while(m2.find()) {
            element = m2.group();
            if(element != null) {
                list.add(element);
            }
        }
        return m.matches();
    }

    public boolean testMessageProtocole() {
        // création des variables des messages + ajout au lexique
        ABNF user = ABNF.compilerEtAjouter("user = 4*8(letter|digit)");
        ABNF pass = ABNF.compilerEtAjouter("pass = 4*10passchar");
        ABNF message = ABNF.compilerEtAjouter("message = 1*500character");

        // création d'un message et configuration de ses variables
        MessageProtocole mp = new MessageProtocole("susers = \"SUSERS\" *(space user) crlf", user);
        // HELLO\x20(?<user>(?:[[a-zA-Z][0-9]]){4,8}?)\x20(?<pass>[\x21-\xFF]{4,10}?)\x0D\x0A

        // affectation des variables
        String alice = "aLice01";
        String mdp = "m0npaS5;";
        List list = Arrays.asList(new String[] {"Alice", "bobby1", "carl7"});
        if (!mp.set(user, list)) return false;
        //mp.set(pass, mdp);
        //mp.set(message, "OK");
        // formattage du message
        String messageAEnvoyer = "HELLO "+alice+" "+mdp+"\r\n";
        try {
            messageAEnvoyer = mp.remplir();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        // parsage du message
        mp.scanner(messageAEnvoyer);
        boolean verifier = true;
        if (!mp.get(user).equals(alice)) {
            verifier = false;
        }
        if (!mp.get(pass).equals(mdp)) {
            verifier = false;
        }
        return verifier;
    }

    public boolean testProtocoleSwinen() {
        String alice = "aLice01";
        String mdp = "m0npaS5;";
        boolean verification = testMakeAndCheck(
                ProtocoleSwinen.HELLO, "HELLO " + alice + " " + mdp + "\r\n",
                new AbstractMap.SimpleEntry<ABNF, Object>(ProtocoleSwinen.user, alice),
                new AbstractMap.SimpleEntry<ABNF, Object>(ProtocoleSwinen.pass, mdp));
        String digit = "9";
        String message = "invalide";
        verification = verification && testMakeAndCheck(
                ProtocoleSwinen.RESPONSE, digit + "\r\n",
                new AbstractMap.SimpleEntry<ABNF, Object>(ProtocoleSwinen.digit, digit),
                new AbstractMap.SimpleEntry<ABNF, Object>(ProtocoleSwinen.message, message)
        );
        String sender = "alice";
        message = "coucou";
        verification = verification && testMakeAndCheck(
                ProtocoleSwinen.SALL, "SALL " + sender + " " + message + "\r\n",
                new AbstractMap.SimpleEntry<ABNF, Object>(ProtocoleSwinen.sender, sender),
                new AbstractMap.SimpleEntry<ABNF, Object>(ProtocoleSwinen.message, message)
        );
        return verification;
    }
    private boolean testMakeAndCheck(MessageProtocole mp, String messageExpected, AbstractMap.SimpleEntry<ABNF, Object>... args) {
        Protocole protocole = new ProtocoleSwinen();
        protocole.prepare(mp);
        // création d'un message avec des variables données
        String request;
        try {
            request = protocole.make(args);
        } catch (Exception ex) {
            // Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        // vérification du format du message
        if (protocole.check(request)) {
            // vérification des variables correctement insérées dans le message
            return messageExpected.equals(request);
        }
        return false;
    }
}
