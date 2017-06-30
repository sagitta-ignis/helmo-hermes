/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.message.channel;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.command.message.base.Message;
import hermes.command.message.base.Quit;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salto
 */
public class Channels extends Message {

    public Channels(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(0)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            protocole.prepare(ProtocoleSwinen.CHANNELS);
            String request;
            try {
                request = protocole.make();
                if (request != null && protocole.check(request)) {
                    client.getEmetteur().envoyer(request);
                    String response = client.getEcouteur().lire();
                    chat.getEcouteur().recevoir(response);
                }
            } catch (Exception ex) {
                Logger.getLogger(Quit.class.getName()).log(Level.SEVERE, null, ex);
                client.setEtat(ClientStatus.BadProtocoleSended);
            }
        }
    }

    @Override
    public void response(String response) {
    }
    
}
