/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.command.requete;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServerShutDown extends Requete {

    public ServerShutDown(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String text = (String) args[0];
            chat.getProtocole().prepare(ProtocoleSwinen.SERVERSHUTDOWN);
            if (chat.getProtocole().check(text)) {
                chat.getClient().setEtat(Client.ServerShutDown);
            } else {
                chat.getClient().setEtat(Client.BadProtocoleReceived);
            }
        }
    }
    
}
