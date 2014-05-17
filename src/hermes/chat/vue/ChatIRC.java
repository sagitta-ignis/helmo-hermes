/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.AbstractChat;
import hermes.chat.controleur.Chatter;
import hermes.chat.controleur.MessageLogger;
import hermes.chat.controleur.Overlayer;
import hermes.client.channels.Channel;
import hermes.client.channels.Channels;
import hermes.client.utilisateurs.Utilisateur;
import hermes.client.utilisateurs.Utilisateurs;
import javax.swing.JOptionPane;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ChatIRC extends AbstractChat {

    private final ChatGUI gui;

    public ChatIRC(Chatter chatter) {
        gui = new ChatGUI(chatter);
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
    
    @Override
    public String demander(String titre, String message) {
        return gui.demander(titre, message);
    }

    @Override
    public void sUsers(Utilisateurs users) {
        for (Utilisateur user : users) {
            ajouterConversationUtilisateur(user.getName());
        }
    }
    
    private void ajouterConversationUtilisateur(String user) {
        gui.addConversation(new Conversation(user, false));
    }

    @Override
    public void sChannel(Channels channels) {
        for (Channel channel : channels) {
            ajouterConversationChannel(channel.getNom());
        }
    }

    @Override
    public void createChannel(String channel) {
        ajouterConversationChannel(channel);
    }

    @Override
    public void deleteChannel(String channel) {
        gui.removeConversation(channel);
    }
    
    private void ajouterConversationChannel(String channel) {
        Conversation c = new Conversation(channel, true);
        gui.addConversation(c);
    }
    
    public void ouvrirHistorique(String nom, boolean isChannel) {
        Conversation c = new Conversation(nom, isChannel);
        c.setHistorique(true);
        gui.addConversation(c);
        gui.entrer(nom);
    }
    
    public void fermerHistorique(String nom) {
        gui.removeConversation(nom);
    }

    @Override
    public void join(String user) {
        ajouterConversationUtilisateur(user);
    }

    @Override
    public void leave(String user) {
        gui.removeConversation(user);
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
    public void entrer(String channel) {
        gui.entrer(channel);
    }

    @Override
    public void sortir(String channel) {
        gui.sortir(channel);
    }

    public void setOverlayer(Overlayer overlayer) {
        gui.setOverlayer(overlayer);
    }
    
    public void setLogger(MessageLogger logger) {
        gui.setLogger(logger);
    }

    public void clear() {
        gui.clear();
    }

    public boolean confirmer(String titre, String message) {
        int res = gui.confirmer(titre, message, JOptionPane.YES_NO_OPTION);
        return res==0;
    }

}
