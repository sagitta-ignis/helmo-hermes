package hermes.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import hermes.client.command.messages.*;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnreachableServerExeception;
import hermes.client.exception.UnopenableExecption;
import hermes.protocole.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Client {

    private Protocole protocole;
    private Socket socket;
    private final Emetteur emetteur;
    private final Ecouteur ecouteur;
    private Encodeur encodeur;

    private PrintStream output;
    private ClientListener listener;

    private Map<MessageProtocole, Message> request;

    private boolean connected;
    private boolean logged;
    private boolean opened;

    public Client() {
        connected = false;
        logged = false;
        opened = false;
        request = new HashMap<>();
        protocole = new ProtocoleSwinen();
        emetteur = new Emetteur();
        ecouteur = new Ecouteur(this);
        initCommands();
    }

    private void initCommands() {
        request.put(ProtocoleSwinen.SALL, new SAll(this, protocole));
        request.put(ProtocoleSwinen.SMSG, new SMsg(this, protocole));
    }

    public Client(ClientListener cl, InputStream in, OutputStream out) {
        this();
        listener = cl;
        if (in != null) {
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
            password = "password";
        }
        Message hello = new Hello(this, protocole);
        hello.setArgs(nom, password);
        hello.execute();
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
        text = nettoyer(text, true);
        emetteur.envoyer(text);
        //logged = !text.equals("/quit");
    }

    private String nettoyer(String text, boolean envoi) {
        String n = "\n";
        String r = "\r";
        String nPropre = "-n";
        String rPropre = "-r";
        if (!envoi) {
            n = "-n";
            r = "-r";
            nPropre = "\n";
            rPropre = "\r";
        }
        text = remplacer(n, text, nPropre);
        return remplacer(r, text, rPropre);
    }

    private String remplacer(String definition, String sequence, String synthax) {
        Matcher chercher = Pattern.compile(definition).matcher(sequence);
        return chercher.replaceAll(synthax);
    }

    public boolean parse(String text) {
        if (text.startsWith("[error]")) {
            return false;
        }
        text = nettoyer(text, false);
        for (Map.Entry<MessageProtocole, Message> entry : request.entrySet()) {
            MessageProtocole messageProtocole = entry.getKey();
            protocole.prepare(messageProtocole);
            if (protocole.check(text)) {
                Message message = entry.getValue();
                message.setArgs(text);
                message.execute();
            }
        }
        //print(message);
        return true;
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

    public Ecouteur getEcouteur() {
        return ecouteur;
    }

    public String recevoir() {
        String message = ecouteur.lire();
        if (message == null) {
            return null;
        }
        return nettoyer(message, false);
    }

    public void envoyer(String user, String text) {
        Message message;
        if(text.equals("/quit")) {
            message = new Quit(this, protocole);
        } else if (user.equals("all")) {
            message = new All(this, protocole);
            message.setArgs(text);
        } else {
            message = new Msg(this, protocole);
            message.setArgs(user, text);
        }
        message.execute();
    }
}
