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
public class Hello extends Message {

    public Hello(Client client, Protocole protocole) {
        super(client, protocole);
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
                if(request == null) return;
                client.send(request);
                String message = client.recevoir();
                if(message == null) return;
                protocole.prepare(ProtocoleSwinen.response);
                if (protocole.check(message) && protocole.get(ProtocoleSwinen.digit).equals("0")) {
                    client.print("-- " + user + " a rejoint le serveur");
                    client.setLogged(true);
                } else if (protocole.get(ProtocoleSwinen.digit).equals("1")) {
                    client.print("-- unknown user");
                } else if (protocole.get(ProtocoleSwinen.digit).equals("9")) {
                    client.print("-- bad protocole [r]");
                }
            } catch (Exception ex) {
                // Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
                client.print("-- bad protocole [s]");
            }
        }
    }

}
