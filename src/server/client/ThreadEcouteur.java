/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controlleur.ChannelControlleur;
import server.controlleur.ServeurControlleur;
import server.commands.Quit;
import server.response.SentResponse;

/**
 *
 * @author David
 */
public class ThreadEcouteur extends Thread {

    private final Client clientInfo;
    private final ClientManager manager;
    private BufferedReader inFromClient;
    private final ChannelControlleur channelManager;
    private final SentResponse responseNS;

    public ThreadEcouteur(Client client, Socket sck, ClientManager clientManager, ChannelControlleur channelManager) throws IOException {
        clientInfo = client;
        manager = clientManager;
        this.channelManager = channelManager;
        responseNS = new SentResponse();
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
                manager.traiter(message + "\r\n");
            }

        } catch (SocketException ex) {
            connectionLost();
        } catch (IOException ex) {
            channelManager.afficher(responseNS.getError(102));
        }
    }

    private void connectionLost() {
        if (clientInfo.isOpened()) {
            Quit quit = new Quit(manager, channelManager);
            quit.execute();
        }

    }

    public void close() {
        try {
            inFromClient.close();
            this.interrupt();
        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
            channelManager.afficher(responseNS.getError(103));
        } finally {
            inFromClient = null;
        }
    }

}
