/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.channels;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.controlleurs.ChannelControlleur;
import server.client.ClientManager;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class Channels extends CommandArgument {

    private final ChannelControlleur manager;
    private final ClientManager clientManager;
    private final Protocole protocole;

    public Channels(ChannelControlleur manager, ClientManager clientManager) {
        this.manager = manager;
        this.clientManager = clientManager;
        protocole = new ProtocoleSwinen();
    }

    @Override
    public void execute() {

        List<String> channelList = manager.getChannelList();

        protocole.prepare(ProtocoleSwinen.SCHANNELS);
        String messageProtocole = "";

        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object) channelList)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        clientManager.envoyer(messageProtocole);
    }

}
