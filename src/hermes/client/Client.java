package hermes.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
import hermes.chat.controleur.handler.ClientMessageHandler;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnreachableServerExeception;
import java.io.*;
import hermes.util.Arrays;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Client extends Observable {

    public final static String Initial = "init";

    public final static String Connected = "connected";
    public final static String ConnexionLost = "lost";
    public final static String ConnexionBroken = "broken";

    public final static String LoggedIn = "logeedin";
    public final static String UnknownUser = "unknownuser";
    public final static String AlreadyLoggedIn = "alreadyin";
    public final static String BadMessageMaked = "badmsg";

    public final static String Opened = "opened";
    public final static String BadProtocoleMaked = "badprotomkd";
    public final static String BadProtocoleReceived = "badprotorcv";
    public final static String BadProtocoleSended = "badprotosnd";

    public final static String MessageSended = "msgsnd";
    public final static String MSG = "msg";
    public final static String MSGToSelf = "selfmsg";

    public final static String RESPONSE = "response";
    public final static String SALL = "sall";
    public final static String SMSG = "smsg";
    public final static String JOIN = "join";
    public final static String LEAVE = "leave";
    public final static String STYPING = "styping";    
    public final static String ServerShutDown = "shutdown";
    public final static String SDISCUSS = "sdiscuss"; 
    public final static String UnknownRequestReceived = "unknownreq";
    public final static String ReceptionFailed = "receptionfailed";

    public final static String LoggedOut = "";

    public final static String InputStreamUnclosed = "";
    public final static String OutputStreamUnclosed = "";

    private String etat;
    private final ClientConnectionHandler connectionHandler;
    private final ClientMessageHandler messageHandler; 
    
    private Emetteur emetteur;
    private Ecouteur ecouteur;

    public Client(ClientMessageHandler cmh) {
        etat = Initial;
        connectionHandler = new ClientConnectionHandler();
        messageHandler = cmh;
    }

    public void setEtat(String etat) {
        this.etat = etat;
        setChanged();
        notifyObservers(new Object[]{etat});
    }

    public void setEtat(String etat, Object... args) {
        this.etat = etat;
        setChanged();
        notifyObservers(Arrays.merge(new Object[]{etat},args));
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
            emetteur = new Emetteur();
            ecouteur = new Ecouteur(this);
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
        messageHandler.execute("/hello", nom, password);
        return connectionHandler.isLogged();
    }

    public void fermer() throws Exception {
        try {
            connectionHandler.shutdown();
            emetteur.fermer();
            ecouteur.fermer();
            connectionHandler.disconnect();
        } catch (IOException ex) {
            String message = "[error] fermeture du socket client a échoué";
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, message, ex);
            throw new Exception(message, ex);
        }
    }

    public boolean canRun() {
        return connectionHandler.canRun();
    }
}
