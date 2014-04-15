/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class Users extends CommandArgument {

    private final Protocole protocole;
    private final ServerControleur server;

    public Users(ServerControleur server) {
        protocole = new ProtocoleSwinen();
        this.server = server;
    }

    @Override
    public void execute() {
        List<String> utilisateurs = creationListeConnecte();        
        List list = Arrays.asList(new String[] {"Alice", "bobby1", "carl7"});
        protocole.prepare(ProtocoleSwinen.SUSERS);
        String messageProtocole = "";
       
        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user, (Object)utilisateurs)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        server.afficher(messageProtocole);
        server.transmettre(messageProtocole);

    }

    private List<String> creationListeConnecte() {
        List<ClientManager> clientConnected = server.getConnected();
        List<String> nomClient = new ArrayList<>();

        for (ClientManager client : clientConnected) {
            nomClient.add(client.getClient().getUsername());
        }
        
        return nomClient;
    }

}
