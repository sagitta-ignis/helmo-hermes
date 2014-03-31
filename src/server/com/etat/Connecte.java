/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.etat;

import hermes.protocole.ProtocoleSwinen;
import server.ServerControleur;
import server.com.Client;
import server.com.ClientManager;
import server.com.commands.All;
import server.com.commands.Msg;
import server.com.commands.Quit;
import server.com.commands.Typing;
import server.com.commands.Users;
import server.com.response.SentAll;
import server.com.response.SentResponse;

/**
 *
 * @author David
 */
public class Connecte extends EtatAbstract {

    private final Client client;
    private final SentAll sentAll;
    private final ServerControleur server;
    private final SentResponse response;
    private final ClientManager manager;
    
    public Connecte(Client client, SentAll sentAll, ServerControleur server, SentResponse response, ClientManager manager) {
        super(response);
        this.client = client;
        this.sentAll = sentAll;
        this.server = server;
        this.response = response;
        this.manager = manager;
    }

    @Override
    public void initialiserCommandes() {
        commandsProtocole.put(ProtocoleSwinen.ALL, new All(sentAll, client));
        commandsProtocole.put(ProtocoleSwinen.MSG, new Msg(server, response));
        commandsProtocole.put(ProtocoleSwinen.QUIT, new Quit(manager, server));
        commandsProtocole.put(ProtocoleSwinen.USERS, new Users());
        commandsProtocole.put(ProtocoleSwinen.TYPING, new Typing(server, client));
    }
}
