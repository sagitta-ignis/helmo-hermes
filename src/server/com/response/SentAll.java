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
import server.com.etat.Waiting;

/**
 *
 * @author David
 */
public class SentAll {

    private final Protocole protocole;
    private final ServerControleur server;

    public SentAll(ServerControleur server) {
        protocole = new ProtocoleSwinen();
        this.server = server;
    }

    public void sent(String auteur, String message) {
        
        protocole.prepare(ProtocoleSwinen.SALL);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.sender, auteur),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, message)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        server.afficher(auteur + ": " + message);
        server.transmettre(messageProtocole);

    }
}
