/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.security.hash;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class HasherFactory {
    
    public static final String SHA = "SHA";
    
    public static Hasher make(String type) {
        Hasher instance = null;
        switch(type) {
            case SHA:
                instance = new SHAHasher();
                break;
            default:
                
        }
        return instance;
    }
    
}
