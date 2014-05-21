/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.channel;

import hermes.chat.controleur.Chatter;
import hermes.chat.controleur.handler.ClientMessageHandler;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.client.channels.Channels;
import hermes.command.requete.base.Requete;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.List;

/**
 *
 * @author salto
 */
public class SChannels extends Requete {

    public SChannels(Chatter chat) {
        super(chat);
    }
    
    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SCHANNELS);
            if (protocole.check(text)) {
                List<String> channels = protocole.getAll(ProtocoleSwinen.channel);
                if(channels != null) remplir(channels);
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
    
    private void remplir(List<String> liste) {
        ClientMessageHandler handler = chat.getMessageHandler();
        Channels channels = chat.getChannels();
        for (String channel : liste) {
            channels.ajouter(channel);
            handler.execute("/infochannel", channel);
            handler.execute("/userschannel", channel);
        }
    }
}
