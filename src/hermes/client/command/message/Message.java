/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.client.command.message;

import hermes.client.Client;
import hermes.client.ClientConnectionHandler;
import pattern.command.CommandArgument;
import hermes.protocole.Protocole;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class Message extends CommandArgument {

    protected final Client client;
    protected final Protocole protocole;
    protected final ClientConnectionHandler connection;

    public Message(Client client) {
        this.client = client;
        protocole = client.getProtocole();
        connection = client.getConnectionHandler();
    }
    
    protected void waitResponse() {
        client.getEcouteur().getMessageQueue().ajouter(this);
    }
    
    public abstract void response(String response);
}
