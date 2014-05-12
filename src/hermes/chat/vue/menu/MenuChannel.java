/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue.menu;

import hermes.chat.controleur.Chatter;
import hermes.chat.vue.menu.listeners.Entrer;
import hermes.chat.vue.menu.listeners.Sortir;
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

    public MenuChannel(Chatter chat) {
        this.chat = chat;
        entrer = new Entrer(chat);
        sortir = new Sortir(chat);
        initItems();
    }
    
    private void initItems() {
        ajouterItem("entrer", entrer);
        ajouterItem("sortir", sortir);
    }

    @Override
    public List<JMenuItem> filtrerItemsPour(Channel model) {
        configurer(model);
        return getItems();
    }
    
    private void configurer(Channel model) {
        entrer.setName(model.getNom());
        sortir.setName(model.getNom());
    }
}
