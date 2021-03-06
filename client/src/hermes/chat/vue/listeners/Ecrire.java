/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.listeners;

import hermes.chat.Chat;
import hermes.chat.controleur.Chatter;
import hermes.chat.vue.ChatGUI;
import hermes.chat.vue.Conversation;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Ecrire extends KeyAdapter {

    private final ChatGUI fenetre;
    private final Chatter chat;

    public Ecrire(ChatGUI fenetre, Chatter chat) {
        this.fenetre = fenetre;
        this.chat = chat;
    }   
    
    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyChar() == '\n') {
            fenetre.getEnvoyer().doClick();
        } else {
            JTextField message = (JTextField) ke.getSource();
            String text = message.getText();
            Conversation c = fenetre.getConversation(Chat.CURRENT);
            if(c == null || c.isHistorique() || c.isPrivate()) return;
            if (!fenetre.isTyping() && !text.isEmpty()) {
                chat.setTyping(c.getName(), true);
            } else if (fenetre.isTyping() && text.isEmpty()) {
                chat.setTyping(c.getName(), false);
            }
        }
    }
}
