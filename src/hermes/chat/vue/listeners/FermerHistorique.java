/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue.listeners;

import hermes.chat.controleur.Chatter;
import hermes.chat.vue.ChatIRC;
import java.awt.event.ActionEvent;
import javax.swing.JCloseableTabComponent;

/**
 *
 * @author Thomas Menini
 */
public class FermerHistorique extends Fermer {

    public FermerHistorique(Chatter chat, JCloseableTabComponent parent) {
        super(chat, parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChatIRC fenetre = chat.getFenetre();
        fenetre.fermerHistorique(parent.getName());
    }
    
}
