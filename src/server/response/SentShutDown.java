/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.response;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controlleurs.ChannelControlleur;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class SentShutDown {

    private final Protocole protocole;
    private final ChannelControlleur channelManager;

    public SentShutDown(ChannelControlleur channelManager) {
        this.protocole = new ProtocoleSwinen();
        this.channelManager = channelManager;
    }

    public void sent() {

        protocole.prepare(ProtocoleSwinen.SERVERSHUTDOWN);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make();
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        channelManager.transmettre(messageProtocole);
    }
}
