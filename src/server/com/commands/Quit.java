/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import pattern.CommandArgument;
import server.com.ClientManager;
import server.com.response.SentAll;

/**
 *
 * @author David
 */
public class Quit extends CommandArgument {

    private final ClientManager client;
    private final SentAll sentAll;

    public Quit(ClientManager client, SentAll sentAll) {
        this.client = client;
        this.sentAll = sentAll;
    }

    @Override
    public void execute() {
        sentAll.sent("Serveur", client.getClient().getUsername() + " deconnecte");
        client.close();
    }

}
