/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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
    BufferedReader inFromServer;

    public Ecouteur(Client clt) {
        client = clt;
    }

    public void lier(Socket socket) throws IOException {
        inFromServer = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        Charset.forName("UTF-8")
                )
        );
    }

    @Override
    public void run() {
        while (client.canRun()) {
            String message = lire();
            if(!traiter(message)) {
                client.setOpened(false);
                break;
            }
        }
    }

    public String lire() {
        try {
            return inFromServer.readLine();
        } catch (SocketException ex) {
            return "[error] connexion perdue avec le serveur";
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return "[error] reception depuis serveur a échoué";
        }
    }

    private boolean traiter(String message) {
        if (message != null) {
            client.print(message);
            return !message.startsWith("[error]");
        }
        return false;
    }

    public void fermer() {
        this.interrupt();
        try {
            if (inFromServer != null) {
                inFromServer.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            client.print("[error] socket avec serveur mal fermé");
        }
        inFromServer = null;
    }
}
