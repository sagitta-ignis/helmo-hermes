/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.command.requete.base;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class SMsg extends Requete {

    public SMsg(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SMSG);
            if (protocole.check(text)) {
                String sender = protocole.get(ProtocoleSwinen.sender);
                String message = protocole.get(ProtocoleSwinen.message);
                client.setEtat(ClientStatus.SMSG, sender, message);
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
}
