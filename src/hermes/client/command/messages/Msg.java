/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.command.messages;

import hermes.client.Client;
import hermes.client.ClientConnectionHandler;
import hermes.client.Emetteur;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Msg extends Message {

    public Msg(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if (verifierArguments(2)) {
            String receiver = (String) args[0];
            String message = (String) args[1];
            protocole.prepare(ProtocoleSwinen.MSG);
            String request;
            try {
                request = protocole.make(
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.receiver, receiver),
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, message)
                );
            } catch (Exception ex) {
                // Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            if (request != null && protocole.check(request)) {
                emetteur.envoyer(request);
            } else {
                client.afficher("-- le message n'a pas pu être transmis");
            }
        }
    }

}
