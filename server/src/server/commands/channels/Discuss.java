/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.channels;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.message.MessageProtocole;
import hermes.protocole.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.channels.Channel;
import server.client.ClientManager;
import server.controlleurs.ChannelControlleur;
import server.etat.Waiting;
import server.response.SentResponse;

/**
 *
 * @author David
 */
public class Discuss extends CommandArgument {

    private final ChannelControlleur manager;
    private final ClientManager clientManager;
    private final Protocole protocole;
    private final SentResponse response;

    public Discuss(ChannelControlleur manager, ClientManager clientManager) {
        this.manager = manager;
        this.clientManager = clientManager;
        protocole = new ProtocoleSwinen();
        response = new SentResponse(clientManager);
    }

    @Override
    public void execute() {

        MessageProtocole message = (MessageProtocole) args[0];
        String nomChannel = message.get(ProtocoleSwinen.channel);
        String msg = message.get(ProtocoleSwinen.message);
        
        Channel c = manager.getChannel(nomChannel);
        if(c == null){
            response.sent(1);
            return;
        }
        
        
        protocole.prepare(ProtocoleSwinen.SDISCUSS);
        String messageProtocole = "";

        try {
            messageProtocole = protocole.make(
                    new Entry<>(ProtocoleSwinen.channel, (Object) nomChannel),
                    new Entry<>(ProtocoleSwinen.user, (Object) clientManager.getClient().getUsername()),
                    new Entry<>(ProtocoleSwinen.message, (Object) msg)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        manager.loggerUnSeulChannel(nomChannel,clientManager.getClient().getUsername(), message.toString());
        manager.transmettreChannel(nomChannel, messageProtocole);
    }

}
