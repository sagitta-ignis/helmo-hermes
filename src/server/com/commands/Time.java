/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import pattern.Command;
import server.Server;
import server.com.Client;
import server.com.ClientManager;

/**
 *
 * @Author David M
 */
public class Time implements Command {

    private final Server server;
    private final Client clientInfo;
    private final ClientManager clManager;

    public Time(Server server, Client clientInfo, ClientManager clientManager) {
        this.server = server;
        this.clientInfo = clientInfo;
        this.clManager = clientManager;
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(String args) {
        int min;
        StringBuilder message = new StringBuilder();
        message.append("--Connecté depuis ");

        if ((min = clientInfo.getTimeConnected()) == 0) {
            message.append("moins de une minute");
        } else {
            message.append(min);
            message.append(" minutes.");
        }

        clManager.envoyer(message.toString());
    }
    
    
    @Override
    public String desciption() {
        return "Temps connecté sur le serveur";
    }
}
