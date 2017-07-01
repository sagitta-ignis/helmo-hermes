/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue;

import hermes.chat.model.UtilisateurNode;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class UserTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        if(obj instanceof UtilisateurNode) {
            UtilisateurNode user = (UtilisateurNode) obj;
            if(user.isTyping()) {
                setForeground(Color.BLUE);
            } else {
                setForeground(Color.BLACK);
            }
        }
        return this;
    }
    
}
