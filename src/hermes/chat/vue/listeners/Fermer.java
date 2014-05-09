/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.listeners;

import hermes.chat.controleur.Chatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCloseableTabComponent;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Fermer implements ActionListener {

    private final Chatter chat;
    private final JCloseableTabComponent parent;

    public Fermer(Chatter chat, JCloseableTabComponent parent) {
        this.chat = chat;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chat.sortir(parent.getName());
    }

}
