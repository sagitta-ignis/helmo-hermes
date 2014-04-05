/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.command.messages;

import hermes.client.Client;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Hello extends Message {

    public Hello(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if (verifierArguments(2)) {
            String user = (String) args[0];
            String pass = (String) args[1];
            protocole.prepare(ProtocoleSwinen.HELLO);
            String request;
            try {
                request = protocole.make(
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user, user),
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.pass, pass)
                );
                if (request == null) {
                    return;
                }
                emetteur.envoyer(request);
                String message = ecouteur.lire();
                if (message == null) {
                    return;
                }
                protocole.prepare(ProtocoleSwinen.RESPONSE);
                if (protocole.check(message)) {
                    switch (protocole.get(ProtocoleSwinen.digit)) {
                        case "0":
                            client.afficher("-- " + user + " a rejoint le serveur");
                            connection.setLogged(true);
                            break;
                        case "1":
                            client.afficher("-- unknown user");
                            break;
                        case "9":
                            client.afficher("-- bad protocole [r]");
                            break;
                    }
                } else {
                    client.afficher("-- bad protocole [s]");
                }
            } catch (Exception ex) {
                // Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
                client.afficher("-- bad protocole [s]");
            }
        }
    }

}
