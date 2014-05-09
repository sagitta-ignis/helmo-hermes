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
public class Entrer implements ActionListener {

    private final Chatter chat;
    private final String name;
    private final boolean publique;

    public Entrer(Chatter chat, String name, boolean publique) {
        this.chat = chat;
        this.name = name;
        this.publique = publique;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chat.entrer(name, publique);
    }

}
