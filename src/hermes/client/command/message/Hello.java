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
public class Hello extends Message {

    public Hello(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if (verifierArguments(2)) {
            String user = (String) args[0];
            String pass = (String) args[1];
            hello(user, pass);
        }
    }

    private boolean hello(String user, String pass) {
        protocole.prepare(ProtocoleSwinen.HELLO);
        String request;
        try {
            request = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.user, user),
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.pass, pass)
            );
        } catch (Exception ex) {
            // Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
            client.setEtat(Client.BadMessageMaked);
            request = null;
            //client.afficher("-- bad protocole [s]");
        }
        if (request != null) {
            waitResponse();
            emetteur.envoyer(request);
            ecouteur.lire();
            return true;
        }
        return false;
    }

    public void response(String response) {
        if (response != null) {
            protocole.prepare(ProtocoleSwinen.RESPONSE);
            if (protocole.check(response+"\r\n")) {
                switch (protocole.get(ProtocoleSwinen.digit)) {
                    case "0":
                        connection.setLogged(true);
                        client.setEtat(Client.LoggedIn);
                        //client.afficher("-- logged in user");
                        break;
                    case "1":
                        client.setEtat(Client.UnknownUser);
                        //client.afficher("-- unknown user");
                        break;
                    case "9":
                        client.setEtat(Client.BadProtocoleSended);
                        //client.afficher("-- bad protocole [r]");
                        break;
                }
            } else {
                client.setEtat(Client.BadProtocoleReceived);
                //client.afficher("-- bad protocole [s]");
            }
        }
    }
}
