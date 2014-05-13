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
import java.util.AbstractMap;

/**
 *
 * @author salto
 */
public class InfoChannel extends Message {
    
    public InfoChannel(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String channel = (String) args[0];
            infoChannel(channel);
        }
    }

    private void infoChannel(String channel) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.INFOCHANNEL);
        String request;
        try {
            request = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object) channel));
        } catch (Exception ex) {
            return;
        }
        if (request != null && protocole.check(request)) {
            waitResponse();
            client.getEmetteur().envoyer(request);
        } else {
            client.setEtat(ClientStatus.BadMessageMaked);
        }
    }

    @Override
    public void response(String response) {
        chat.getEcouteur().recevoir(response);
    }
}
