/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.controleur;

import hermes.chat.vue.Overlay;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Overlayer implements ActionListener {

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
    
    public void afficher(String channel, String user, String text) {
        overlay.afficher(channel, user, text);
    }
    
    public void fermer() {
        overlay.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) e.getSource();
            String name = item.getText();
            String action = (name.split(" "))[0];
            switch(action) {
                case "Overlay":
                    String nombre = (name.split(" "))[1];
                    activer(Integer.parseInt(nombre));
                    break;
                case "Desactiver":
                    desactiver();
                    break;
            }
        }
    }

}
