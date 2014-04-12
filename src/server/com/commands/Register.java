/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import hermes.protocole.message.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.command.CommandArgument;

/**
 *
 * @author David
 */
public class Register extends CommandArgument {

    @Override
    public void execute() {
        MessageProtocole message = (MessageProtocole) args[0];
        String username = message.get(ProtocoleSwinen.user);
        String password = message.get(ProtocoleSwinen.pass);
    }

}
