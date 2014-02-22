/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import pattern.Command;
import server.ServerControleur;
import server.com.commands.*;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientManager {

    private static int increment = 1;

    private final ServerControleur server;

    private final EcouteurClient ecouteur;
    private final SortieClient sortie;

    private final Client clientInfo;

    private Map<String, Command> commands;
    private Map<String, Command> commandsAdmin;

    public ClientManager(ServerControleur srv, Socket sck) throws IOException {
        clientInfo = new Client(increment++);

        server = srv;

        ecouteur = new EcouteurClient(clientInfo, sck, this, server);
        sortie = new SortieClient(sck);        
        initCommands();

        ecouteur.start();
    }

    private void initCommands() {
        commandsAdmin = new HashMap<>();
        commandsAdmin.put("/mute", new Mute(server));
        commandsAdmin.put("/unmute", new UnMute(server));

        commands = new HashMap<>();
        commands.put("/quit", new Quit(server, this, clientInfo));
        commands.put("/connect", new Connect(server, clientInfo));
        commands.put("/set", new Set(this));
        commands.put("/time", new Time(server, clientInfo, this));
        commands.put("/users", new Users(server, this));

        //DOIT TOUJOURS ETRE LE DERNIER AJOUT
        commands.put("/help", new Help(this, commands, commandsAdmin));
    }

    public Client getClient() {
        return clientInfo;
    }

    public void traiter(String message) {
        if (message.charAt(0) == '/') {
            executer(message);
        } else if (!clientInfo.isMuet()) {
            server.transmettre(clientInfo.toString() + " : " + message);
        }
    }

    public void executer(String requete) {
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

            } else if (clientInfo.isAdmin() && commandAdmin != null) {
                if (r.length == 2) {
                    commandAdmin.execute(r[1]);
                } else {
                    commandAdmin.execute();
                }
            } else {
                envoyer(String.format("server : commande %s inconnue\n", requete));
            }
        }
    }

    public void envoyer(String message) {
        sortie.envoyer(message);
    }

    public void close() {
        ecouteur.close();
        sortie.close();
    }

}
