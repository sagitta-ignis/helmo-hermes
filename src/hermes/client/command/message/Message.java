/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.client.command.message;

import hermes.client.Client;
import hermes.client.ClientConnectionHandler;
import hermes.client.Ecouteur;
import hermes.client.Emetteur;
import pattern.command.CommandArgument;
import hermes.protocole.Protocole;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class Message extends CommandArgument {

    protected final Client client;
    protected final Protocole protocole;
    protected final Emetteur emetteur;
    protected final Ecouteur ecouteur;
    protected final ClientConnectionHandler connection;

    public Message(Client client) {
        this.client = client;
        protocole = client.getProtocole();
        emetteur = client.getEmetteur();
        ecouteur = client.getEcouteur();
        connection = client.getConnectionHandler();
    }
    
    protected void waitResponse() {
        ecouteur.getMessageQueue().ajouter(this);
    }
    
    public abstract void response(String response);
}
