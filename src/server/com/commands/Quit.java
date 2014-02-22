/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import server.com.ClientManager;
import pattern.Command;
import server.ServerControleur;
import server.com.Client;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Quit implements Command {

    private final ServerControleur server;
    private final ClientManager client;
    private final Client clientInfo;

    public Quit(ServerControleur server, ClientManager client, Client clInfo) {
        this.server = server;
        this.client = client;
        this.clientInfo = clInfo;
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(String args) {
        StringBuilder message = new StringBuilder();
        message.append("-- ").append(clientInfo.getUsername());
        message.append("(").append(clientInfo.getId()).append(") ");
        message.append("a quitté le serveur");
        if(args!=null && !args.isEmpty()) {
            message.append(" (").append(args).append(")");
        }
        server.transmettre(message.toString());
        server.retirer(client);
        clientInfo.setOpened(false);
        client.close();
    }

    @Override
    public String desciption() {
        return "Quitter le serveur";
    }
}
