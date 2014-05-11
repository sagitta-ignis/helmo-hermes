/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.channel;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.command.requete.base.Requete;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author salto
 */
public class SInfoChannel extends Requete {

    public SInfoChannel(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SINFOCHANNEL);
            if (protocole.check(text)) {
                String channel = protocole.get(ProtocoleSwinen.channel);
                String protege = protocole.get(ProtocoleSwinen.digit);
                chat.getChannels().setPublique(channel, protege.equals("1"));
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
    
}
