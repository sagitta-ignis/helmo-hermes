/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.com.commands;

import server.com.ClientManager;
import pattern.Command;
import server.Server;
import server.com.Client;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Connect implements Command {

    private final Server server;
    private final Client clientInfo;

    public Connect(Server server, Client client) {
        this.server = server;
        this.clientInfo = client;
    }

    @Override
    public void execute() {
        execute("Anonyme");
    }

    @Override
    public void execute(String args) {
        clientInfo.setUsername(args);
        server.transmettre("-- "+clientInfo.toString()+" a rejoint le serveur");
    }

    @Override
    public String desciption() {
        return "Change de pseudo";
    }
    
}
