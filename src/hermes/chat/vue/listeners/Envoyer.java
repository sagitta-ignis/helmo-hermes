/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.listeners;

import static hermes.chat.Chat.CURRENT;
import hermes.chat.controleur.Chatter;
import hermes.chat.vue.ChatGUI;
import hermes.chat.vue.Conversation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Envoyer implements ActionListener {
    
    private final ChatGUI fenetre;
    private final Chatter chat;

    public Envoyer(ChatGUI fenetre, Chatter chat) {
        this.fenetre = fenetre;
        this.chat = chat;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        chat.setTyping(false);
        Conversation c = fenetre.getConversation(CURRENT);
        if(c != null) {
            chat.ecrire(c.getName(), fenetre.getMessage().getText(), c.isPublic());
        } else {
            fenetre.avertir("Erreur", "Pas de conversation ouverte pour discuter");
        } 
        fenetre.getMessage().setText("");
    }
}
