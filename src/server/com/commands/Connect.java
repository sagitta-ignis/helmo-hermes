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
public class Connect implements Command {

    private final Server server;
    private final ClientManager client;

    public Connect(Server server, ClientManager client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void execute() {
        execute("Anonyme");
    }

    @Override
    public void execute(String args) {
        client.setUsername(args);
        server.transmettre("-- "+client.toString()+" a rejoint le serveur");
    }
    
}
