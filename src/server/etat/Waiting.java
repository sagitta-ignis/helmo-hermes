/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.etat;

import hermes.protocole.message.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.command.Command;
import pattern.command.CommandArgument;
import server.controlleur.ChannelControlleur;
import server.controlleur.ServeurControlleur;
import server.client.Client;
import server.client.ClientManager;
import server.commands.Hello;
import server.commands.Register;
import server.response.SentResponse;
import server.configuration.ListUser;

/**
 *
 * @author David
 */
public class Waiting extends EtatAbstract {

    private final ClientManager manager;
    private final SentResponse response;
    private final Client clientInfo;
    private final ListUser listeUtilisateurs;
    private final ChannelControlleur channelManager;

    public Waiting(ClientManager clientManager, Client client, ListUser listeUtilisateurs, SentResponse response, ChannelControlleur channelManager) {
        super(response);
        this.manager = clientManager;
        this.response = response;
        this.clientInfo = client;
        this.listeUtilisateurs = listeUtilisateurs;
        this.channelManager = channelManager;        
    }

    @Override
    public void initialiserCommandes() {
        commandsProtocole.put(ProtocoleSwinen.HELLO, new Hello(manager, clientInfo, listeUtilisateurs, response, channelManager));
        commandsProtocole.put(ProtocoleSwinen.REGISTER, new Register());
    }

    @Override
    protected void traiter(MessageProtocole messageProtocole) {
        Command command = commandsProtocole.get(messageProtocole);

        if (command != null) {
            ((CommandArgument) command).setArgs(messageProtocole);
            command.execute();
        } else {
            response.sent(9);
            manager.close();
        }
    }

}
