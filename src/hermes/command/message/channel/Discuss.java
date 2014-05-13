/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.message.channel;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.command.message.base.Message;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.Entry;

/**
 *
 * @author salto
 */
public class Discuss extends Message {

    public Discuss(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if(verifierArguments(2)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String channel = (String) args[0];
            String message = (String) args[1];
            protocole.prepare(ProtocoleSwinen.DISCUSS);
            String request;
            try {
                request = protocole.make(
                        new Entry<>(ProtocoleSwinen.channel, (Object) channel),
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
