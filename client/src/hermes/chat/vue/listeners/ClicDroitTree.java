/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.listeners;

import hermes.chat.controleur.Chatter;
import hermes.chat.model.ChannelNode;
import hermes.chat.model.ServerNode;
import hermes.chat.model.UtilisateurNode;
import hermes.chat.vue.menu.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClicDroitTree extends MouseAdapter {
    
    private final Chatter chat;
    private final JTree tree;
    private final JPopupMenu menu;
    private final Menus menus;

    public ClicDroitTree(Chatter chat, JTree tree) {
        this.chat = chat;
        this.tree = tree;
        menu = new JPopupMenu();
        menus = new Menus(chat);
        menus.ajouter(UtilisateurNode.class, new MenuUtilisateur(chat));
        menus.ajouter(ChannelNode.class, new MenuChannel(chat));
        menus.ajouter(ServerNode.class, new MenuServer(chat));
    }
    private void remlpirMenu(Object lastSelectionPathComponent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastSelectionPathComponent;
        Object data = node.getUserObject();
        Class type = data.getClass();
        Menu m = menus.get(type);
        menu.removeAll();
        if(m == null) return;
        List<JMenuItem> items = m.filtrerItemsPour(data);
        for (JMenuItem item : items) {
            menu.add(item);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            int row = tree.getClosestRowForLocation(e.getX(), e.getY());
            tree.setSelectionRow(row);
            Object pourComponent = tree.getLastSelectedPathComponent();
            remlpirMenu(pourComponent);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
