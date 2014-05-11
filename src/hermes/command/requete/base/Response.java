/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.base;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Response extends Requete {

    public Response(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.RESPONSE);
            if (protocole.check(text)) {
                String digit = protocole.get(ProtocoleSwinen.digit);
                String message = protocole.get(ProtocoleSwinen.message);
                client.setEtat(Client.RESPONSE, digit, message);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
}
