/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.notification;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.client.channels.Channel;
import hermes.client.channels.Channels;
import hermes.client.utilisateurs.Utilisateur;
import hermes.command.requete.base.Requete;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class STyping extends Requete {

    public STyping(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            String text = (String) args[0];
            sTyping(text);
        }
    }

    private void sTyping(String text) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        Channels chs = chat.getChannels();
        protocole.prepare(ProtocoleSwinen.STYPING);
        if (protocole.check(text)) {
            String channel = protocole.get(ProtocoleSwinen.channel);
            String user = protocole.get(ProtocoleSwinen.user);
            String digit = protocole.get(ProtocoleSwinen.digit);
            Channel ch = chs.get(channel);
            if(ch != null) {
                Utilisateur u = ch.getUtilisateur(user);
                if(u!=null) u.setTyping(digit.equals("1"));
            }
        } else {
            client.setEtat(ClientStatus.BadProtocoleReceived);
        }
    }
}
