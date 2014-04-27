/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.response;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerControleur;
import server.com.ClientManager;
import server.com.etat.Waiting;

/**
 *
 * @author David
 */
public class SentShutDown {

    private final Protocole protocole;
    private final ServerControleur server;

    public SentShutDown(ServerControleur server) {
        this.protocole = new ProtocoleSwinen();
        this.server = server;
    }

    public void sent() {

        protocole.prepare(ProtocoleSwinen.SERVERSHUTDOWN);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make();
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        server.transmettre(messageProtocole);
    }
}
