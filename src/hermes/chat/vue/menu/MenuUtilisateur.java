/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.menu;

import hermes.chat.controleur.Chatter;
import hermes.chat.vue.listeners.Entrer;
import hermes.client.channels.Channel;
import hermes.client.utilisateurs.Utilisateur;
import java.util.List;
import javax.swing.JMenuItem;

/**
 *
 * @author d120041
 */
public class MenuUtilisateur extends Menu<Utilisateur>{
    private final Chatter chat;
    private final Entrer entrer;

    public MenuUtilisateur(Chatter chat) {
        this.chat = chat;
        entrer = new Entrer(chat);
        initItems();
    }
    
    private void initItems() {
        ajouterItem("message privé", entrer);
    }

    @Override
    public List<JMenuItem> filtrerItemsPour(Utilisateur model) {
        configurer(model);
        return getItems();
    }
    
    private void configurer(Utilisateur model) {
        entrer.setName(model.getName());
        entrer.setPublique(false);
    }
    
}
