/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.CommandArgument;
import server.ServerControleur;
import server.com.Client;
import server.com.ClientManager;
import server.com.etat.Waiting;
import server.com.response.SentResponse;

/**
 *
 * @author David
 */
public class Msg extends CommandArgument {

    private final Client client;
    private final ServerControleur serveur;
    private final SentResponse response;
    private List<ClientManager> clientConnecte;

    public Msg(Client client, ServerControleur serveur, SentResponse response) {
        this.client = client;
        this.serveur = serveur;
        this.response = response;
    }

    private void transmettre(ClientManager client, String text, String auteur) {
        Protocole protocole = new ProtocoleSwinen();
        protocole.prepare(ProtocoleSwinen.SMSG);
        String message = "";
        try {
            message = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.sender, auteur),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, text)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        serveur.afficher(auteur + " -> " + client.getClient().getUsername() + ": " + text);
        client.envoyer(message);
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        boolean found = false;
        String destinataire = message.get(ProtocoleSwinen.receiver);
        String text = message.get(ProtocoleSwinen.message);
        clientConnecte = serveur.getConnected();

        for (ClientManager clientManager : clientConnecte) {
            if (clientManager.getClient().getUsername().equals(destinataire)) {
                found = true;
                response.sent(0);
                transmettre(clientManager, text, this.client.getUsername());
            }
        }
        if (!found) {
            response.sent(1);
        }
    }
}
