/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.command.messages;

import hermes.client.Client;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class SMsg extends Message {

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
                client.afficher("[pm]"+sender + " : " + message);
            } else {
                client.afficher("-- bad protocole [r]");
            }
        }
    }

}
