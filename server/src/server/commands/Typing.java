/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.message.MessageProtocole;
import hermes.protocole.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.client.Client;
import server.controlleurs.ChannelControlleur;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class Typing extends CommandArgument {

    private final ChannelControlleur channelManager;
    private final Client clientInfo;
    private final Protocole protocole;

    public Typing(ChannelControlleur channelManager, Client clientInfo) {
        this.channelManager = channelManager;
        this.clientInfo = clientInfo;
        protocole = new ProtocoleSwinen();
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String channel = message.get(ProtocoleSwinen.channel);
        String activite = message.get(ProtocoleSwinen.digit);

        protocole.prepare(ProtocoleSwinen.STYPING);
        String messageProtocole = "";

        try {
            messageProtocole = protocole.make(
                    new Entry<>(ProtocoleSwinen.channel, (Object) channel),
                    new Entry<>(ProtocoleSwinen.user, (Object) clientInfo.getUsername()),
                    new Entry<>(ProtocoleSwinen.digit, (Object) activite)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        channelManager.transmettre(messageProtocole);
    }

}
