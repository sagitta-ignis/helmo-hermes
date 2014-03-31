/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.etat;

import hermes.protocole.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.HashMap;
import java.util.Map;
import pattern.CommandArgument;
import pattern.command.Command;
import server.com.response.SentResponse;

/**
 *
 * @author David
 */
public abstract class EtatAbstract {

    private MessageProtocole mp;
    private final Protocole pt;
    private final SentResponse response;
    private boolean initialiser = false;
    protected Map<MessageProtocole, Command> commandsProtocole;

    public EtatAbstract(SentResponse response) {
        commandsProtocole = new HashMap<>();
        pt = new ProtocoleSwinen();
        this.response = response;
    }

    public abstract void initialiserCommandes();

    public final void verifier(String message) {
        if (!initialiser) {
            initialiserCommandes();
            initialiser = true;
        }        
        check(message);
    }

    public void check(String message) {
        mp = pt.search(message);

        if (mp
                == null) {
            response.sent(9);
            System.err.println("Protocol inconnu: " + message);
        } else {
            traiter(mp);
        }
    }

    private void traiter(MessageProtocole messageProtocole) {

        Command command = commandsProtocole.get(messageProtocole);

        if (command != null) {
            ((CommandArgument) command).setArgs(messageProtocole);
            command.execute();

        } else {
            response.sent(9);
        }
    }

}
