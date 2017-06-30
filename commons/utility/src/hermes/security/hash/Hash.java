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
public class Hash {
    
    private static Hasher hasher = HasherFactory.make(HasherFactory.SHA);
    
    public static void setHasher(Hasher h) {
        if(h != null) {
            hasher = h;
        }
    }
    
    public static void setHasher(String type) {
        setHasher(HasherFactory.make(type));
    }
    
    public static String hash(String message) {
        return hasher.hash(message);
    }
}
