/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.etat;

import server.ServerControleur;
import server.com.Client;
import server.com.ClientManager;
import server.configuration.ListUser;
import server.configuration.User;

/**
 *
 * @author David
 */
public class Waiting {

    private int tentativesConnection;
    private final ClientManager manager;
    private final ServerControleur serveur;
    private final Client clientInfo;
    private final ListUser utilisateurs;

    public Waiting(ClientManager clientManager, ServerControleur server, Client client, ListUser listeUtilisateurs) {
        manager = clientManager;
        serveur = server;
        clientInfo = client;
        tentativesConnection = 0;
        utilisateurs = listeUtilisateurs;
    }

    public void traiter(String message) {
        
        String[] r = message.split(" ", 3);
        if (r[0].equals("/connect")) {
            if (r.length == 3) {
                verifierDonnees(r[1], r[2]);
            }
        }

        tentativesConnection++;
        nombresDeConnectionValides();
    }

    private void nombresDeConnectionValides() {
        if (tentativesConnection >= 3) {
            manager.close();
        }
    }

    private void verifierDonnees(String pseudo, String motDePasse) {
        if (pseudo != null && motDePasse != null) {
            for (User myUser : utilisateurs.getUsers()) {
                if (myUser.getNickname().equals(pseudo) && myUser.getPassword().equals(motDePasse)) {
                    connectionAvecSucces(pseudo);
                }
            }
        }
    }

    private void connectionAvecSucces(String pseudo) {
        clientInfo.setAccepte(true);
        clientInfo.setUsername(pseudo);
        serveur.transmettre(pseudo + " connecté");
        manager.envoyer("Bienvenue sur Hermes !");
    }
}
