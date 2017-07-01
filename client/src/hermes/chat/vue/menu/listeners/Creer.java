/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Creer implements ActionListener {

    private final Chatter chat;

    public Creer(Chatter chat) {
        this.chat = chat;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        creerChannel();
    }
    
    public void creerChannel() {
        ChatIRC fenetre = chat.getFenetre();
        String channel = fenetre.demander("Création channel", "nom du channel :");
        if(channel == null) return;
        String password = null;
        if(fenetre.confirmer("Création channel", "ce channel est-il privé ?")) {
            password = fenetre.demander("Création channel", "mot de passe :");
        }
        ClientMessageHandler handler = chat.getMessageHandler();
        handler.execute("/createchannel", channel, password);
    }
}
