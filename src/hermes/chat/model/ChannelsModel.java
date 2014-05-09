/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import hermes.client.channels.Channel;
import hermes.client.channels.Channels;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ChannelsModel extends Channels {
    private final DefaultMutableTreeNode root;
    private final DefaultTreeModel model;

    public ChannelsModel() {
        root = new DefaultMutableTreeNode();
        model = new DefaultTreeModel(root);
    }

    public DefaultTreeModel getModel() {
        return model;
    }

    @Override
    public void setRoot(Channel root) {
        super.setRoot(root);
    }

    @Override
    protected Channel getInstance(String channel) {
        return new ChannelNode(channel);
    }

    @Override
    public Channel ajouter(String channel) {
        ChannelNode c = (ChannelNode) super.ajouter(channel);
        root.add(c.getNode());
        return c;
    }

    @Override
    public Channel retirer(String channel) {
        ChannelNode c = (ChannelNode) super.retirer(channel);
        root.remove(c.getNode());
        return c;
    }
    
    
    
}
