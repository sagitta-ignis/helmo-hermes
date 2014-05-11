/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.channel;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.channels.Channels;
import hermes.command.requete.base.Requete;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author d120041
 */
public class Here extends Requete {

    public Here(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            Channels channels = chat.getChannels();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.HERE);
            if (protocole.check(text)) {
                String channel = protocole.get(ProtocoleSwinen.channel);
                chat.entrer(channel, true);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
    
}
