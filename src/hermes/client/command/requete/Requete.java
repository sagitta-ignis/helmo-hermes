/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.command.requete;

import hermes.client.Client;
import pattern.command.CommandArgument;
import hermes.protocole.Protocole;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class Requete extends CommandArgument{
    protected final Client client;
    protected final Protocole protocole;

    public Requete(Client client) {
        this.client = client;
        protocole = client.getProtocole();
    }    
}
