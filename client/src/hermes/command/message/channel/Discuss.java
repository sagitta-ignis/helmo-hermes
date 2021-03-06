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

    private String channel;
    
    public Discuss(Chatter chat) {
        super(chat);
        setExpected(ProtocoleSwinen.RESPONSE);
    }

    @Override
    public void execute() {
        if(verifierArguments(2)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            channel = (String) args[0];
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
                waitResponse();
                client.getEmetteur().envoyer(request);
            } else {
                client.setEtat(ClientStatus.BadMessageMaked);
            }
        }
    }
    
    @Override
    public void response(String response) {
        if (response != null) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            protocole.prepare(ProtocoleSwinen.RESPONSE);
            if (protocole.check(response + "\r\n")) {
                switch (protocole.get(ProtocoleSwinen.digit)) {
                    case "0":
                        
                        break;
                    case "1":
                        chat.getFenetre().avertir("Channel inconnu", "discution impossible vers "+channel);
                        break;
                    case "9":
                        client.setEtat(ClientStatus.BadProtocoleSended);
                        break;
                }
            } else {
                chat.getEcouteur().recevoir(response);
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
}
