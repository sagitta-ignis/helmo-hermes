/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.channels;

import hermes.client.utilisateurs.Utilisateurs;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Channels extends Observable implements Iterable<Channel> {

    private Channel root;
    private final Utilisateurs utilisateurs;
    private final Map<String, Channel> channels;

    public Channels(Utilisateurs u) {
        utilisateurs = u;
        channels = new ConcurrentHashMap<>();
    }

    public Channel getRoot() {
        return root;
    }

    public void setRoot(Channel root) {
        this.root = root;
        setChanged();
        notifyObservers(new Object[]{ChannelsStatus.Root, root});
    }

    public Channel[] getChannels() {
        return (Channel[]) channels.values().toArray();
    }

    public boolean remplir(Collection<? extends String> channels) {
        for (String channel : channels) {
            ajouter(channel);
        }
        setChanged();
        notifyObservers(new Object[]{ChannelsStatus.SChannels, this});
        return true;
    }

    protected Channel instanciate(String channel) {
        return new Channel(channel);
    }

    public Channel ajouter(String channel) {
        Channel c = instanciate(channel);
        channels.put(channel, c);
        setChanged();
        notifyObservers(new Object[]{ChannelsStatus.Create, channel});
        return c;
    }

    public Channel retirer(String channel) {
        Channel c = channels.remove(channel);
        if (c != null) {
            setChanged();
            notifyObservers(new Object[]{ChannelsStatus.Delete, channel});
        }
        return c;
    }

    public void remplir(String channel, List<String> users) {
        Channel c = get(channel);
        if (c != null) {
            c.remplir(users);
            setChanged();
            notifyObservers(new Object[]{ChannelsStatus.SUsersChannel, channel, get(channel).getArrayUtilisateurs()});
        }
    }

    public void rejoindre(String channel, String user) {
        Channel c = get(channel);
        if (c != null) {
            if(!utilisateurs.existe(user)) return;
            c.rejoindre(user);
            setChanged();
            notifyObservers(new Object[]{ChannelsStatus.Join, channel, user});
        }
    }

    public void quitter(String channel, String user) {
        Channel c = get(channel);
        if (c != null) {
            if(!utilisateurs.existe(user)) return;
            c.quitter(user);
            setChanged();
            notifyObservers(new Object[]{ChannelsStatus.Leave, channel, user});
        }
    }
    
    public void setProtege(String channel, boolean protege) {
        Channel c = get(channel);
        if (c != null) {
            c.setProtege(protege);
        }
    }
    
    public Channel get(String channel) {
        return channels.get(channel);
    }

    @Override
    public Iterator<Channel> iterator() {
        return channels.values().iterator();
    }

    public void clear() {
        channels.clear();
    }
    
}
