/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.Command;
import server.ServerControleur;
import server.com.Client;
import server.com.ClientManager;
import server.com.response.SentResponse;
import server.configuration.ListUser;
import server.configuration.User;

/**
 *
 * @author David
 */
public class Hello implements Command {

    private final ClientManager manager;
    private final Client clientInfo;
    private final ListUser utilisateurs;
    private final SentResponse response;
    private final ServerControleur serveur;
    private final All sendAll;

    public Hello(ClientManager clientManager, Client client, ListUser listeUtilisateurs, SentResponse response, ServerControleur serveur, Command sendAll) {
        manager = clientManager;
        clientInfo = client;
        this.response = response;
        this.serveur = serveur;
        this.sendAll = (All) sendAll;
        utilisateurs = listeUtilisateurs;

    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(MessageProtocole message) {
        String pseudo = message.get(ProtocoleSwinen.user);
        String motDePasse = message.get(ProtocoleSwinen.pass);

        if (pseudo != null && motDePasse != null) {
            for (User myUser : utilisateurs.getUsers()) {
                if (myUser.getNickname().equals(pseudo) && myUser.getPassword().equals(motDePasse)) {
                    connectionAvecSucces(pseudo);
                }
            }
        }
        if (!clientInfo.isAccepte()) {
            response.response(1);
            manager.close();
        }
    }

    private void connectionAvecSucces(String pseudo) {
        clientInfo.setAccepte(true);
        clientInfo.setUsername(pseudo);

        response.response(0);
        serveur.afficher(pseudo + " connecté");
        sendAll.execute("Serveur", pseudo + " connecte");

    }

}
