/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.command.messages;

import hermes.client.Client;
import hermes.client.command.CommandArgument;
import hermes.protocole.Protocole;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class Message extends CommandArgument {
    
    protected Client client;
    protected Protocole protocole;

    public Message(Client client,Protocole protocole) {
        this.client = client;
        this.protocole = protocole;
    }
    
    protected boolean verifierArguments(int nombre) {
        if(args == null && nombre == 0) return true;
        if(args.length != nombre) return false;
        for (Object arg : args) {
            if(!(arg instanceof String)) return false;
        }
        return true;
    }
}
