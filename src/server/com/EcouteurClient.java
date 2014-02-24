/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerControleur;

/**
 *
 * @author David
 */
public class EcouteurClient extends Thread {

    private final Client clientInfo;
    private final ClientManager manager;
    private final BufferedReader inFromClient;
    private final ServerControleur server;

    public EcouteurClient(Client client, Socket sck, ClientManager clientManager, ServerControleur serveur) throws IOException {
        clientInfo = client;
        manager = clientManager;
        server = serveur;
        inFromClient = new BufferedReader(new InputStreamReader(sck.getInputStream(), Charset.forName("UTF-8")));
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

            if (message == null) {
                connectionLost();
            } else if (message.length() > 0) {
                manager.traiter(message);
            }

        } catch (SocketException ex) {
            connectionLost();
        } catch (IOException ex) {
            server.afficher("[error] reception depuis " + toString() + " a échouée");
        }
    }

    private void connectionLost() {
        //Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
        server.afficher("[error] connection avec " + toString() + " perdue");
        manager.close();
        if (clientInfo.isAccepte()) {
            manager.executer("/quit");
        }
    }

    public void close() {
        try {
            inFromClient.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            server.afficher("[error] ecouteur " + toString() + " mal fermé");
        }
    }

}
