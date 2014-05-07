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
import server.channels.Channel;
import server.client.ClientManager;
import server.response.SentResponse;
import server.response.channels.SentChannelRemoved;

/**
 *
 * @author David
 */
public class DeleteChannel extends CommandArgument {

    private final ChannelControlleur manager;
    private final SentResponse sentResponse;
    private final ClientManager clientManager;
    private final SentChannelRemoved sentChannelRemoved;
    private Channel channel;

    public DeleteChannel(ChannelControlleur manager, ClientManager clientManager) {
        this.clientManager = clientManager;
        this.manager = manager;
        sentResponse = new SentResponse(clientManager);
        sentChannelRemoved = new SentChannelRemoved(manager);
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String nomChannel = message.get(ProtocoleSwinen.channel);

        if (nomChannel == null) {
            sentResponse.sent(9);
            return;
        }
        channel = manager.getChannel(nomChannel);

        if (channel == null) {
            sentResponse.sent(2);
            return;
        }

        if(!channel.getAdministrateur().equals(clientManager.getClient().getUsername())){
            sentResponse.sent(4);
            return;
        }
        
        manager.supprimerChannel(nomChannel);
        sentResponse.sent(0);
        sentChannelRemoved.sent(nomChannel);
    }

}
