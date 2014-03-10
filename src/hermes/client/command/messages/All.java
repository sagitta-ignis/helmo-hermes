/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.command.messages;

import hermes.client.Client;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class All extends Message {

    public All(Client client, Protocole protocole) {
        super(client, protocole);
    }
    @Override
    public void execute() {
        if(verifierArguments(1)) {
            String message = (String) args[0];
            protocole.prepare(ProtocoleSwinen.ALL);
            String request;
            try {
                request = protocole.make(
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, message)
                );
            } catch (Exception ex) {
                // Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            if (request != null && protocole.check(request)) {
                client.send(request);
            } else {
                client.print("-- le message n'a pas pu être transmis");
            }
        }
    }

    
}
