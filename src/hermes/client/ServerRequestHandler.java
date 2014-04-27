/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.client;

import hermes.client.command.requete.*;
import hermes.protocole.message.MessageProtocole;
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
        requeteDeServeur.put(ProtocoleSwinen.JOIN, new Join(client));
        requeteDeServeur.put(ProtocoleSwinen.LEAVE, new Leave(client));
        requeteDeServeur.put(ProtocoleSwinen.RESPONSE, new Response(client));
        requeteDeServeur.put(ProtocoleSwinen.SALL, new SAll(client));
        requeteDeServeur.put(ProtocoleSwinen.SMSG, new SMsg(client));
        requeteDeServeur.put(ProtocoleSwinen.STYPING, new STyping(client));
        requeteDeServeur.put(ProtocoleSwinen.SUSERS, new SUsers(client));
        requeteDeServeur.put(ProtocoleSwinen.SERVERSHUTDOWN, new ServerShutDown(client));
    }

    public boolean parser(String request) {
        MessageProtocole messageProtocole = client.getProtocole().search(request);
        if (messageProtocole != null) {
            Requete requete = requeteDeServeur.get(messageProtocole);
            requete.setArgs(request);
            requete.execute();
            return true;
        }
        return false;
    }
}
