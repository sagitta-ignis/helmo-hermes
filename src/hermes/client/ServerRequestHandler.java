/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client;

import hermes.client.command.messages.Message;
import hermes.client.command.messages.SAll;
import hermes.client.command.messages.SMsg;
import hermes.protocole.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServerRequestHandler {

    private final Client client;

    private final Map<MessageProtocole, Message> requeteDeServeur;

    public ServerRequestHandler(Client client) {
        this.client = client;
        requeteDeServeur = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        requeteDeServeur.put(ProtocoleSwinen.SALL, new SAll(client));
        requeteDeServeur.put(ProtocoleSwinen.SMSG, new SMsg(client));
    }

    public boolean parser(String text) {
        if (text.startsWith("[error]")) {
            return false;
        }
        Protocole protocole = client.getProtocole();
        for (Map.Entry<MessageProtocole, Message> entry : requeteDeServeur.entrySet()) {
            MessageProtocole messageProtocole = entry.getKey();
            protocole.prepare(messageProtocole);
            if (protocole.check(text)) {
                Message message = entry.getValue();
                message.setArgs(text);
                message.execute();
            }
        }
        return true;
    }
}
