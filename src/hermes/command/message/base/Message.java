/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.command.message.base;

import hermes.chat.controleur.Chatter;
import hermes.security.hash.Hasher;
import hermes.security.hash.HasherFactory;
import hermes.security.hash.SHAHasher;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class Message extends CommandArgument {

    private static final Hasher hasher = HasherFactory.make(HasherFactory.SHA);
    
    static {
        ((SHAHasher)hasher).setFamilly(SHAHasher.SHA512);
        ((SHAHasher)hasher).setSalt("377e5493ce2027cd");
    }
    
    protected final Chatter chat;

    public Message(Chatter chat) {
        this.chat = chat;
    }
    
    protected String hash(String message) {
        return hasher.hash(message);
    }
    
    protected void waitResponse() {
        chat.getEcouteur().redirigerVers(this);
    }
    
    public abstract void response(String response);
}
