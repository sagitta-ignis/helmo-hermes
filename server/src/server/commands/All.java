/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import hermes.protocole.message.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.command.CommandArgument;
import server.client.ClientManager;
import server.controlleurs.ChannelControlleur;
import server.response.SentAll;

/**
 *
 * @author David
 */
public class All extends CommandArgument {

    private final SentAll sentAll;
    private final ClientManager client;

    public All(ChannelControlleur channelManager, ClientManager client) {
        this.sentAll = new SentAll(client,channelManager);
        this.client = client;
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String messageUtilisateur = message.get(ProtocoleSwinen.message);
        sentAll.sent(client.getClient().getUsername(), messageUtilisateur);
    }

}
