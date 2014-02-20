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
public class Mute implements Command {

    private final Server server;

    public Mute(Server server) {
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
            cl.setMuet(true);
            message.append(cl.getUsername());
            message.append(" est maintenant muet.");

            server.transmettre(message.toString());
        }
    }

    @Override
    public String desciption() {
        return "Mute un utilisateur";
    }
}
