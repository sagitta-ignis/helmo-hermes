/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.protocole;

import hermes.format.abnf.ABNF;
import hermes.format.abnf.Lexique;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ProtocoleSwinen extends AbstractProtocole {

    private static final Lexique lexique;

    public static ABNF user;
    public static ABNF pass;
    public static ABNF message;
    public static ABNF sender;
    public static ABNF receiver;
    public static ABNF digit = Lexique.digit;

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
        user = lexique.compiler("user", "[letter[digit]]{4,8}+");
        pass = lexique.compiler("pass", "passchar{4,10}+");
        message = lexique.compiler("message", "character{1,500}+");
        sender = lexique.compiler("sender", "[letter[digit]]{4,8}+");
        receiver = lexique.compiler("receiver", "[letter[digit]]{4,8}+");
    }

    private static void initLexique() {
        lexique.ajouter(user);
        lexique.ajouter(pass);
        lexique.ajouter(message);
        lexique.ajouter(sender);
        lexique.ajouter(receiver);
    }

    private static void initMessages() {
        HELLO = new MessageProtocole(lexique, "HELLO", "HELLO space user space pass crlf", user, pass);
        MSG = new MessageProtocole(lexique, "MSG", "MSG space receiver space message crlf", receiver, message);
        SMSG = new MessageProtocole(lexique, "SMSG", "SMSG space sender space message crlf", sender, message);
        ALL = new MessageProtocole(lexique, "ALL", "ALL space message crlf", message);
        SALL = new MessageProtocole(lexique, "SALL", "SALL space sender space message crlf", sender, message);
        QUIT = new MessageProtocole(lexique, "QUIT", "QUIT crlf");
        RESPONSE = new MessageProtocole(lexique, "REPONSE", "digit *space message* crlf", digit, message);
        USERS = new MessageProtocole(lexique, "USERS", "USER crlf");
        SUSERS = new MessageProtocole(lexique, "SUSERS", "SUSERS space crlf", user);
        JOIN = new MessageProtocole(lexique, "JOIN", "JOIN space user crlf", user);
        LEAVE = new MessageProtocole(lexique, "LEAVE", "LEAVE space user crlf", user);
        REGISTER = new MessageProtocole(lexique, "REGISTER", "REGISTER space user space pass crlf", user, pass);
        TYPING = new MessageProtocole(lexique, "TYPING", "TYPING crlf");
        STYPING = new MessageProtocole(lexique, "STYPING", "STYPING space user crlf",user);
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
    }
}
