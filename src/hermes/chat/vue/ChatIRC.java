/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.AbstractChat;
import hermes.chat.controleur.Chatter;
import hermes.chat.controleur.Overlayer;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ChatIRC extends AbstractChat {

    private final ChatGUI gui;

    public ChatIRC(Chatter chatter, Overlayer overlayer) {
        gui = new ChatGUI(chatter, overlayer);
    }

    public void setTyping(boolean typing) {
        gui.setTyping(typing);
    }

    public boolean isTyping() {
        return gui.isTyping();
    }

    @Override
    public void afficher(String channel, String user, String texte) {
        gui.afficher(channel, user, texte);
    }

    @Override
    public void avertir(String titre, String message) {
        gui.avertir(titre, message);
    }

    public void setVisible(boolean visible) {
        gui.setVisible(visible);
    }
    
    public boolean isVisible() {
        return gui.isVisible();
    }

    public void setChannels(TreeModel channels) {
        gui.setChannels(channels);
    }

    @Override
    public void entrer(String channel, boolean publique) {
        gui.entrer(channel, publique);
    }

    @Override
    public void sortir(String channel) {
        gui.sortir(channel);
    }
}
