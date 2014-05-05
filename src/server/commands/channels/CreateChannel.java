/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.channels;

import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.message.MessageProtocole;
import pattern.command.CommandArgument;
import server.controlleur.ChannelControlleur;
import server.channels.Channel;
import server.client.ClientManager;
import server.response.SentResponse;
import server.response.channels.SentChannelAdded;

/**
 *
 * @author David
 */
public class CreateChannel extends CommandArgument {

    private final ChannelControlleur manager;
    private final ClientManager clientManager;
    private final SentResponse sentResponse;
    private final SentChannelAdded sentChannelAdded;    

    public CreateChannel(ChannelControlleur manager, ClientManager clientManager) {
        this.manager = manager;
        this.clientManager = clientManager;
        sentResponse = new SentResponse(clientManager);
        sentChannelAdded = new SentChannelAdded(manager);
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String nomChannel = message.get(ProtocoleSwinen.channel);
        String motDePasse = message.get(ProtocoleSwinen.pass);

        if(nomChannel == null){
            sentResponse.sent(9);
            return;
        }
        
        if (manager.getChannel(nomChannel) != null) {
            sentResponse.sent(2);
            return;
        }

        Channel nouveauChannel = manager.ajouterChannel(nomChannel);
        nouveauChannel.setAdministrateur(clientManager.getClient().getUsername());
        if (motDePasse != null) {
            nouveauChannel.setMotDePasse(motDePasse);
        }
        
        nouveauChannel.ajouterUtilisateurChannel(clientManager);
        sentResponse.sent(0);
        sentChannelAdded.sent(nomChannel);
    }

}
