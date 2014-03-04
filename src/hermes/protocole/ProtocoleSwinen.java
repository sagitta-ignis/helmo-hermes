/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.protocole;

import hermes.format.abnf.ABNF;
import hermes.format.abnf.Lexique;
import java.util.Map.Entry;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ProtocoleSwinen implements Protocole {

    private static final Lexique lexique;
    
    public static ABNF user;
    public static ABNF pass;
    public static ABNF message;
    public static ABNF sender;
    public static ABNF receiver;

    public static MessageProtocole HELLO;
    public static MessageProtocole MSG;
    public static MessageProtocole SMSG;
    public static MessageProtocole ALL;
    public static MessageProtocole SALL;
    public static MessageProtocole QUIT;
    public static MessageProtocole response;
    
    static {
        lexique = new Lexique();
        initVariable();
        initLexique();
        initMessages();
    }
    
    /**
     * Compile les variables du protocole à partir des mots du lexique. Le
     * lexique n'est pas encore capable de traduire l'ABNF. Il faut utiliser la
     * synthaxe Java pour le reste.
     *
     * @see Lexique
     */
    private static void initVariable() {
        user = lexique.compiler("user", "([letter][digit]){4,8}+");
        pass = lexique.compiler("pass", "(passchar){4,10}+");
        message = lexique.compiler("message", "(character){1,500}+");
        sender = lexique.compiler("sender", "user");
        receiver = lexique.compiler("receiver", "user");
    }

    private static void initLexique() {
        lexique.ajouter(user);
        lexique.ajouter(pass);
        lexique.ajouter(message);
        lexique.ajouter(sender);
        lexique.ajouter(receiver);
    }

    private static void initMessages() {
        HELLO = new MessageProtocole(lexique.compiler("HELLO", "HELLO space user space pass crlf"));
        HELLO.ajouter(user);
        HELLO.ajouter(pass);
        MSG = new MessageProtocole(lexique.compiler("MSG", "MSG space receiver space message crlf"));
        MSG.ajouter(receiver);
        MSG.ajouter(message);
        SMSG = new MessageProtocole(lexique.compiler("SMSG", "SMSG space sender space message crlf"));
        MSG.ajouter(sender);
        MSG.ajouter(message);
        ALL = new MessageProtocole(lexique.compiler("ALL", "ALL space message crlf"));
        ALL.ajouter(message);
        SALL = new MessageProtocole(lexique.compiler("SALL", "SALL space sender space message crlf"));
        SALL.ajouter(sender);
        SALL.ajouter(message);
        QUIT = new MessageProtocole(lexique.compiler("QUIT", "QUIT crlf"));
        response = new MessageProtocole(lexique.compiler("response", "digit (space message)? crlf"));
        response.ajouter(message);
    }
    
    private MessageProtocole prepared;

    public ProtocoleSwinen() {
        prepared = null;
    }

    @Override
    public void prepare(MessageProtocole message) {
        prepared = message;
    }

    @Override
    public String make(Entry<ABNF,String>... args) {
        for (Entry<ABNF, String> entry : args) {
            prepared.set(entry.getKey(), entry.getValue());
        }
        return prepared.remplir();
    }

    @Override
    public boolean check(String message) {
        return prepared.verifier(message);
    }
}
