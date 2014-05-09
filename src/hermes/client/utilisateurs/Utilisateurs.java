/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.utilisateurs;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Utilisateurs extends Observable {

    public final static String SUsers = "susers";
    public final static String Join = "join";
    public final static String Leave = "leave";
    private final Map<String, Utilisateur> users;

    public Utilisateurs() {
        users = new ConcurrentHashMap<>();
    }

    public boolean remplir(Collection<? extends String> utilisateurs) {
        for (String utilisateur : utilisateurs) {
            if (!ajouter(utilisateur)) {
                return false;
            }
        }
        setChanged();
        notifyObservers(new Object[]{SUsers, this});
        return true;
    }
    
    protected Utilisateur getInstance(String utilisateur) {
        return new Utilisateur(utilisateur);
    }

    public boolean ajouter(String utilisateur) {
        if (users.containsKey(utilisateur)) {
            return false;
        }
        users.put(utilisateur, getInstance(utilisateur));
        setChanged();
        notifyObservers(new String[]{Join, utilisateur});
        return true;
    }

    public boolean retirer(String utilisateur) {
        if (!users.containsKey(utilisateur)) {
            return false;
        }
        users.remove(utilisateur);
        setChanged();
        notifyObservers(new String[]{Leave, utilisateur});
        return true;
    }
    
    public Utilisateur get(String utilisateur) {
        return users.get(utilisateur);
    }

    public Object[] toArray() {
        return users.values().toArray();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String user : users.keySet()) {
            if (builder.toString().length() != 0) {
                builder.append(", ");
            }
            builder.append(user);
        }
        return builder.toString();
    }
}
