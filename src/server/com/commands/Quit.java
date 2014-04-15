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
import server.com.ClientManager;
import server.com.etat.Waiting;

/**
 *
 * @author David
 */
public class Quit extends CommandArgument {

    private final Protocole protocole;
    private final ClientManager client;
    private final ServerControleur server;

    public Quit(ClientManager client,ServerControleur server) {
        protocole = new ProtocoleSwinen();
        this.client = client;
        this.server = server;
    }

    @Override
    public void execute() {
        sent();
        client.close();

    }

    private void sent() {

        protocole.prepare(ProtocoleSwinen.LEAVE);
        String messageProtocole = "";
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user,(Object) client.getClient().getUsername())
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        server.afficher(messageProtocole);
        server.transmettre(messageProtocole);

    }

}
