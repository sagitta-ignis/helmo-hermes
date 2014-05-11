/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.command.message.base;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.protocole.Protocole;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class Message extends CommandArgument {

    protected final Chatter chat;

    public Message(Chatter chat) {
        this.chat = chat;
    }
    
    protected void waitResponse() {
        chat.getEcouteur().redirigerVers(this);
    }
    
    public abstract void response(String response);
}
