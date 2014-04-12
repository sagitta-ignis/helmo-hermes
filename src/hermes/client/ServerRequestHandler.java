/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.client;

import hermes.client.command.requete.*;
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

    private final Map<MessageProtocole, Requete> requeteDeServeur;

    public ServerRequestHandler(Client client) {
        this.client = client;
        requeteDeServeur = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        requeteDeServeur.put(ProtocoleSwinen.RESPONSE, new Response(client));
        requeteDeServeur.put(ProtocoleSwinen.SALL, new SAll(client));
        requeteDeServeur.put(ProtocoleSwinen.SMSG, new SMsg(client));
        requeteDeServeur.put(ProtocoleSwinen.JOIN, new Join(client));
        requeteDeServeur.put(ProtocoleSwinen.LEAVE, new Leave(client));
    }

    public boolean parser(String text) {
        Protocole protocole = client.getProtocole();
        for (Map.Entry<MessageProtocole, Requete> entry : requeteDeServeur.entrySet()) {
            MessageProtocole messageProtocole = entry.getKey();
            protocole.prepare(messageProtocole);
            if (protocole.check(text)) {
                Requete requete = entry.getValue();
                requete.setArgs(text);
                requete.execute();
                return true;
            }
        }
        return false;
    }
}
