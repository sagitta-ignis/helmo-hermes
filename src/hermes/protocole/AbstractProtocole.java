/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.protocole;

import hermes.protocole.message.MessageProtocole;
import hermes.format.abnf.ABNF;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class AbstractProtocole implements Protocole {
    
    private final List<MessageProtocole> messages;
    private MessageProtocole prepared;

    public AbstractProtocole() {
        messages = new ArrayList<>();
        prepared = null;
    }
    
    protected void add(MessageProtocole messageProtocole) {
        messages.add(messageProtocole);
    }

    @Override
    public void prepare(MessageProtocole message) {
        prepared = message;
    }

    @Override
    public String make(Entry<ABNF,Object>... args) throws Exception{
            if(prepared == null) return null;
            prepared.effacer();
            for (Entry<ABNF, Object> entry : args) {
                if(!prepared.set(entry.getKey(), entry.getValue())) {
                    return null;
                }
            }
            return prepared.remplir();
    }

    @Override
    public boolean check(String message) {
        if(prepared == null) return false;
        return prepared.scanner(message);
    }
    
    @Override
    public String get(ABNF variable) {
        if(prepared == null) return null;
        return prepared.get(variable);
    }
    
    @Override
    public List<String> getAll(ABNF variable) {
        if(prepared == null) return null;
        return prepared.getAll(variable);
    }

    @Override
    public MessageProtocole search(String message) {
        for (MessageProtocole messageProtocole : messages) {
            if(messageProtocole.scanner(message)) {
                return messageProtocole;
            }
        }
        return null;
    }
}
