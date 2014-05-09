/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public interface Chat {
    
    public static final String ALL = "all";
    public static final String CURRENT = "current";
    public static final String SERVER = "server";
    public static final String ME = "me";
    
    void entrer(String channel, boolean publique);
    void sortir(String channel);
    
    void afficher(String channel, String user, String texte);
    void avertir(String titre, String message);
}
