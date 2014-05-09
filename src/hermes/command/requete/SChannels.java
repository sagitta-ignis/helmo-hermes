/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.channels.Channels;
import hermes.command.message.InfoChannel;
import hermes.command.message.Message;
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
                channels.remove("SCHANNEL");
                remplir(channels);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
    
    private void remplir(List<String> liste) {
        Channels channels = chat.getChannels();
        for (String channel : liste) {
            channels.ajouter(channel);
            Message m = new InfoChannel(chat);
            m.setArgs(channel);
            m.execute();
        }
    }
}
