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
import java.util.AbstractMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class CreateChannel extends Message {

    public CreateChannel(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1, 2)) {
            String channel = (String) args[0];
            String password = null;
            if (args.length == 2) {
                password = (String) args[1];
            }
            createChannel(channel, password);
        }
    }

    private void createChannel(String channel, String password) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.CREATECHANNEL);
        String request;
        try {
            if(password == null) {
                request = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object) channel));
            } else {
                request = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object) channel),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.pass, (Object) password));
            }
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
        int i = 0;
    }

}
