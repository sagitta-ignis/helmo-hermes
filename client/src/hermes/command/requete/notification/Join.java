/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.requete.notification;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.command.requete.base.Requete;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Join extends Requete {

    public Join(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.JOIN);
            if (protocole.check(text)) {
                String user = protocole.get(ProtocoleSwinen.user);
                chat.getUtilisateurs().ajouter(user);
            } else {
                client.setEtat(ClientStatus.BadProtocoleReceived);
            }
        }
    }
}
