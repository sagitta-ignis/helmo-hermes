/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.command.messages;

import hermes.client.Client;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Quit extends Message {

    public Quit(Client client, Protocole protocole) {
        super(client, protocole);
    }

    @Override
    public void execute() {
        if(verifierArguments(0)) {
            protocole.prepare(ProtocoleSwinen.QUIT);
            String request;
            try {
                request = protocole.make();
                if(request != null && protocole.check(request)) {
                    client.send(request);
                    client.disconnect();
                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(Quit.class.getName()).log(Level.SEVERE, null, ex);
            }
            client.print("-- bad protocol [s]");
        }
    }
}
