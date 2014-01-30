/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.Command;
import server.com.commands.Connect;
import server.com.commands.Quit;
import server.Server;
import server.com.commands.Set;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientManager extends Thread {

    private static int increment = 1;

    private final Socket socket;
    private final Server server;
    private final BufferedReader inFromClient;
    private final PrintWriter outToClient;

    private Map<String, Command> commands;

    private final int id = increment++;
    private String username = "Anonyme";
    private boolean opened = true;

    public ClientManager(Server srv, Socket sck) throws IOException {
        inFromClient = new BufferedReader(
                new InputStreamReader(
                        sck.getInputStream(), Charset.forName("UTF-8"))
        );
        OutputStreamWriter osw = new OutputStreamWriter(
                sck.getOutputStream(), Charset.forName("UTF-8")
        );
        outToClient = new PrintWriter(osw, true);
        server = srv;
        socket = sck;
        initCommands();
    }

    private void initCommands() {
        commands = new HashMap<>();
        commands.put("/quit", new Quit(server, this));
        commands.put("/connect", new Connect(server, this));
        commands.put("/set", new Set(this));
    }

    public int getClientId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    @Override
    public void run() {
        while (opened) {
            recevoir();
        }
    }

    private void recevoir() {
        try {
            String message = inFromClient.readLine();
            if(message == null) {
                executer("/quit");
            }
            else if (message.length() > 0) {
                traiter(message);
            }
        } catch (SocketException ex) {
            connectionLost();
        } catch (IOException ex) {
            server.afficher("[error] reception depuis " + toString() + " a échouée");
        }
    }

    private void connectionLost() {
        //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        server.afficher("[error] connection avec " + toString() + " perdue");
        opened = false;
        executer("/quit");
    }

    private void traiter(String message) {
        if (message.charAt(0) == '/') {
            executer(message);
        } else {
            server.transmettre(username + " : " + message);
        }
    }
    
    private void executer(String requete) {
        String[] r = requete.split(" ", 2);
        if (r.length >= 1) {
            Command c = commands.get(r[0]);
            if (c != null) {
                if (r.length == 2) {
                    c.execute(r[1]);
                } else {
                    c.execute();
                }
            } else {
                outToClient.printf("server : commande %s inconnue\n", requete);
            }
        }
    }

    public void envoyer(String message) {
        outToClient.println(message);
        outToClient.flush();
    }

    public void close() {
        try {
            inFromClient.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            server.afficher("[error] socket avec " + toString() + " mal fermé");
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", username, id);
    }
}
