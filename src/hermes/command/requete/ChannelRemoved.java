/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author salto
 */
public class ChannelRemoved extends Requete {

    public ChannelRemoved(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.STYPING);
            if (protocole.check(text)) {
                String channel = protocole.get(ProtocoleSwinen.channel);
                chat.getChannels().retirer(channel);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }    
}
