/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import hermes.protocole.message.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.List;
import pattern.command.CommandArgument;
import server.controlleur.ChannelControlleur;
import server.client.Client;
import server.client.ClientManager;
import server.response.SentJoin;
import server.response.SentResponse;
import server.configuration.ListUser;
import server.configuration.User;

/**
 *
 * @author David
 */
public class Hello extends CommandArgument {

    private final ClientManager manager;
    private final Client clientInfo;
    private final ListUser utilisateurs;
    private final SentResponse response;
    private final ChannelControlleur channelManager;

    public Hello(ClientManager clientManager, Client client, ListUser listeUtilisateurs, SentResponse response, ChannelControlleur channelManager) {
        manager = clientManager;
        clientInfo = client;
        this.response = response;

        this.channelManager = channelManager;
        utilisateurs = listeUtilisateurs;

    }

    private boolean verifierParametresConnection(String pseudo, String motDePasse) {
        if (pseudo != null && motDePasse != null) {
            for (User myUser : utilisateurs.getUsers()) {
                if (myUser.getNickname().equals(pseudo) && myUser.getPassword().equals(motDePasse)) {
                    return true;
                    // connectionAvecSucces(pseudo);
                }
            }
        }
        return false;
    }

    private void fermerConnection(int idErreur) {
        response.sent(idErreur);
        manager.close();
    }

    private void connectionAvecSucces(String pseudo) {
        clientInfo.setUsername(pseudo);
        SentJoin sentJoin = new SentJoin(manager, channelManager);
        sentJoin.sent();

        clientInfo.setEtat(3);
        response.sent(0);
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String pseudo = message.get(ProtocoleSwinen.user);
        String motDePasse = message.get(ProtocoleSwinen.pass);

        if (verifierParametresConnection(pseudo, motDePasse)) {

            if (channelManager.clientConnected(pseudo) == null) {

                connectionAvecSucces(pseudo);
            } else {
                fermerConnection(2);
            }
        } else {
            fermerConnection(1);
        }
    }

}
