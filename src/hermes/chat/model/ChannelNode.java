/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import hermes.client.channels.Channel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ChannelNode extends Channel {

    private final DefaultMutableTreeNode node;
    
    public ChannelNode(String nom) {
        super(nom);
        node = new DefaultMutableTreeNode(this);
        setUtilisateurs(new UtilisateursModel());
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

    @Override
    public UtilisateurNode rejoindre(String utilisateur) {
        UtilisateurNode u = (UtilisateurNode) super.rejoindre(utilisateur);
        node.add(u.getNode());
        return u;
    }

    @Override
    public UtilisateurNode quitter(String utilisateur) {
        UtilisateurNode u = (UtilisateurNode) super.quitter(utilisateur);
        node.remove(u.getNode());
        return u;
    }
    
    
}
