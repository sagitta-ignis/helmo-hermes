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
import pattern.Command;
import server.ServerControleur;
import server.com.ClientManager;
import server.com.etat.Waiting;
import server.com.response.SentResponse;

/**
 *
 * @author David
 */
public class Msg implements Command {

    private final ServerControleur serveur;
    private final SentResponse response;
    private List<ClientManager> clientConnecte;

    public Msg(ServerControleur serveur, SentResponse response) {
        this.serveur = serveur;
        this.response = response;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute(MessageProtocole message) {
        boolean found = false;
        String destinataire = message.get(ProtocoleSwinen.receiver);
        String text = message.get(ProtocoleSwinen.message);
        clientConnecte = serveur.getConnected();

        for (ClientManager client : clientConnecte) {
            if (client.getClient().getUsername().equals(destinataire)) {
                found = true;                
                transmettre(client, text, client.getClient().getUsername());

            }
        }

        if (!found) {
            response.response(1);
        } else {
            response.response(0);
        }

    }

    private void transmettre(ClientManager client, String text, String auteur) {
        Protocole protocole = new ProtocoleSwinen();
        protocole.prepare(ProtocoleSwinen.SMSG);
        String message = "";
        try {
            message = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.sender,auteur),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message,text)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        serveur.afficher(auteur+" -> "+client.getClient().getUsername()+": "+text);
        client.envoyer(message);
    }

}
