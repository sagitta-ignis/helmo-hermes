/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.command.requete;

import hermes.client.Client;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServerShutDown extends Requete {

    public ServerShutDown(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SERVERSHUTDOWN);
            if (protocole.check(text)) {
                client.setEtat(Client.ServerShutDown);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
    
}
