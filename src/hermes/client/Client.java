package hermes.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
import hermes.client.command.message.All;
import hermes.client.command.message.Msg;
import hermes.client.command.message.Hello;
import hermes.client.command.message.Message;
import pattern.command.CommandArgument;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnreachableServerExeception;
import hermes.client.exception.UnopenableExecption;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.io.*;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Client extends Observable {

    public final static int Initial = 0;
    
    public final static int Connected = 1;
    public final static int ConnexionLost = -1;
    
    public final static int LoggedIn = 2;
    public final static int UnknownUser = -20;
    public final static int BadMessageMaked = -21;
    
    public final static int Opened = 3;
    public final static int BadProtocoleMaked = -30;
    public final static int BadProtocoleReceived = -31;
    public final static int BadProtocoleSended = -32;
    
    public final static int MessageSended = 4;
    public final static int MSG = 42;
    
    public final static int RESPONSE = 50;
    public final static int SALL = 51;
    public final static int SMSG = 52;
    public final static int JOIN = 53;
    public final static int LEAVE = 54;
    public final static int STYPING = 55;
    public final static int UnknownRequestReceived = -50;
    public final static int ReceptionFailed = -51;
        
    public final static int LoggedOut = 6;
    
    public final static int InputStreamUnclosed = -71;
    public final static int OutputStreamUnclosed = -72;
    
    private int etat;    
    private final Utilisateurs users;
    private final Protocole protocole;
    private final ClientConnectionHandler connectionHandler;
    private final Emetteur emetteur;
    private final Ecouteur ecouteur;

    public Client(Utilisateurs users) {
        etat = Initial;
        this.users = users;
        protocole = new ProtocoleSwinen();
        connectionHandler = new ClientConnectionHandler();
        emetteur = new Emetteur();
        ecouteur = new Ecouteur(this);
    }

    public void setEtat(int etat) {
        this.etat = etat;
        setChanged();
        notifyObservers();
    }
    
    public void setEtat(int etat, Object... args) {
        this.etat = etat;
        setChanged();
        notifyObservers(args);
    }

    public int getEtat() {
        return etat;
    }
    
    public Utilisateurs getUsers() {
        return users;
    }

    public Protocole getProtocole() {
        return protocole;
    }

    public ClientConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public Emetteur getEmetteur() {
        return emetteur;
    }

    public Ecouteur getEcouteur() {
        return ecouteur;
    }

    public boolean connect(String host, int port) throws UnreachableServerExeception {
        return connectionHandler.connect(host, port);
    }

    public final boolean login(String nom, String password) throws NotConnectedException {
        if (!connectionHandler.isConnected()) {
            throw new NotConnectedException();
        }
        try {
            ecouteur.lier(connectionHandler.getSocket());
            emetteur.lier(connectionHandler.getSocket());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            connectionHandler.setConnected(false);
            return false;
        }
        if (nom == null || nom.isEmpty()) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            password = "password";
        }
        Message hello = new Hello(this);
        hello.setArgs(nom, password);
        hello.execute();
        return connectionHandler.isLogged();
    }

    public void ouvrir() throws UnopenableExecption {
        if (!connectionHandler.isConnected() || !connectionHandler.isLogged()) {
            throw new UnopenableExecption();
        }
        ecouteur.start();
    }

    public void fermer() throws Exception {
        try {
            connectionHandler.disconnect();
            emetteur.fermer();
            ecouteur.fermer();
        } catch (IOException ex) {
            String message = "[error] fermeture du socket client a échoué";
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, message, ex);
            throw new Exception(message);
        }
    }

    public boolean canRun() {
        return connectionHandler.canRun();
    }

    public void envoyer(String text) {
        CommandArgument message;
        message = new All(this);
        message.setArgs(text);
        message.execute();
    }

    public void envoyer(String user, String text) {
        CommandArgument message;
        message = new Msg(this);
        message.setArgs(user, text);
        message.execute();
    }
}
