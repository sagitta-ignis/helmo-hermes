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
public class Utilisateurs extends Observable implements Iterable<Utilisateur> {

    private final Map<String, Utilisateur> utilisateurs;

    public Utilisateurs() {
        utilisateurs = new ConcurrentHashMap<>();
    }

    public boolean remplir(Collection<? extends String> utilisateurs) {
        for (String utilisateur : utilisateurs) {
            if (!ajouter(utilisateur)) {
                return false;
            }
        }
        setChanged();
        notifyObservers(new Object[]{UtilisateursStatus.SUsers, this});
        return true;
    }
    
    public Utilisateur instanciate(String utilisateur) {
        return new Utilisateur(utilisateur);
    }

    public boolean ajouter(String utilisateur) {
        if (utilisateurs.containsKey(utilisateur)) {
            return false;
        }
        utilisateurs.put(utilisateur, instanciate(utilisateur));
        setChanged();
        notifyObservers(new String[]{UtilisateursStatus.Join, utilisateur});
        return true;
    }

    public boolean retirer(String utilisateur) {
        if (!utilisateurs.containsKey(utilisateur)) {
            return false;
        }
        utilisateurs.remove(utilisateur);
        setChanged();
        notifyObservers(new String[]{UtilisateursStatus.Leave, utilisateur});
        return true;
    }
    
    public Utilisateur get(String utilisateur) {
        return utilisateurs.get(utilisateur);
    }

    public Utilisateur[] toArray() {
        Utilisateur[] array = new Utilisateur[utilisateurs.size()];
        return utilisateurs.values().toArray(array);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String user : utilisateurs.keySet()) {
            if (builder.toString().length() != 0) {
                builder.append(", ");
            }
            builder.append(user);
        }
        return builder.toString();
    }

    public boolean existe(String user) {
        return utilisateurs.containsKey(user);
    }

    @Override
    public Iterator<Utilisateur> iterator() {
        return utilisateurs.values().iterator();
    }

    public void clear() {
        utilisateurs.clear();
    }
}
