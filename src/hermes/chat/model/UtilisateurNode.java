/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import hermes.client.utilisateurs.Utilisateur;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class UtilisateurNode extends Utilisateur {

    private final DefaultTreeModel model;
    private final DefaultMutableTreeNode node;
    
    public UtilisateurNode(String name, DefaultTreeModel model) {
        super(name);
        this.model = model;
        node = new DefaultMutableTreeNode(this);
    }

    @Override
    public void setTyping(boolean typing) {
        super.setTyping(typing);
        // node.setUserObject(this);
        model.nodeChanged(node);
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }
}
