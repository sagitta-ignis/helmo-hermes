/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.channels;

import hermes.client.utilisateurs.Utilisateur;
import hermes.client.utilisateurs.Utilisateurs;
import java.util.List;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Channel {
    
    private final String nom;
    private boolean protege;
    private boolean racine;
    private Utilisateurs utilisateurs;

    protected Channel(String nom) {
        this.nom = nom;
        racine = false;
        protege = false;
        utilisateurs = new Utilisateurs();
    }

    public String getNom() {
        return nom;
    }

    public boolean isRacine() {
        return racine;
    }
    
    public void setProtege(boolean protege) {
        this.protege = protege;
    }

    public boolean isProtege() {
        return protege;
    }

    public void setRacine(boolean racine) {
        this.racine = racine;
    }

    protected void setUtilisateurs(Utilisateurs utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    protected Utilisateurs getUtilisateurs() {
        return utilisateurs;
    }

    public void remplir(List<String> utilisateurs) {
        for (String utilisateur : utilisateurs) {
            rejoindre(utilisateur);
        }
    }

    public Utilisateur rejoindre(String utilisateur) {
        getUtilisateurs().ajouter(utilisateur);
        return getUtilisateurs().get(utilisateur);
    }

    public Utilisateur quitter(String utilisateur) {
        Utilisateur u = getUtilisateurs().get(utilisateur);
        getUtilisateurs().retirer(utilisateur);
        return u;
    }
    
    public Utilisateur getUtilisateur(String utilisateur) {
        return utilisateurs.get(utilisateur);
    }

    public Utilisateur[] getArrayUtilisateurs() {
        return utilisateurs.toArray();
    }

    protected String toLabel() {
        return nom;
    }

    @Override
    public String toString() {
        return toLabel();
    }
}
