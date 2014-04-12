/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.vue;

import hermes.client.controleur.Chatter;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class IRCChat extends AbstractChat {

    private final ChatGUI chat;

    public IRCChat(Chatter chatter) {
        chat = new ChatGUI(chatter, getUtilisateurs());
    }
    
    @Override
    public void afficher(String texte) {
        chat.afficher(texte);
    }

    @Override
    public void avertir(String titre, String message) {
        chat.avertir(titre, message);
    }
    
    public void setVisible(boolean visible) {
        chat.setVisible(visible);
    }
}
