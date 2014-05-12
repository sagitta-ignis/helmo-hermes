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
public class JoinChannel extends Requete {

    public JoinChannel(Chatter chat) {
        super(chat);
    }
    
    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.JOINCHANNEL);
            if (protocole.check(text)) {
                String channel = protocole.get(ProtocoleSwinen.channel);
                String user = protocole.get(ProtocoleSwinen.user);
                chat.getChannels().rejoindre(channel, user);
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
}
