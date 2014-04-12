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
public class Leave extends Requete {

    public Leave(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if(verifierArguments(1)) {
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.LEAVE);
            if(protocole.check(text)) {
                String user = protocole.get(ProtocoleSwinen.user);
                client.getUsers().retirer(user);
                //client.afficher("-- "+user+" a quitté le serveur");
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
    
}
