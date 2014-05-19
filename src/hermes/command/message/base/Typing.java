/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.message.base;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Typing extends Message {

    public Typing(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(2)) {
            String channel = (String) args[0];
            boolean b = (boolean) args[1];
            typing(channel,(b ? "1" : "0"));
        }
    }

    private void typing(String channel, String digit) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.TYPING);
        String request;
        try {
            request = protocole.make(
                    new Entry<>(ProtocoleSwinen.channel, (Object) channel),
                    new Entry<>(ProtocoleSwinen.digit, (Object) digit)
            );
            if (request != null && protocole.check(request)) {
                client.getEmetteur().envoyer(request);
            }
        } catch (Exception ex) {
            Logger.getLogger(Quit.class.getName()).log(Level.SEVERE, null, ex);
            client.setEtat(ClientStatus.BadProtocoleSended);
        }
    }

    @Override
    public void response(String response) {
    }
}
