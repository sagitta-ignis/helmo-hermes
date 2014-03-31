/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.etat;

import hermes.protocole.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.CommandArgument;
import pattern.command.Command;
import server.ServerControleur;
import server.com.Client;
import server.com.ClientManager;
import server.com.commands.Hello;
import server.com.commands.Register;
import server.com.response.SentResponse;
import server.configuration.ListUser;

/**
 *
 * @author David
 */
public class Waiting extends EtatAbstract {

    private final ClientManager manager;
    private final SentResponse response;
    private final Client clientInfo;
    private final ListUser listeUtilisateurs;
    private final ServerControleur server;

    public Waiting(ClientManager clientManager, Client client, ListUser listeUtilisateurs, SentResponse response, ServerControleur server) {
        super(response);
        this.manager = clientManager;
        this.response = response;
        this.clientInfo = client;
        this.listeUtilisateurs = listeUtilisateurs;
        this.server = server;        
    }

    @Override
    public void initialiserCommandes() {
        commandsProtocole.put(ProtocoleSwinen.HELLO, new Hello(manager, clientInfo, listeUtilisateurs, response, server));
        commandsProtocole.put(ProtocoleSwinen.REGISTER, new Register());
    }

    private void traiter(MessageProtocole messageProtocole) {
        Command command = commandsProtocole.get(messageProtocole);

        if (command != null) {
            ((CommandArgument) command).setArgs(messageProtocole);
            command.execute();

        } else {
            response.sent(9);
            manager.close();
        }
    }

}
