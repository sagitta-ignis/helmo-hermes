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
    public static ABNF channel;
    public static ABNF pass;
    public static ABNF message;
    public static ABNF sender;
    public static ABNF receiver;
    public static ABNF entier;
    public static ABNF digit;
    
    public static MessageProtocole REGISTER;
    public static MessageProtocole HELLO;
    public static MessageProtocole USERS;
    public static MessageProtocole SUSERS;
    public static MessageProtocole JOIN;
    public static MessageProtocole LEAVE;
    public static MessageProtocole RESPONSE;    
    public static MessageProtocole QUIT;

    public static MessageProtocole SERVERSHUTDOWN;   
    
    public static MessageProtocole CREATECHANNEL;
    public static MessageProtocole DELETECHANNEL; 
    public static MessageProtocole ENTER; 
    public static MessageProtocole EXIT; 
    public static MessageProtocole CHANNELS; 
    public static MessageProtocole SCHANNELS; 
    public static MessageProtocole INFOCHANNEL; 
    public static MessageProtocole SINFOCHANNEL; 
    
    public static MessageProtocole MSG;
    public static MessageProtocole SMSG;
    public static MessageProtocole ALL;
    public static MessageProtocole SALL;
    
    public static MessageProtocole TYPING;
    public static MessageProtocole STYPING;
    
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
        channel = ABNF.compilerEtAjouter("channel = 1*50(letter|digit)");
        pass = ABNF.compilerEtAjouter("pass = 4*10passchar");
        receiver = ABNF.compilerEtAjouter("receiver = user");
        sender = ABNF.compilerEtAjouter("sender = user");
        message = ABNF.compilerEtAjouter("message = 1*500character");
        entier = ABNF.compilerEtAjouter("entier = 1*10digit");
        digit = Lexique.digit;
    }

    
    private static void initMessages() {     
        initConnexions();
        initChannels();
        initCommunications();
        initNotifications();
    }
    
    /**
     * register = "REGISTER" space user space pass crlf
     * hello = "HELLO" space user space pass crlf
     * join = "JOIN" space user crlf
     * quit = "QUIT" crlf
     * leave = "LEAVE" space user crlf
     * users = "USERS" crlf
     * susers = "SUSERS" *(space user) crlf
     * servershutdown = "SERVERSHUTDOWN" crlf
     * response = digit [space message] crlf
     */
    private static void initConnexions() {        
        REGISTER = new MessageProtocole("register = \"REGISTER\" space user space pass crlf", user, pass);   
        
        HELLO = new MessageProtocole("hello = \"HELLO\" space user space pass crlf", user, pass);
        JOIN = new MessageProtocole("join = \"JOIN\" space user crlf", user);
        QUIT = new MessageProtocole("quit = \"QUIT\" crlf");
        LEAVE = new MessageProtocole("leave = \"LEAVE\" space user crlf", user);
        
        USERS = new MessageProtocole("users = \"USERS\" crlf");
        SUSERS = new MessageProtocole("susers = \"SUSERS\" *(space user) crlf", user);

        SERVERSHUTDOWN = new MessageProtocole("servershutdown = \"SERVERSHUTDOWN\" crlf");
        
        RESPONSE = new MessageProtocole("response = digit [space message] crlf", digit, message);
    }
    
    /**
     * createchannel = "CREATECHANNEL" space channel [space pass] crlf
     */
    private static void initChannels() {     
        CREATECHANNEL = new MessageProtocole("createchannel = \"CREATECHANNEL\" space channel [space pass] crlf", channel, pass);
        DELETECHANNEL = new MessageProtocole("deletechannel = \"DELETECHANNEL\" space channel crlf", channel);
        
        ENTER = new MessageProtocole("enter = \"ENTER\" space channel [space pass] crlf", channel, pass);
        EXIT = new MessageProtocole("exit = \"EXIT\" space channel crlf", channel);
        
        CHANNELS = new MessageProtocole("channels = \"CHANNELS\" crlf");
        SCHANNELS = new MessageProtocole("schannels = \"SCHANNELS\" *(space channel) crlf", channel);
        
        INFOCHANNEL = new MessageProtocole("channels = \"INFOCHANNEL\" space channel crlf", channel);
        SINFOCHANNEL = new MessageProtocole("channels = \"SINFOCHANNEL\" space \"protege=\" digit space \"utilisateurs=\" entier crlf", digit, entier);
    }
    
    /**
     * msg = "MSG" space receiver space message crlf
     * smsg = "SMSG" space sender space message crlf
     * all = "ALL" space message crlf
     * sall = "SALL" space sender space message crlf
     */
    private static void initCommunications() {     
        MSG = new MessageProtocole("msg = \"MSG\" space receiver space message crlf", receiver, message);
        SMSG = new MessageProtocole("smsg = \"SMSG\" space sender space message crlf", sender, message);
        ALL = new MessageProtocole("all = \"ALL\" space message crlf", message);
        SALL = new MessageProtocole("sall = \"SALL\" space sender space message crlf", sender, message);
    }
    
    /**
     * typing = "TYPING" space digit crlf
     * styping = "STYPING" space user space digit crlf
     */
    private static void initNotifications() {     
        TYPING = new MessageProtocole("typing = \"TYPING\" space digit crlf", digit);
        STYPING = new MessageProtocole("styping = \"STYPING\" space user space digit crlf", user, digit);
    }

    public ProtocoleSwinen() {
        add(REGISTER);
        add(HELLO);
        add(JOIN);        
        add(QUIT);
        add(LEAVE); 
        add(USERS);
        add(SUSERS);   
        add(SERVERSHUTDOWN);           
        
        add(RESPONSE);
        
        add(MSG);
        add(SMSG);
        add(ALL);
        add(SALL);

        add(TYPING);
        add(STYPING);
    }
}
