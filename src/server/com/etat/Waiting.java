/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.etat;

import hermes.protocole.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import server.com.ClientManager;
import server.com.response.SentResponse;

/**
 *
 * @author David
 */
public class Waiting {

    private final ClientManager manager;
    private final SentResponse response;

    public Waiting(ClientManager clientManager, SentResponse response) {
        manager = clientManager;
        this.response = response;
    }

    public void traiter(String message) {

        Protocole pt = new ProtocoleSwinen();
        MessageProtocole mp = pt.search(message);

        if (mp != null) {
            if (mp.equals(ProtocoleSwinen.HELLO)) {
                manager.executer(mp);
            }
        } else {
            response.sent(9);
            manager.close();
        }
    }
}
