/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.Command;
import server.ServerControleur;
import server.com.Client;
import server.com.etat.Waiting;

/**
 *
 * @author David
 */
public class All implements Command {

    private final ServerControleur server;
    private final Client client;

    public All(ServerControleur server, Client client) {
        this.server = server;
        this.client = client;
    }

    public void execute(String auteur, String message) {
        Protocole protocole = new ProtocoleSwinen();

        protocole.prepare(ProtocoleSwinen.SALL);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.sender, auteur),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, message)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        server.transmettre(messageProtocole);
    }

    @Override
    public void execute(MessageProtocole message) {
        String messageUtilisateur = message.get(ProtocoleSwinen.message);

        execute(client.getUsername(),messageUtilisateur);       
        server.afficher(client.getUsername()+": " +messageUtilisateur);        
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
