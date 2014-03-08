/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.protocole;

import hermes.format.abnf.ABNF;
import java.util.Map;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class AbstractProtocole implements Protocole {
    private MessageProtocole prepared;

    public AbstractProtocole() {
        prepared = null;
    }

    @Override
    public void prepare(MessageProtocole message) {
        prepared = message;
    }

    @Override
    public String make(Map.Entry<ABNF,String>... args) {
        if(prepared == null) return null;
        prepared.effacer();
        for (Map.Entry<ABNF, String> entry : args) {
            if(!prepared.set(entry.getKey(), entry.getValue())) {
                return null;
            }
        }
        return prepared.remplir();
    }

    @Override
    public boolean check(String message) {
        if(prepared == null) return false;
        return prepared.verifier(message);
    }
    
    public String get(ABNF variable) {
        if(prepared == null) return null;
        return prepared.get(variable);
    }
}
