/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.listeners;

import hermes.chat.controleur.Chatter;
import hermes.chat.model.ChannelNode;
import hermes.chat.model.UtilisateurNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public ClicDroitTree(Chatter chat, JTree tree) {
        this.chat = chat;
        this.tree = tree;
        this.menu = new JPopupMenu();
    }
    private void selectMenu(JPopupMenu menu, Object lastSelectionPathComponent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastSelectionPathComponent;
        Object data = node.getUserObject();
        if(data instanceof UtilisateurNode) {
            userMenu(menu, (UtilisateurNode)data);
        } else if  (data instanceof ChannelNode) {
            channelMenu(menu, (ChannelNode)data);
        }
    }
    
    private void userMenu(JPopupMenu menu, UtilisateurNode user) {
        menu.removeAll();
        JMenuItem message = new JMenuItem("message privé");
        message.addActionListener(new Entrer(chat, user.getName(), false));
        menu.add(message);
    }
    
    private void channelMenu(JPopupMenu menu, ChannelNode channel) {
        menu.removeAll();
        JMenuItem entrer = new JMenuItem("entrer");
        entrer.addActionListener(new Entrer(chat, channel.getNom(), true));
        menu.add(entrer);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            int row = tree.getClosestRowForLocation(e.getX(), e.getY());
            tree.setSelectionRow(row);
            Object o = tree.getLastSelectedPathComponent();
            selectMenu(menu, o);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
