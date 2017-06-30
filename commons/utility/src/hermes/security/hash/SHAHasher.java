/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.security.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class SHAHasher extends AbstractHasher {

    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";
    
    private MessageDigest md;

    protected SHAHasher() {
        try {
            md = MessageDigest.getInstance(SHA1);
        } catch (NoSuchAlgorithmException ex) {

        }
    }

    public Hasher setFamilly(String familly) {
        try {
            md = MessageDigest.getInstance(familly);
        } catch (NoSuchAlgorithmException ex) {

        }
        return this;
    }

    @Override
    public String hash(String message) {
        if(md == null || message == null) return null;
        md.update(getSalt().getBytes());
        byte[] bytes = md.digest(message.getBytes());
        StringBuilder generated = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            generated.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return generated.toString();
    }

}
