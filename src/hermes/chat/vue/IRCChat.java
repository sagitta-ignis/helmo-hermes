/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.AbstractChat;
import hermes.chat.controleur.Chatter;
import javax.swing.DefaultListModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class IRCChat extends AbstractChat {

    private final ChatGUI chat;
    private final DefaultListModel utilisateurs;

    public IRCChat(Chatter chatter) {
        utilisateurs = new DefaultListModel<>();
        chat = new ChatGUI(chatter, utilisateurs);
    }

    public DefaultListModel getUtilisateurs() {
        return utilisateurs;
    }
    
    public void initUtilisateurs(Object users[]) {
        for (Object user : users) {
            utilisateurs.addElement(user);
        }
    }

    @Override
    public void join(String user) {
        utilisateurs.addElement(user);
        super.join(user);
    }

    @Override
    public void leave(String user) {
        utilisateurs.removeElement(user);
        super.leave(user);
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
