/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.command.message.base;

import hermes.chat.controleur.Chatter;
import hermes.protocole.Protocole;
import hermes.protocole.message.MessageProtocole;
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
        ((SHAHasher) hasher).setFamilly(SHAHasher.SHA512);
        ((SHAHasher) hasher).setSalt("377e5493ce2027cd");
    }

    protected final Chatter chat;
    private MessageProtocole expected;

    public Message(Chatter chat) {
        this.chat = chat;
        expected = null;
    }

    public void setExpected(MessageProtocole expected) {
        this.expected = expected;
    }

    protected String hash(String message) {
        return hasher.hash(message);
    }

    protected void waitResponse() {
        chat.getEcouteur().redirigerVers(this);
    }

    public boolean accept(String response) {
        if(expected == null) return true;
        Protocole p = chat.getProtocole();
        p.prepare(expected);
        return p.check(response);
    }

    public final boolean receive(String response) {
        if (accept(response)) {
            response(response);
            return true;
        }
        return false;
    }

    public abstract void response(String response);
}
