package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import client.exception.NotConnectedException;
import client.exception.UnreachableServerExeception;
import client.exception.UnopenableExecption;
import pattern.Command;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Client {

    private Socket socket;
    private final Emetteur emetteur;
    private final Ecouteur ecouteur;
    private Encodeur encodeur;
    
    private PrintStream output;
    private ClientListener listener;

    private Map<String, Command> commands;

    private boolean connected;
    private boolean logged;
    private boolean opened;

    public Client() {
        connected = false;
        logged = false;
        opened = false;
        emetteur = new Emetteur();
        ecouteur = new Ecouteur(this);
    }

    public Client(ClientListener cl, InputStream in, OutputStream out) {
        this();
        listener = cl;
        if(in != null) {
            encodeur = new Encodeur(this, in);
        }
        if (out != null) {
            output = new PrintStream(out);
        }
    }

    public Client(ClientListener cl) {
        this(cl, null, null);
    }

    public Client(InputStream in, OutputStream out) {
        this(null, in, out);
    }

    public boolean connect(String host, int port) throws UnreachableServerExeception {
        try {
            socket = new Socket(host, port);
            emetteur.lier(socket);
            ecouteur.lier(socket);
            connected = true;
        } catch (IOException ex) {
            throw new UnreachableServerExeception();
        }
        return connected;
    }
    
    public boolean disconnect() {
        logged = false;
        connected = false;
        try {
            socket.close();
        } catch (IOException ex) {
            
        }
        socket = null;
        return canRun();
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean b) {
        logged = b;
    }

    public void setOpened(boolean b) {
        opened = b;
    }

    public boolean canRun() {
        return connected && logged && opened;
    }

    public final boolean login(String nom, String password) throws NotConnectedException {
        if (!connected) {
            throw new NotConnectedException();
        }
        if (nom == null || nom.isEmpty()) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            send(String.format("/connect %s ", nom));
        } else {
            send(String.format("/connect %s %s", nom, password));
        }
        String message = ecouteur.lire();
        if (message.startsWith("-- " + nom)) {
            print(message);
            logged = true;
        }
        return logged;
    }

    public synchronized void open() throws UnopenableExecption {
        if (!connected || !logged) {
            throw new UnopenableExecption();
        }
        opened = true;
        if (encodeur != null) {
            encodeur.start();
        }
        ecouteur.start();
        while ((encodeur != null && encodeur.isAlive()) || ecouteur.isAlive()) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public void send(String text) {
        emetteur.envoyer(text);
        logged = !text.equals("/quit");
    }

    public void print(String text) {
        if (output != null) {
            output.println(text);
        }
        if (listener != null) {
            listener.lire(text);
        }
    }

    public void close() {
        disconnect();
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            print("[error] fermeture du socket client a échoué");
        }
        emetteur.fermer();
        ecouteur.fermer();
    }
}
