/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.response;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controlleur.ChannelControlleur;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class SentAll {

    private final Protocole protocole;
    private final ChannelControlleur channelManager;

    public SentAll(ChannelControlleur channelManager) {
        protocole = new ProtocoleSwinen();
        this.channelManager = channelManager;
    }

    public void sent(String auteur, String message) {
        
        protocole.prepare(ProtocoleSwinen.SALL);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.sender, (Object)auteur),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message,(Object) message)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        channelManager.afficher(messageProtocole);
        channelManager.transmettre(messageProtocole);

    }
}
