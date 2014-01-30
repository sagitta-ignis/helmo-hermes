/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import server.com.ClientManager;
import pattern.Command;
import server.Server;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Quit implements Command {

    private final Server server;
    private final ClientManager client;

    public Quit(Server server, ClientManager client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(String args) {
        StringBuilder message = new StringBuilder();
        message.append("-- ").append(client.getUserName());
        message.append("(").append(client.getClientId()).append(") ");
        message.append("a quitté le serveur");
        if(args!=null && !args.isEmpty()) {
            message.append(" (").append(args).append(")");
        }
        server.transmettre(message.toString());
        server.retirer(client);
        client.setOpened(false);
        client.close();
    }
}
