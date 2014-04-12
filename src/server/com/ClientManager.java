/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerControleur;
import server.com.etat.Connecte;
import server.com.etat.EtatAbstract;
import server.com.etat.Waiting;
import server.com.response.SentAll;
import server.com.response.SentResponse;
import server.configuration.ListUser;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientManager {

    private static int increment = 1;

    private final ServerControleur server;

    private final EcouteurClient ecouteur;
    private final SortieClient sortie;
    private final Client clientInfo;
    private final Socket socket;
    private final ListUser listeUtilisateurs;

    private final SentResponse response;
    private final SentAll sentAll;

    private Map<Integer, EtatAbstract> etat;
   
    public ClientManager(ServerControleur srv, Socket sck, ListUser listeUtilisateurs) throws IOException {
        this.listeUtilisateurs = listeUtilisateurs;
        clientInfo = new Client(increment++);
        
        server = srv;
        socket = sck;
        
        response = new SentResponse(this);
        sentAll = new SentAll(server);
     
        ecouteur = new EcouteurClient(clientInfo, sck, this, server);
        sortie = new SortieClient(sck);
        initCommands();

        ecouteur.start();
        sortie.start();
    }

    private void initCommands() {
        etat = new HashMap<>();

        etat.put(0,new Waiting(this, clientInfo, listeUtilisateurs, response, server));
        etat.put(3,new Connecte(clientInfo, sentAll, server, response, this));
    }

    public Client getClient() {
        return clientInfo;
    }

    public void traiter(String message) {        
        EtatAbstract etatReaction = etat.get(clientInfo.getEtat());
        etatReaction.verifier(message);
    }

    public void envoyer(String message) {
        sortie.ajouter(message);
    }

    public void envoitImmediat(String message) {
        sortie.envoyer(message);
    }

    public void close() {
        try {
            server.retirer(this);
            clientInfo.setOpened(false);
            ecouteur.close();
            sortie.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);            
            server.afficher(response.getError(101));
        }
    }

}
