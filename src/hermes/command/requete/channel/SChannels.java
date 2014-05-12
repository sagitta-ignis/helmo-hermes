/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.channel;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.client.channels.Channels;
import hermes.command.message.base.Message;
import hermes.command.message.channel.InfoChannel;
import hermes.command.message.channel.UsersChannel;
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
                channels.remove("SCHANNEL");
                remplir(channels);
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
    
    private void remplir(List<String> liste) {
        Channels channels = chat.getChannels();
        for (String channel : liste) {
            channels.ajouter(channel);
            Message info = new InfoChannel(chat);
            /*
            info.setArgs(channel);
            info.execute();
            */
            Message users = new UsersChannel(chat);
            users.setArgs(channel);
            users.execute();
        }
    }
}
