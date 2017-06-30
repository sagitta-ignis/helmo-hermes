/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.command.requete.base;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.protocole.Protocole;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class Requete extends CommandArgument{
    protected final Chatter chat;

    public Requete(Chatter chat) {
        this.chat = chat;
    }    
}
