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
import hermes.protocole.Entry;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Register extends Message {

    public Register(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(2)) {
            String user = (String) args[0];
            String pass = (String) args[1];
            Register(user, pass);
        }
    }

    private void Register(String user, String pass) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.REGISTER);
        String request;
        try {
            request = protocole.make(
                    new Entry<>(ProtocoleSwinen.user, (Object) user),
                    new Entry<>(ProtocoleSwinen.pass, (Object) pass));
        } catch (Exception ex) {
            client.setEtat(ClientStatus.BadMessageMaked);
            request = null;
        }
        if (request != null) {
            client.getEmetteur().envoyer(request);
            String reponse = client.getEcouteur().lire();
            response(reponse);
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
                        client.setEtat(ClientStatus.RegisterComplete);
                        break;
                    case "1":
                        client.setEtat(ClientStatus.PseudoAlreadyUsed);
                        break;
                }
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
}
