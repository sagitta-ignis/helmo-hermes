/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.message.channel;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.command.message.base.Message;
import hermes.protocole.Entry;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author d120041
 */
public class Exit extends Message {

    public Exit(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String channel = (String) args[0];
            exit(channel);
        }
    }

    private void exit(String channel) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.EXIT);
        String request;
        try {
            request = protocole.make(
                    new Entry<>(ProtocoleSwinen.channel, (Object) channel));
        } catch (Exception ex) {
            return;
        }
        if (request != null && protocole.check(request)) {
            client.getEmetteur().envoyer(request);
        } else {
            client.setEtat(ClientStatus.BadMessageMaked);
        }
    }

    @Override
    public void response(String response) {
    }

}
