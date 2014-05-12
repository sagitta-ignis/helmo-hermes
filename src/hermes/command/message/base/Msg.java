/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.command.message.base;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Msg extends Message {

    private String receiver;
    private String message;

    public Msg(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(2)) {
            receiver = (String) args[0];
            message = (String) args[1];
            msg();
        }
    }

    private void msg() {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.MSG);
        String request;
        try {
            request = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.receiver, (Object) receiver),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, (Object) message));
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
                        client.setEtat(ClientStatus.MSG, receiver, message);
                        break;
                    case "1":
                        client.setEtat(ClientStatus.UnknownUser);
                        break;
                    case "3":
                        client.setEtat(ClientStatus.MSGToSelf);
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
