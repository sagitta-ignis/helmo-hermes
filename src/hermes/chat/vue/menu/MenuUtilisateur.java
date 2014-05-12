/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.menu;

import hermes.chat.controleur.Chatter;
import hermes.chat.vue.menu.listeners.Entrer;
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
        List<JMenuItem> items = getItems();
        if(model.getName().equals(chat.getUtilisateur().getName())) {
            items.remove(get("message privé"));
        }
        configurer(model);
        return items;
    }
    
    private void configurer(Utilisateur model) {
        entrer.setName(model.getName());
    }
    
}
