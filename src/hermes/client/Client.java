package hermes.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import hermes.client.command.CommandArgument;
import hermes.client.command.messages.*;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnreachableServerExeception;
import hermes.client.exception.UnopenableExecption;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Client {

    private final Protocole protocole;
    private final ClientConnectionHandler connectionHandler;
    private final Emetteur emetteur;
    private final Ecouteur ecouteur;

    private final List<ClientListener> listeners;

    public Client() {
        protocole = new ProtocoleSwinen();
        connectionHandler = new ClientConnectionHandler();
        emetteur = new Emetteur();
        ecouteur = new Ecouteur(this);

        listeners = new ArrayList<>();
    }

    public void addListener(ClientListener listener) {
        listeners.add(listener);
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

    public void open() throws UnopenableExecption {
        if (!connectionHandler.isConnected() || !connectionHandler.isLogged()) {
            throw new UnopenableExecption();
        }
        connectionHandler.setOpened(true);
        ecouteur.start();
        while (ecouteur.isAlive()) {
        }
    }

    public void close() throws Exception {
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

    public synchronized void afficher(String text) {
        if (listeners != null) {
            for (ClientListener listener : listeners) {
                listener.lire(text);
            }
        }
    }

    public void envoyer(String user, String text) {
        CommandArgument message;
        if (user == null) {
            message = new All(this);
            message.setArgs(text);
        } else {
            message = new Msg(this);
            message.setArgs(user, text);
        }
        message.execute();
    }

    public void quitter() {
        CommandArgument message = new Quit(this, protocole);
        message.execute();
    }
}
