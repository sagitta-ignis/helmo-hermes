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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.controlleurs.ChannelControlleur;
import server.channels.Channel;
import server.client.ClientManager;
import server.etat.Waiting;
import server.response.SentResponse;

/**
 *
 * @author David
 */
public class InfoChannel extends CommandArgument {

    private final ChannelControlleur manager;    
    private final SentResponse sentResponse;
    private final ClientManager clientManager;
    private final Protocole protocole;
    
    public InfoChannel(ChannelControlleur manager, ClientManager clientManager) {
        this.manager = manager;
        this.clientManager = clientManager;
        protocole = new ProtocoleSwinen();
        sentResponse = new SentResponse(clientManager);
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String nomChannel = message.get(ProtocoleSwinen.channel);

        Channel channel = manager.getChannel(nomChannel);
        
        if(channel == null){
            sentResponse.sent(1);
            return;
        }
     
        String motDePasse = null;
        if(channel.getMotDePasse() == null){
            motDePasse = "0";
        }else{
            motDePasse = "1";
        }
        
        protocole.prepare(ProtocoleSwinen.STYPING);
        String messageProtocole = "";

        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.digit, (Object) motDePasse),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.entier, (Object) channel.getClientSize())
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        clientManager.envoyer(messageProtocole);
    }

}
