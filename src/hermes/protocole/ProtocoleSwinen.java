/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.protocole;

import hermes.protocole.message.MessageProtocole;
import hermes.format.abnf.ABNF;
import hermes.format.abnf.Lexique;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ProtocoleSwinen extends AbstractProtocole {
    
    public static ABNF user;
    public static ABNF pass;
    public static ABNF message;
    public static ABNF sender;
    public static ABNF receiver;
    public static ABNF digit;

    public static MessageProtocole HELLO;
    public static MessageProtocole MSG;
    public static MessageProtocole SMSG;
    public static MessageProtocole ALL;
    public static MessageProtocole SALL;
    public static MessageProtocole QUIT;
    public static MessageProtocole RESPONSE;
    public static MessageProtocole USERS;
    public static MessageProtocole SUSERS;
    public static MessageProtocole JOIN;
    public static MessageProtocole LEAVE;
    public static MessageProtocole REGISTER;
    public static MessageProtocole TYPING;
    public static MessageProtocole STYPING;
    public static MessageProtocole SERVERSHUTDOWN;
    
    static {
        initVariables();
        initMessages();
    }

    /**
     * Compile les variables du protocole à partir des mots du lexique.
     *
     * user = 4*8(letter|digit) ; 4 à 8 lettres ou chiffres
     * pass = 4*10passchar ; 4 à 10 caractères imprimables sans espace
     * receiver = user ; définition identique (pour la lisibilité)
     * sender = user ; définition identique (pour la lisibilité)
     * message = 1*500character ; 1 à 500 caractères imprimables (espace compris)
     * 
     * @see ABNF
     * @see Lexique
     * @see ABNFSyntaxicalParser
     */
    private static void initVariables() {   
        user = ABNF.compilerEtAjouter("user = 4*8(letter|digit)");
        pass = ABNF.compilerEtAjouter("pass = 4*10passchar");
        receiver = ABNF.compilerEtAjouter("receiver = user");
        sender = ABNF.compilerEtAjouter("sender = user");
        message = ABNF.compilerEtAjouter("message = 1*500character");
        digit = Lexique.digit;
    }

    /**
     * hello = "HELLO" space user space pass crlf
     * msg = "MSG" space receiver space message crlf
     * smsg = "SMSG" space sender space message crlf
     * all = "ALL" space message crlf
     * sall = "SALL" space sender space message crlf
     * quit = "QUIT" crlf
     * response = digit [space message] crlf
     */
    private static void initMessages() {     
        HELLO = new MessageProtocole("hello = \"HELLO\" space user space pass crlf", user, pass);
        MSG = new MessageProtocole("msg = \"MSG\" space receiver space message crlf", receiver, message);
        SMSG = new MessageProtocole("smsg = \"SMSG\" space sender space message crlf", sender, message);
        ALL = new MessageProtocole("all = \"ALL\" space message crlf", message);
        SALL = new MessageProtocole("sall = \"SALL\" space sender space message crlf", sender, message);
        QUIT = new MessageProtocole("quit = \"QUIT\" crlf");
        RESPONSE = new MessageProtocole("response = digit [space message] crlf", digit, message);
        USERS = new MessageProtocole("users = \"USERS\" crlf");
        SUSERS = new MessageProtocole("susers = \"SUSERS\" *(space user) crlf", user);
        JOIN = new MessageProtocole("join = \"JOIN\" space user crlf", user);
        LEAVE = new MessageProtocole("leave = \"LEAVE\" space user crlf", user);
        REGISTER = new MessageProtocole("register = \"REGISTER\" space user space pass crlf", user, pass);
        TYPING = new MessageProtocole("typing = \"TYPING\" space digit crlf", digit);
        STYPING = new MessageProtocole("styping = \"STYPING\" space user space digit crlf", user, digit);
        SERVERSHUTDOWN = new MessageProtocole("servershutdown = \"SERVERSHUTDOWN\" crlf");
    }

    public ProtocoleSwinen() {
        add(HELLO);
        add(MSG);
        add(SMSG);
        add(ALL);
        add(SALL);
        add(QUIT);
        add(RESPONSE);
        add(USERS);
        add(SUSERS);
        add(JOIN);
        add(LEAVE);
        add(REGISTER);
        add(TYPING);
        add(STYPING);
        add(SERVERSHUTDOWN);
    }
}
