/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import hermes.client.utilisateurs.Utilisateur;
import hermes.client.utilisateurs.Utilisateurs;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class UtilisateursModel extends Utilisateurs {

    @Override
    public Utilisateur instanciate(String utilisateur) {
        return new UtilisateurNode(utilisateur); //To change body of generated methods, choose Tools | Templates.
    }
    
}
