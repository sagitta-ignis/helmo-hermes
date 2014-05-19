/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import hermes.client.channels.Channel;
import hermes.client.channels.Channels;
import hermes.client.utilisateurs.Utilisateurs;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ChannelsModel extends Channels {
    private final ServerNode server;
    private final DefaultMutableTreeNode root;
    private final DefaultTreeModel model;

    public ChannelsModel(Utilisateurs u) {
        super(u);
        server = new ServerNode("Server");
        root = new DefaultMutableTreeNode(server);
        model = new DefaultTreeModel(root);
    }

    public DefaultTreeModel getModel() {
        return model;
    }

    @Override
    protected Channel instanciate(String channel) {
        return new ChannelNode(channel, model);
    }

    @Override
    public Channel ajouter(String channel) {
        ChannelNode c = (ChannelNode) super.ajouter(channel);
        root.add(c.getNode());
        model.nodeStructureChanged(root);
        return c;
    }

    @Override
    public Channel retirer(String channel) {
        ChannelNode c = (ChannelNode) super.retirer(channel);
        root.remove(c.getNode());
        model.nodeStructureChanged(root);
        return c;
    }
    
    
    
}
