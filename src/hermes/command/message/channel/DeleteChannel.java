/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class DeleteChannel extends Message {

    public DeleteChannel(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String channel = (String) args[0];
            deleteChannel(channel);
        }
    }

    private void deleteChannel(String channel) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.DELETECHANNEL);
        String request;
        try {
            request = protocole.make(
                    new Entry<>(ProtocoleSwinen.channel, (Object) channel));
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
        if (response != null) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            protocole.prepare(ProtocoleSwinen.RESPONSE);
            if (protocole.check(response + "\r\n")) {
                switch (protocole.get(ProtocoleSwinen.digit)) {
                    case "0":
                        
                        break;
                    case "9":
                        client.setEtat(ClientStatus.BadProtocoleSended);
                        break;
                }
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
}
