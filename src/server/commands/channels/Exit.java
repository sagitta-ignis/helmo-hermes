/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.channels;

import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.message.MessageProtocole;
import pattern.command.CommandArgument;
import server.controlleurs.ChannelControlleur;
import server.client.ClientManager;
import server.response.SentResponse;
import server.response.channels.SentChannelRemoved;
import server.response.channels.SentLeaveChannel;

/**
 *
 * @author David
 */
public class Exit extends CommandArgument {

    private final ChannelControlleur manager;
    private final ClientManager clientManager;
    private final SentResponse sentResponse;
    private final SentLeaveChannel sentLeave;
    private final SentChannelRemoved sentRemoved;

    public Exit(ChannelControlleur manager, ClientManager clientManager) {
        this.manager = manager;
        this.clientManager = clientManager;
        sentResponse = new SentResponse(clientManager);
        sentLeave = new SentLeaveChannel(manager);
        sentRemoved = new SentChannelRemoved(manager);
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String nomChannel = message.get(ProtocoleSwinen.channel);

        if (manager.getChannel(nomChannel) == null) {
            sentResponse.sent(1);
            return;
        }
        
        sentLeave.sent(nomChannel,clientManager.getClient().getUsername());
        manager.retirerUtilisateurChannel(nomChannel, clientManager);
        sentResponse.sent(0);
        if(manager.getChannel(nomChannel) == null) {
            sentRemoved.sent(nomChannel);
        }
    }

}
