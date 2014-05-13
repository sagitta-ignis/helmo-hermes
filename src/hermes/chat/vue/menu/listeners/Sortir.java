/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue.menu.listeners;

import hermes.chat.controleur.Chatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Sortir implements ActionListener {

    private final Chatter chat;
    private String channel;

    public Sortir(Chatter chat) {
        this.chat = chat;
        channel = null;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chat.sortir(channel);
    }

}
