/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.etat;

import hermes.protocole.MessageProtocole;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import pattern.Command;
import server.com.ClientManager;
import server.com.response.SentResponse;

/**
 *
 * @author David
 */
public class Waiting {

    private final ClientManager manager;
    private final SentResponse response;
    private final Command hello;

    public Waiting(ClientManager clientManager, Command hello, SentResponse response) {
        manager = clientManager;
        this.response = response;
        this.hello = hello;
    }

    public void traiter(String message) {

        Protocole pt = new ProtocoleSwinen();
        MessageProtocole mp = pt.search(message);

        if (mp != null) {
            if (mp.equals(ProtocoleSwinen.HELLO)) {
                hello.execute(mp);
            }
        } else {
            response.response(9);
            manager.close();
        }
    }
}
