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
        notifyObservers(Root);
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
    
    protected Channel getInstance(String channel) {
        return new Channel(channel);
    }

    public Channel ajouter(String channel) {
        Channel c = getInstance(channel);
        channels.put(channel, c);
        setChanged();
        notifyObservers(new String[]{Create, channel});
        return c;
    }

    public Channel retirer(String channel) {
        Channel c = channels.remove(channel);
        if(c != null) {
            setChanged();
            notifyObservers(new String[]{Delete, channel});
        }
        return c;
    }
    
    public void remplir(String channel, List<String> users) {
        get(channel).remplir(users);
        setChanged();
        notifyObservers(new Object[]{SUsersChannel, channel, get(channel).getArrayUtilisateurs()});
    }

    public void rejoindre(String channel, String user) {
        get(channel).rejoindre(user);
        setChanged();
        notifyObservers(new Object[]{Join, channel, user});
    }

    public void quitter(String channel, String user) {
        get(channel).quitter(user);
        setChanged();
        notifyObservers(new Object[]{Leave, channel, user});
    }
    
    public void setPublique(String channel, boolean publique) {
        get(channel).setPublique(publique);
    }

    private Channel get(String channel) {
        return channels.get(channel);
    }
}
