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
import server.Server;
import server.com.commands.*;


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
    private Map<String, Command> commandsAdmin;

    private Client clientInfo;

    public ClientManager(Server srv, Socket sck) throws IOException {
        clientInfo = new Client(increment++);
        
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
        commandsAdmin = new HashMap<>();
        commandsAdmin.put("/mute", new Mute(server));
        commandsAdmin.put("/unmute", new UnMute(server));
        
        commands = new HashMap<>();
        commands.put("/quit", new Quit(server, this, clientInfo));
        commands.put("/connect", new Connect(server, clientInfo));
        commands.put("/set", new Set(this));
        commands.put("/time", new Time(server, clientInfo,this));
        commands.put("/users", new Users(server,this));
        
        //DOIT TOUJOURS ETRE LE DERNIER AJOUT
        commands.put("/help", new Help(this,commands,commandsAdmin));
    }

    public Client getClient(){
        return clientInfo;
    }
    
    @Override
    public void run() { 
        while (clientInfo.isOpened()) {
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
        clientInfo.setOpened(false);
        executer("/quit");
    }

    private void traiter(String message) {
        if (message.charAt(0) == '/') {
            executer(message);
        } else if(!clientInfo.isMuet()){            
            server.transmettre(clientInfo.toString() + " : " + message);
        }
    }
    
    private void executer(String requete) {
        String[] r = requete.split(" ", 2);
        if (r.length >= 1) {
            
            Command commandPublique = commands.get(r[0]); 
            Command commandAdmin = commandsAdmin.get(r[0]);
            
            if (commandPublique != null) {
                if (r.length == 2) {
                    commandPublique.execute(r[1]);
                } else {
                    commandPublique.execute();
                }
                        
            }else if(clientInfo.isAdmin() && commandAdmin != null){
                 if (r.length == 2) {
                    commandAdmin.execute(r[1]);
                } else {
                    commandAdmin.execute();
                }
            }else {
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

}
