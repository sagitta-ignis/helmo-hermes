/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.channels;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.message.MessageProtocole;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.channels.Channel;
import server.controlleurs.ChannelControlleur;
import server.client.ClientManager;
import server.etat.Waiting;
import server.response.SentResponse;

/**
 *
 * @author David
 */
public class UsersChannel extends CommandArgument {

    private final ChannelControlleur manager;
    private final ClientManager clientManager;
    private final Protocole protocole;
    private final SentResponse response;

    public UsersChannel(ChannelControlleur manager, ClientManager clientManager) {
        this.manager = manager;
        this.clientManager = clientManager;
        protocole = new ProtocoleSwinen();
        response = new SentResponse(clientManager);
    }

    @Override
    public void execute() {

        MessageProtocole message = (MessageProtocole) args[0];
        String nomChannel = message.get(ProtocoleSwinen.channel);

        Channel channel = manager.getChannel(nomChannel);
        if (channel == null) {
            response.sent(1);
            return;
        }

        protocole.prepare(ProtocoleSwinen.SUSERSCHANNEL);
        String messageProtocole = "";

        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object) nomChannel),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user, (Object) creationListeUsers(channel))
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        clientManager.envoyer(messageProtocole);
    }

    private List<String> creationListeUsers(Channel channel) {
        List<String> users = new ArrayList<>();

        for (ClientManager client : channel.getclientsChannel()) {
            users.add(client.getClient().getUsername());
        }
        return users;
    }

}
