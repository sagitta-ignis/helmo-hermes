/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServerNode {
    private final String nom;
    
    private final DefaultMutableTreeNode node;
    
    public ServerNode(String nom) {
        this.nom= nom;
        node = new DefaultMutableTreeNode(this);
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

    public String getNom() {
        return nom;
    }
    
    private String toLabel() {
        return nom;
    }

    @Override
    public String toString() {
        return toLabel();
    }
    
}
