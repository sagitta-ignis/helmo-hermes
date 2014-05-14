/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.menu.listeners;

import hermes.chat.controleur.Chatter;
import hermes.chat.controleur.handler.ClientMessageHandler;
import hermes.chat.vue.ChatIRC;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author d120041
 */
public class Supprimer implements ActionListener {
    private final Chatter chat;
    private String channel;

    public Supprimer(Chatter chat) {
        this.chat = chat;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        supprimerChannel();
    }
    
    public void supprimerChannel() {
        ChatIRC fenetre = chat.getFenetre();
        if(fenetre.confirmer("Création channel", "voulez-vous vraiment supprimer "+channel+"?")) {
            ClientMessageHandler handler = chat.getMessageHandler();
            handler.execute("/deletechannel", channel);
        }
        
    }
}
