/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.ServerControleur;
import server.com.Client;
import server.com.etat.Waiting;

/**
 *
 * @author David
 */
public class Typing extends CommandArgument {

    private final ServerControleur server;
    private final Client clientInfo;
    private final Protocole protocole;

    public Typing(ServerControleur server, Client clientInfo) {
        this.server = server;
        this.clientInfo = clientInfo;
        protocole = new ProtocoleSwinen();
    }

    @Override
    public void execute() {

        protocole.prepare(ProtocoleSwinen.STYPING);
        String messageProtocole = "";
      
        try {
            messageProtocole = protocole.make();
        } catch (Exception ex) {
            Logger.getLogger(Typing.class.getName()).log(Level.SEVERE, null, ex);
        }
  

        server.transmettre(messageProtocole);
    }

}
