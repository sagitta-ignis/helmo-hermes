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
import server.controlleur.ChannelControlleur;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class SentLeaveChannel {

    private final Protocole protocole;
    private final ChannelControlleur channelManager;

    public SentLeaveChannel(ChannelControlleur channelManager) {
        protocole = new ProtocoleSwinen();
        this.channelManager = channelManager;
    }

    public void sent(String nomChannel, String user) {
        
        protocole.prepare(ProtocoleSwinen.LEAVECHANNEL);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object)nomChannel),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user,(Object) user)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        channelManager.transmettreChannel(nomChannel, messageProtocole);
    }
}
