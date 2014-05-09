/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.command.message;

import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;

/**
 *
 * @author salto
 */
public class Discuss extends Message {

    public Discuss(Chatter chat) {
        super(chat);
    }

    @Override
    public void execute() {
        if(verifierArguments(2)) {
            Protocole protocole = chat.getProtocole();
            Client client = chat.getClient();
            String channel = (String) args[0];
            String message = (String) args[1];
            protocole.prepare(ProtocoleSwinen.DISCUSS);
            String request;
            try {
                request = protocole.make(
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object) channel),
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, (Object) message)
                );
            } catch (Exception ex) {
                // Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            if (request != null && protocole.check(request)) {
                client.getEmetteur().envoyer(request);
            } else {
                client.setEtat(Client.BadMessageMaked);
                //client.afficher("-- le message n'a pas pu être transmis");
            }
        }
    }
    
    @Override
    public void response(String response) {}
}
