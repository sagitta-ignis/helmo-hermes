/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue.menu;

import hermes.chat.controleur.Chatter;
import hermes.chat.vue.menu.listeners.Entrer;
import hermes.chat.vue.menu.listeners.Sortir;
import hermes.chat.vue.menu.listeners.Supprimer;
import hermes.client.channels.Channel;
import java.util.List;
import javax.swing.JMenuItem;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MenuChannel extends Menu<Channel>{
    private final Chatter chat;
    private final Entrer entrer;
    private final Sortir sortir;
    private final Supprimer supprimer;

    public MenuChannel(Chatter chat) {
        this.chat = chat;
        entrer = new Entrer(chat);
        sortir = new Sortir(chat);
        supprimer = new Supprimer(chat);
        initItems();
    }
    
    private void initItems() {
        ajouterItem("entrer", entrer);
        ajouterItem("sortir", sortir);
        ajouterItem("supprimer", supprimer);
    }

    @Override
    public List<JMenuItem> filtrerItemsPour(Channel model) {
        configurer(model);
        return getItems();
    }
    
    private void configurer(Channel model) {
        entrer.setChannel(model.getNom());
        sortir.setChannel(model.getNom());
        supprimer.setChannel(model.getNom());
    }
}
