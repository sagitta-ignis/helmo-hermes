/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.MessageProtocole;
import pattern.Command;
import server.ServerControleur;
import server.com.ClientManager;

/**
 *
 * @author David
 */
public class Quit implements Command {

    private ClientManager client;
    private ServerControleur serveur;
    private final All sendAll;

    public Quit(ClientManager client, ServerControleur serveur, Command sendAll) {
        this.client = client;
        this.serveur = serveur;
        this.sendAll = (All) sendAll;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute(MessageProtocole message) {
        sendAll.execute("Serveur", client.getClient().getUsername() + " deconnecte");
        serveur.afficher(client.getClient().getUsername() + " déconnecté");
        client.close();
    }

}
