/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue.listeners;

import hermes.chat.controleur.Chatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Sortir implements ActionListener {

    private final Chatter chat;
    private String name;

    public Sortir(Chatter chat) {
        this.chat = chat;
        name = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chat.sortir(name);
    }

}
