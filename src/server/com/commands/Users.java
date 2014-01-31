/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import java.util.List;
import pattern.Command;
import server.Server;
import server.com.ClientManager;

/**
 *
 * @Author David M
 */

public class Users implements Command {

    private final Server server;
    private final ClientManager clientManager;
    
    public Users(Server server, ClientManager cl) {
        this.server = server;
        this.clientManager = cl;
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(String args) {
        
        List<ClientManager> clients = server.getConnected();
        
        StringBuilder message = new StringBuilder();
        
        for(ClientManager client: clients){
                         
            message.append(client.getClient().toString());
            if(client.getClient().isMuet()) message.append(" - Muet");
            message.append(" - ");
            message.append(client.getClient().getTimeConnected());
            message.append(" minutes \n");
            
        }
        
        clientManager.envoyer(message.toString());
    }
    
    
    @Override
    public String desciption() {
        return "Affiche les utilisateurs connecté";
    }
}
