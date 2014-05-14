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
import hermes.security.hash.Hash;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Hello extends Message {

    public Hello(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(2)) {
            String user = (String) args[0];
            String pass = (String) args[1];
            pass = hash(pass);
            hello(user, pass);
        }
    }

    private void hello(String user, String pass) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.HELLO);
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
                        client.getConnectionHandler().setLogged(true);
                        client.setEtat(ClientStatus.LoggedIn);
                        break;
                    case "1":
                        client.setEtat(ClientStatus.UnknownUser);
                        break;
                    case "2":
                        client.setEtat(ClientStatus.AlreadyLoggedIn);
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
