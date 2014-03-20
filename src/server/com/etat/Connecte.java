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
public class Connecte {

    private final ClientManager manager;

    
    private final SentResponse response;

    public Connecte(ClientManager clientManager, SentResponse response) {
        manager = clientManager; 
        this.response = response;
    }

    public void traiter(String message) {
        
        Protocole pt = new ProtocoleSwinen();
        MessageProtocole mp = pt.search(message);
        
        if(mp == null){
            response.sent(9);
            System.err.println("Protocol inconnu: "+message);
        }else{
            manager.executer(mp);
        }
    }
}
