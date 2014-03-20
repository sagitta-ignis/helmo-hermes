/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.response;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.com.ClientManager;
import server.com.etat.Waiting;

/**
 *
 * @author David
 */
public class SentResponse {

    private final Protocole protocole;
    private final ClientManager manager;
    private final Map<Integer, Response> errorList;

    public SentResponse(ClientManager manager) {
        protocole = new ProtocoleSwinen();
        errorList = new HashMap<>();
        this.manager = manager;
        initErrors();
    }

    public void sent(int idError) {

        if (errorList.get(idError) != null) {
            protocole.prepare(ProtocoleSwinen.response);
            String response = "";
            try {
                response = protocole.make(
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.digit, errorList.get(idError).getId()),
                        new AbstractMap.SimpleEntry<>(ProtocoleSwinen.message, errorList.get(idError).getMessage())
                );
            } catch (Exception ex) {
                Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
            }

            manager.envoitImmediat(response);
        } else {
            System.err.println("Réponse" + idError + " inconnue");
        }
    }

    private void initErrors() {
        errorList.put(0, new Response("0", "OK"));
        errorList.put(1, new Response("1", "utilisateur inconnu"));
        errorList.put(9, new Response("9", "message invalide"));
    }
}
