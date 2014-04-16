/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.client.command.message;

import hermes.client.Client;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Msg extends Message {

    private String receiver;
    private String message;
    public Msg(Client client) {
        super(client);
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
        protocole.prepare(ProtocoleSwinen.MSG);
            String request;
            try {
                request = protocole.make(
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.receiver, (Object) receiver),
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, (Object) message)
                );
            } catch (Exception ex) {
                // Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            if (request != null && protocole.check(request)) {
                waitResponse();
                System.out.print(request);
                emetteur.envoyer(request);
            } else {
                client.setEtat(Client.BadMessageMaked);
                //client.afficher("-- le message n'a pas pu être transmis");
            }
    }

    public void response(String response) {
        if (response != null) {
            protocole.prepare(ProtocoleSwinen.RESPONSE);
            if (protocole.check(response+"\r\n")) {
                switch (protocole.get(ProtocoleSwinen.digit)) {
                    case "0":
                        client.setEtat(Client.MSG, receiver, message);
                        break;
                    case "1":
                        client.setEtat(Client.UnknownUser);
                        break;
                    case "3":
                        client.setEtat(Client.MSGToSelf);
                        break;
                    case "9":
                        client.setEtat(Client.BadProtocoleSended);
                        break;
                }
            } else {
                client.setEtat(Client.BadProtocoleReceived);
                //client.afficher("-- bad protocole [s]");
            }
        }
    }
}
