/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.client.command.requete;

import hermes.client.Client;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class SMsg extends Requete {

    public SMsg(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SMSG);
            if (protocole.check(text)) {
                String sender = protocole.get(ProtocoleSwinen.sender);
                String message = protocole.get(ProtocoleSwinen.message);
                client.setEtat(Client.SMSG, sender, message);
                //client.afficher("[pm]"+sender + " : " + message);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }

}
