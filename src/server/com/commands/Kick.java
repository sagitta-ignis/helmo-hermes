/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import pattern.Command;
import server.Server;
import server.com.Client;

/**
 *
 * @Author David M
 */

public class Kick implements Command {

    private final Server server;

    public Kick(Server server) {
        this.server = server;
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(String args) {
        
    }

    @Override
    public String desciption() {
        return "kick un utilisateur";
    }
}
