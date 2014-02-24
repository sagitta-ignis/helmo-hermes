/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.etat;

import server.ServerControleur;
import server.com.Client;
import server.com.ClientManager;

/**
 *
 * @author David
 */
public class Connecte {

    private final ClientManager manager;
    private final Client clientInfo;
    private final ServerControleur server;

    public Connecte(ClientManager clientManager, Client client, ServerControleur serveur) {
        manager = clientManager;
        clientInfo = client;
        server = serveur;
    }

    public void traiter(String message) {
        if (message.charAt(0) == '/') {
            manager.executer(message);
        } else if (!clientInfo.isMuet()) {
            server.transmettre(clientInfo.toString() + " : " + message);
        }
    }
}
