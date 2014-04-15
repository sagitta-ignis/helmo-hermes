/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.command.message;

import hermes.client.Client;
import hermes.protocole.ProtocoleSwinen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Users extends Message {

    public Users(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            protocole.prepare(ProtocoleSwinen.USERS);
            String request;
            try {
                request = protocole.make();
                if (request != null && protocole.check(request)) {
                    System.out.print(request);
                    emetteur.envoyer(request);
                }
            } catch (Exception ex) {
                Logger.getLogger(Quit.class.getName()).log(Level.SEVERE, null, ex);
                client.setEtat(Client.BadProtocoleSended);
            }
        }
    }
    
    @Override
    public void response(String response) {}
}
