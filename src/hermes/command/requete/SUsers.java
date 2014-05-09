/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class SUsers extends Requete {

    public SUsers(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String text = (String) args[0];
            protocole.prepare(ProtocoleSwinen.SUSERS);
            if (protocole.check(text)) {
                List<String> users = protocole.getAll(ProtocoleSwinen.user);
                users.remove("SUSERS");
                chat.getUtilisateurs().remplir(users);
            } else {
                client.setEtat(Client.BadProtocoleReceived);
            }
        }
    }
}
