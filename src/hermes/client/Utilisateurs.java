/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Utilisateurs extends Observable {
    public final static String SUsers = "susers";
    public final static String Join = "join";
    public final static String Leave = "leave";
    private final Set<String> users;

    public Utilisateurs() {
        users = new  ConcurrentSkipListSet<>();
    }
    
    public boolean remplir(Collection<? extends String> utilisateurs) {
        if(users.addAll(utilisateurs)) {
            setChanged();
            notifyObservers(new Object[] {SUsers,this});
            return true;
        }
        return false;
    }
    
    public boolean ajouter(String utilisateur) {
        if(users.add(utilisateur)) {
            setChanged();
            notifyObservers(new String[] {Join,utilisateur});
            return true;
        }
        return false;
    }
    
    public boolean retirer(String utilisateur) {
        if(users.remove(utilisateur)) {
            setChanged();
            notifyObservers(new String[] {Leave,utilisateur});
            return true;
        }
        return false;
    }
    
    public Object[] toArray() {
        return users.toArray();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String user : users) {
            if(builder.toString().length() != 0) {
                builder.append(", ");
            }
            builder.append(user);
        }
        return builder.toString();
    }
}
