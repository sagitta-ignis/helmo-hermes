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
public abstract class AbstractHasher implements Hasher {

    private String salt;

    public AbstractHasher() {
        salt = "e4c53afeaa7a08b1";
    }

    protected final String getSalt() {
        return salt;
    }

    public Hasher setSalt(String salt) {
        this.salt = salt;
        return this;
    }
    
}
