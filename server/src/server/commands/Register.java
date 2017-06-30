/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import hermes.protocole.message.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.command.CommandArgument;
import server.controlleurs.ChannelControlleur;
import server.response.SentResponse;

/**
 *
 * @author David
 */
public class Register extends CommandArgument {

    private final ChannelControlleur channelManager;
    private final SentResponse response;
    
    public Register(ChannelControlleur channelManager, SentResponse response) {
        this.channelManager = channelManager;
         this.response = response;
    }

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String username = message.get(ProtocoleSwinen.user);
        String password = message.get(ProtocoleSwinen.pass);

        if(channelManager.enregistrerUtilisateur(username, password)){
            response.sent(0);
        }
        else{
            response.sent(3);
        }
        
    }

}
