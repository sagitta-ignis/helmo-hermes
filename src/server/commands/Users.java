/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.ArrayList;
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
public class Users extends CommandArgument {

    private final Protocole protocole;
    private final ClientManager client;
    private final ChannelControlleur channelManager;

    public Users(ClientManager client,ChannelControlleur channelManager) {
        protocole = new ProtocoleSwinen();
        this.client = client;
        this.channelManager = channelManager;
    }

    @Override
    public void execute() {
        List<String> utilisateurs = creationListeConnecte();        
        protocole.prepare(ProtocoleSwinen.SUSERS);
        String messageProtocole = "";
       
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user, (Object)utilisateurs)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        client.afficherToutLesChannels(messageProtocole);
        client.envoyer(messageProtocole);
    }

    private List<String> creationListeConnecte() {
        List<ClientManager> clientConnected = channelManager.getConnected();
        List<String> nomClient = new ArrayList<>();

        for (ClientManager client : clientConnected) {
            nomClient.add(client.getClient().getUsername());
        }
        
        return nomClient;
    }

}
