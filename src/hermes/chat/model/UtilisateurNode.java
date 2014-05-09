/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import hermes.client.utilisateurs.Utilisateur;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class UtilisateurNode extends Utilisateur {

    private final DefaultMutableTreeNode node;
    private final String nameOn;
    
    public UtilisateurNode(String name) {
        super(name);
        nameOn = String.format("<span style=\"color:blue;\">%s</span>", name);
        node = new DefaultMutableTreeNode(this);
    }

    @Override
    public void setTyping(boolean typing) {
        super.setTyping(typing);
        node.setUserObject(this);
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

    @Override
    protected String toLabel() {
        return isTyping()?nameOn:getName();
    }
    
}
