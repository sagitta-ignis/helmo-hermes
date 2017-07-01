/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.response;

import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.client.ClientManager;
import server.etat.Waiting;

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

    public SentResponse() {
        protocole = new ProtocoleSwinen();
        errorList = new HashMap<>();
        manager = null;
        initErrors();
    }

    public void sent(int idError) {
        manager.envoitImmediat(getError(idError));
    }

    public String getError(int id) {
        if (errorList.get(id) != null) {
            protocole.prepare(ProtocoleSwinen.RESPONSE);
            String response = "";
            try {
                response = protocole.make(
                        new Entry<>(ProtocoleSwinen.digit, (Object)errorList.get(id).getId()),
                        new Entry<>(ProtocoleSwinen.message, (Object)errorList.get(id).getMessage())
                );
            } catch (Exception ex) {
                Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
            }
            return response;
        } else {
            System.err.println("Réponse" + id + " inconnue");
        }
        return null;
    }

    private void initErrors() {
        errorList.put(0, new Response("0", "OK"));
        errorList.put(1, new Response("1", "INCONNU"));
        errorList.put(2, new Response("2", "EXISTANT"));
        errorList.put(3, new Response("3", "SOI-MEME"));
        errorList.put(4, new Response("4", "NOK"));
        errorList.put(5, new Response("5", "INCOMPLET"));
        errorList.put(9, new Response("9", "ILLISIBLE"));

        //Erreurs serveur
        errorList.put(101, new Response("101", "socket mal fermé"));
        errorList.put(102, new Response("102", "la reception a échouée"));
        errorList.put(103, new Response("103", "ecouteur mal fermé"));
        errorList.put(104, new Response("104", "création du socket serveur a échouée"));
        errorList.put(105, new Response("105", "connexion au client a échouée"));
    }
}
