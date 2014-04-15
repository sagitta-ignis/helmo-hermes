/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.message.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.List;
import pattern.command.CommandArgument;
import server.ServerControleur;
import server.com.Client;
import server.com.ClientManager;
import server.com.response.SentJoin;
import server.com.response.SentResponse;
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
    private final ServerControleur server;

    public Hello(ClientManager clientManager, Client client, ListUser listeUtilisateurs, SentResponse response, ServerControleur server) {
        manager = clientManager;
        clientInfo = client;
        this.response = response;

        this.server = server;
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

    private boolean verifierSiUtilisateurPasDejaConnecte(String pseudo) {
        List<ClientManager> usersConnected = server.getConnected();

        for (ClientManager clientConnected : usersConnected) {
            if (clientConnected.getClient().getUsername().equals(pseudo)) {
                return false;
            }
        }

        return true;
    }

    private void fermerConnection(int idErreur) {
        response.sent(idErreur);
        manager.close();
    }

    private void connectionAvecSucces(String pseudo) {
        clientInfo.setUsername(pseudo);
        SentJoin sentJoin = new SentJoin(manager, server);
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

            if (verifierSiUtilisateurPasDejaConnecte(pseudo)) {

                connectionAvecSucces(pseudo);
            } else {
                fermerConnection(2);
            }
        } else {
            fermerConnection(1);
        }
    }

}
