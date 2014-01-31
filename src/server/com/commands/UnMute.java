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
public class UnMute implements Command {

    private final Server server;

    public UnMute(Server server) {
        this.server = server;
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(String args) {
        StringBuilder message = new StringBuilder();
        Client cl = server.trouverClient(Integer.parseInt(args));

        if (cl != null) {
            cl.setMuet(false);
            message.append(cl.getUsername());
            message.append(" peut maintenant parler.");

            server.transmettre(message.toString());
        }
    }
    
    
    @Override
    public String desciption() {
       return "Unmute un utilisateur";
    }
}
