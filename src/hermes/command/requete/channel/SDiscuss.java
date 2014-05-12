/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.channel;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.command.requete.base.Requete;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author salto
 */
public class SDiscuss extends Requete {

    public SDiscuss(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SDISCUSS);
            if (protocole.check(text)) {
                String channel = protocole.get(ProtocoleSwinen.channel);
                String user = protocole.get(ProtocoleSwinen.user);
                String message = protocole.get(ProtocoleSwinen.message);
                client.setEtat(ClientStatus.SDISCUSS, channel, user, message);
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
    
}
