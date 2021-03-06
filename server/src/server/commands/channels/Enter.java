/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.channels;

import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.message.MessageProtocole;
import pattern.command.CommandArgument;
import server.channels.Channel;
import server.client.Client;
import server.client.ClientManager;
import server.controlleurs.ChannelControlleur;
import server.response.SentResponse;
import server.response.channels.SentJoinChannel;

/**
 *
 * @author David
 */
public class Enter extends CommandArgument {

    private final ChannelControlleur manager;
    private final SentResponse sentResponse;
    private final ClientManager clientManager;
    private final SentJoinChannel sentJoin;

    public Enter(ChannelControlleur manager, ClientManager clientManager) {
        this.manager = manager;
        this.clientManager = clientManager;
        sentResponse = new SentResponse(clientManager);
        sentJoin = new SentJoinChannel(manager);
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String nomChannel = message.get(ProtocoleSwinen.channel);
        String motDePasse = message.get(ProtocoleSwinen.pass);

        Channel channel = manager.getChannel(nomChannel);

        if (channel == null) {
            sentResponse.sent(1);
            return;
        }

        if (channel.getMotDePasse() != null && motDePasse == null) {
            sentResponse.sent(5);
            return;
        }

        if (channel.getMotDePasse() != null && !channel.getMotDePasse().equals(motDePasse)) {
            sentResponse.sent(4);
            return;
        }

        String user = clientManager.getClient().getUsername();
        boolean alreadyin = false;
        for (ClientManager cm : channel.getclientsChannel()) {
            Client c = cm.getClient();
            if (c.getUsername().equals(user)) {
                sentResponse.sent(2);
                alreadyin = true;
                break;
            }
        }
        if (!alreadyin) {
            manager.loggerUnSeulChannel(nomChannel, clientManager.getClient().getUsername(), message.toString());
            channel.ajouterUtilisateurChannel(clientManager);
            sentResponse.sent(0);
            sentJoin.sent(nomChannel, user);
        }
    }

}
