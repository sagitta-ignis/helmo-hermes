/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.List;

/**
 *
 * @author salto
 */
public class SUsersChannel extends Requete {

    public SUsersChannel(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SUSERSCHANNEL);
            if (protocole.check(text)) {
                String channel = protocole.get(ProtocoleSwinen.channel);
                List<String> users = protocole.getAll(ProtocoleSwinen.user);
                users.remove("SUSERSCHANNEL");
                chat.getChannels().remplir(channel, users);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
}
