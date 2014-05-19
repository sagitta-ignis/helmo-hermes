/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.model;

import hermes.client.utilisateurs.Utilisateur;
import hermes.client.utilisateurs.Utilisateurs;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class UtilisateursModel extends Utilisateurs {
    
    private final DefaultTreeModel model;

    public UtilisateursModel(DefaultTreeModel model) {
        this.model = model;
    }
    
    @Override
    public Utilisateur instanciate(String utilisateur) {
        return new UtilisateurNode(utilisateur, model);
    }
    
}
