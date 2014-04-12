/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client;

import java.util.*;


/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Utilisateurs extends Observable {
    public final static String Join = "join";
    public final static String Leave = "leave";
    private final Set<String> users;

    public Utilisateurs() {
        users = new HashSet<>();
    }
    
    public boolean remplir(Collection<? extends String> utilisateurs) {
        return users.addAll(utilisateurs);
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
}
