/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.CommandArgument;
import server.com.Client;
import server.com.response.SentAll;

/**
 *
 * @author David
 */
public class All extends CommandArgument {

    private final SentAll sentAll;
    private final Client client;

    public All(SentAll sentAll, Client client) {
        this.sentAll = sentAll;
        this.client = client;
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String messageUtilisateur = message.get(ProtocoleSwinen.message);
        sentAll.sent(client.getUsername(), messageUtilisateur);
    }

}
