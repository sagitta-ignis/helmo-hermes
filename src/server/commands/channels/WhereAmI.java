/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.channels;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;
import server.client.ClientManager;
import server.etat.Waiting;

/**
 *
 * @author David
 */
public class WhereAmI extends CommandArgument {

    private final ClientManager manager;
    private final Protocole protocole;
    private final String defaultChannel;

    public WhereAmI(ClientManager manager,String defaultChannel) {
        this.defaultChannel = defaultChannel;
        this.manager = manager;
        protocole = new ProtocoleSwinen();
    }

    @Override
    public void execute() {

        protocole.prepare(ProtocoleSwinen.HERE);
        String messageProtocole = "";

        try {
            messageProtocole = protocole.make(
                    new AbstractMap.SimpleEntry<>(ProtocoleSwinen.channel, (Object) defaultChannel)
            );
        } catch (Exception ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }

        manager.envoyer(messageProtocole);
    }

}
