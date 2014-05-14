/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.message.channel;

import hermes.chat.controleur.Chatter;
import hermes.chat.vue.ChatIRC;
import hermes.client.Client;
import hermes.client.ClientStatus;
import hermes.command.message.base.Message;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.Entry;

/**
 *
 * @author d120041
 */
public class Enter extends Message {

    private String channel;
    
    public Enter(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if (verifierArguments(1, 2)) {
            channel = (String) args[0];
            String password = null;
            if (args.length == 2) {
                password = (String) args[1];
            }
            enter(channel, password);
        }
    }

    private void enter(String channel, String password) {
        Protocole protocole = chat.getProtocole();
        Client client = chat.getClient();
        protocole.prepare(ProtocoleSwinen.ENTER);
        String request;
        try {
            if (password == null) {
                request = protocole.make(
                        new Entry<>(ProtocoleSwinen.channel, (Object) channel));
            } else {
                request = protocole.make(
                        new Entry<>(ProtocoleSwinen.channel, (Object) channel),
                        new Entry<>(ProtocoleSwinen.pass, (Object) password));
            }
        } catch (Exception ex) {
            return;
        }
        if (request != null && protocole.check(request)) {
            waitResponse();
            client.getEmetteur().envoyer(request);
        } else {
            client.setEtat(ClientStatus.BadMessageMaked);
        }
    }

    @Override
    public void response(String response) {
        if (response != null) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            ChatIRC fenetre = chat.getFenetre();
            protocole.prepare(ProtocoleSwinen.RESPONSE);
            if (protocole.check(response + "\r\n")) {
                switch (protocole.get(ProtocoleSwinen.digit)) {
                    case "0":
                        fenetre.entrer(channel);
                        break;
                    case "1":
                        fenetre.avertir("Entrer dans "+channel, "channel inconnu");
                        break;
                    case "2":
                        fenetre.avertir("Entrer dans "+channel, "déjà entré dans ce channel");
                        break;
                    case "4":
                        fenetre.avertir("Entrer dans "+channel, "mot de passe incorrect");
                        break;
                    case "5":
                        fenetre.avertir("Entrer dans "+channel, "mot de passe requis");
                        break;
                    case "9":
                        
                        break;
                }
            } else {
            }
        }
    }
}
