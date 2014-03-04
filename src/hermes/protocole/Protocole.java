package hermes.protocole;

import hermes.format.abnf.ABNF;
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
    String make(Entry<ABNF,String>... args);
    boolean check(String message);
}
