/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue.menu;

import hermes.chat.controleur.Chatter;
import hermes.chat.model.ServerNode;
import hermes.chat.vue.menu.listeners.Creer;
import java.util.List;
import javax.swing.JMenuItem;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MenuServer extends Menu<ServerNode> {
    
    private final Chatter chat;
    private final Creer creer;

    public MenuServer(Chatter chat) {
        this.chat = chat;
        creer = new Creer(chat);
        initItems();
    }    
    
    private void initItems() {
        ajouterItem("creer channel", creer);
    }

    @Override
    public List<JMenuItem> filtrerItemsPour(ServerNode Model) {
        List<JMenuItem> items = getItems();
        
        return items;
    }
    
}
