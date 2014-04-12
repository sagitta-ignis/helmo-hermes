/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Ecouteur extends Thread {

    Client client;
    MessageQueueHandler messageQueue;
    ServerRequestHandler serverRequest;
    BufferedReader inFromServer;

    public Ecouteur(Client clt) {
        client = clt;
        messageQueue = new MessageQueueHandler();
        serverRequest = new ServerRequestHandler(client);
    }

    public void lier(Socket socket) throws IOException {
        inFromServer = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        Charset.forName("UTF-8")
                )
        );
    }

    public MessageQueueHandler getMessageQueue() {
        return messageQueue;
    }

    @Override
    public void run() {
        while (client.getConnectionHandler().canRun()) {
            String message = lire();
            System.out.println("");
            if(message == null) continue;
            if (message.startsWith("[error]")) {
                break;
            }
            if (!traiter(message)) {
                client.setEtat(Client.UnknownRequestReceived, message);
            }
        }
    }

    public synchronized String lire() {
        String message;
        try {
            do {
                System.out.println("readline");
                message = inFromServer.readLine();
                if(veriferAttente(message)) {
                    return null;
                }
            } while (message.length() < 1);
        } catch (SocketException ex) {
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            client.setEtat(Client.ConnexionLost);
            message = "[error] connexion perdue avec le serveur";
        } catch (IOException ex) {
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            client.setEtat(Client.ReceptionFailed);
            message = "[error] reception depuis serveur a échoué";
        }
        return message;
    }

    private boolean veriferAttente(String message) {
        if(message.length() < 1) return false;
        return messageQueue.traiter(message);
    }

    private boolean traiter(String message) {
        if (message != null) {
            return serverRequest.parser(message + "\r\n");
        }
        return false;
    }

    public void fermer() throws IOException {
        this.interrupt();
        try {
            if (inFromServer != null) {
                inFromServer.close();
            }
        } catch (IOException ex) {
            String message = "[error] socket depuis serveur mal fermé";
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, message, ex);
            throw new IOException(message);
        } finally {
            inFromServer = null;
        }
    }
}
