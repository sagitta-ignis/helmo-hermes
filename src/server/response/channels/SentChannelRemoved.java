/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.response.channels;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controlleurs.ChannelControlleur;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class SentChannelRemoved {

    private final Protocole protocole;
    private final ChannelControlleur channelManager;

    public SentChannelRemoved (ChannelControlleur channelManager) {
        protocole = new ProtocoleSwinen();
        this.channelManager = channelManager;
    }

    public void sent(String nomChannel) {
        
        protocole.prepare(ProtocoleSwinen.CHANNELREMOVED);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object)nomChannel)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        channelManager.transmettre(messageProtocole);
    }
}
