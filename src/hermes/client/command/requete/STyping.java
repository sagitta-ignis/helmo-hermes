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
public class STyping extends Requete {

    public STyping(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.STYPING);
            if (protocole.check(text)) {
                String user = protocole.get(ProtocoleSwinen.user);
                String digit = protocole.get(ProtocoleSwinen.digit);
                client.setEtat(Client.STYPING, user, digit);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }

}
