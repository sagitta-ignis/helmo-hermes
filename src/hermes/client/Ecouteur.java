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
public class Ecouteur {

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

    public String lire() {
        String message;
        try {
            do {
                message = inFromServer.readLine();
            } while (message.length() < 1);
        } catch (SocketException ex) {
            message = "[error] connexion perdue avec le serveur";
            // Logger.getLogger(Client.class.getName()).log(Level.SEVERE, message, ex);
            client.setEtat(Client.ConnexionLost);
            
        } catch (IOException ex) {
            message = "[error] reception depuis serveur a échoué";
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, message, ex);
            client.setEtat(Client.ReceptionFailed);
        } catch (NullPointerException ex) { 
            message = "[error] connexion coupée avec le serveur"; 
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, message, ex);
            client.setEtat(Client.ConnexionBroken);
        }
        return message;
    }

    public void fermer() throws IOException {
        try {
            if (inFromServer != null) {
                inFromServer.close();
            }
        } catch (IOException ex) {
            String message = "[error] socket depuis serveur mal fermé";
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, message, ex);
            throw new IOException(message);
        }
    }
}
