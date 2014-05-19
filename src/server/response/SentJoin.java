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
import server.controlleurs.ChannelControlleur;
import server.client.ClientManager;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class SentJoin {

    private final Protocole protocole;
    private final ClientManager client;
    private final ChannelControlleur channelManager;

    public SentJoin(ClientManager client, ChannelControlleur channelManager) {
        this.protocole = new ProtocoleSwinen();
        this.client = client;
        this.channelManager = channelManager;
    }

    public void sent() {

        protocole.prepare(ProtocoleSwinen.JOIN);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user,(Object)client.getClient().getUsername())
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        channelManager.loggerToutLesChannels(client.getClient().getUsername(),messageProtocole);
        channelManager.transmettre(messageProtocole);
    }
}
