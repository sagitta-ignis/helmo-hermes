package hermes.protocole;

import hermes.protocole.message.MessageProtocole;
import hermes.format.abnf.ABNF;
import java.util.List;
import java.util.Map.Entry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public interface Protocole {

    void prepare(MessageProtocole action);

    String make(Entry<ABNF, Object>... args) throws Exception;

    boolean check(String message);

    String get(ABNF variable);
    
    List<String> getAll(ABNF variable);
    
    MessageProtocole search(String message);
}
