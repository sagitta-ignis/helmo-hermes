/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.controleur;

import hermes.chat.vue.Overlay;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Overlayer {

    private final Overlay overlay;

    public Overlayer(Chatter chat) {
        overlay = new Overlay(chat);
    }

    public void activer(int dimension) {
        overlay.initialiser(dimension);
    }

    public void desactiver() {
        overlay.setVisible(false);
    }
    
    public void afficher(String text) {
        overlay.afficher(text);
    }
    
    public void fermer() {
        overlay.setVisible(false);
    }

}
