/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import hermes.protocole.message.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.controlleur.ChannelControlleur;
import server.client.Client;
import server.client.ClientManager;
import server.etat.Waiting;
import server.response.SentResponse;

/**
 *
 * @author David
 */
public class Msg extends CommandArgument {

    private final Client client;
    private final ChannelControlleur channeManager;
    private final SentResponse response;

    public Msg(Client client, ChannelControlleur channeManager, SentResponse response) {
        this.client = client;
        this.channeManager = channeManager;
        this.response = response;
    }

    private void transmettre(ClientManager client, String text, String auteur) {
        Protocole protocole = new ProtocoleSwinen();
        protocole.prepare(ProtocoleSwinen.SMSG);
        String message = "";
        try {
            message = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.sender, (Object) auteur),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, (Object) text)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        channeManager.afficher(message);
        client.envoyer(message);
    }

    @Override
    public void execute() {        
        MessageProtocole message = (MessageProtocole) args[0];
        String destinataire = message.get(ProtocoleSwinen.receiver);
        String text = message.get(ProtocoleSwinen.message);
        ClientManager clientDestinataire;

        if (destinataire.equals(client.getUsername())) {
            response.sent(3);
            return;
        } else if ((clientDestinataire = channeManager.clientConnected(destinataire)) != null) {
            response.sent(0);
            transmettre(clientDestinataire, text, client.getUsername());
        } else {
            response.sent(1);
        }
    }
}
