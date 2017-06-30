/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.protocole;

import java.util.AbstractMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 * @param <T>
 * @param <V>
 */
public class Entry<T,V> extends AbstractMap.SimpleEntry<T,V>{

    public Entry(T key, V value) {
        super(key, value);
    }
    
}
