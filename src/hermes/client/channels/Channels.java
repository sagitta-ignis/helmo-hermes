/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.channels;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Channels extends Observable {

    public final static String Root = "root";
    public final static String SChannels = "schannel";
    public final static String Create = "create";
    public final static String Delete = "delete";
    public final static String SUsersChannel = "suserschannel";
    public final static String Join = "joinchannel";
    public final static String Leave = "leavechannel";

    private Channel root;
    private final Map<String, Channel> channels;

    public Channels() {
        channels = new ConcurrentHashMap<>();
    }

    public Channel getRoot() {
        return root;
    }

    public void setRoot(Channel root) {
        this.root = root;
        setChanged();
        notifyObservers(new Object[]{Root, root});
    }

    public Channel[] getChannels() {
        return (Channel[]) channels.values().toArray();
    }

    public boolean remplir(Collection<? extends String> channels) {
        for (String channel : channels) {
            ajouter(channel);
        }
        setChanged();
        notifyObservers(new Object[]{SChannels, this});
        return true;
    }

    protected Channel instanciate(String channel) {
        return new Channel(channel);
    }

    public Channel ajouter(String channel) {
        Channel c = instanciate(channel);
        channels.put(channel, c);
        setChanged();
        notifyObservers(new Object[]{Create, channel});
        return c;
    }

    public Channel retirer(String channel) {
        Channel c = channels.remove(channel);
        if (c != null) {
            setChanged();
            notifyObservers(new Object[]{Delete, channel});
        }
        return c;
    }

    public void remplir(String channel, List<String> users) {
        Channel c = get(channel);
        if (c != null) {
            c.remplir(users);
            setChanged();
            notifyObservers(new Object[]{SUsersChannel, channel, get(channel).getArrayUtilisateurs()});
        }
    }

    public void rejoindre(String channel, String user) {
        Channel c = get(channel);
        if (c != null) {
            c.rejoindre(user);
            setChanged();
            notifyObservers(new Object[]{Join, channel, user});
        }
    }

    public void quitter(String channel, String user) {
        Channel c = get(channel);
        if (c != null) {
            c.quitter(user);
            setChanged();
            notifyObservers(new Object[]{Leave, channel, user});
        }
    }

    public void setPublique(String channel, boolean publique) {
        Channel c = get(channel);
        if (c != null) {
            c.setPublique(publique);
        }
    }

    public Channel get(String channel) {
        return channels.get(channel);
    }
}
