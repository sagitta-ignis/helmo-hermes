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
public class ChannelAdded extends Requete {

    public ChannelAdded(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.CHANNELADDED);
            if (protocole.check(text)) {
                String channel = protocole.get(ProtocoleSwinen.channel);
                chat.getChannels().ajouter(channel);
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
    
}
