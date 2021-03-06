/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */

package hermes.command.message.base;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.protocole.Entry;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class All extends Message {

    public All(Chatter chat) {
        super(chat);
    }
    
    @Override
    public void execute() {
        if(verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String message = (String) args[0];
            protocole.prepare(ProtocoleSwinen.ALL);
            String request;
            try {
                request = protocole.make(
                        new Entry<>(ProtocoleSwinen.message, (Object) message)
                );
            } catch (Exception ex) {
                return;
            }
            if (request != null && protocole.check(request)) {
                client.getEmetteur().envoyer(request);
            } else {
                client.setEtat(ClientStatus.BadMessageMaked);
            }
        }
    }

    @Override
    public void response(String response) {}
}
