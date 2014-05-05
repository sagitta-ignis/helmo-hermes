/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controlleur.ChannelControlleur;
import server.controlleur.ServeurControlleur;
import server.etat.Connecte;
import server.etat.EtatAbstract;
import server.etat.Waiting;
import server.response.SentAll;
import server.response.SentResponse;
import server.configuration.Configuration;
import server.configuration.ListUser;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientManager {

    private static int increment = 1;

    private final ChannelControlleur channelManager;
    private final Configuration config;
    
    private final ThreadEcouteur ecouteur;
    private final ThreadSortie sortie;
    private final Client clientInfo;        
    private final Socket socket;
    private final ListUser listeUtilisateurs;

    private final SentResponse response;

    private Map<Integer, EtatAbstract> etat;
   
    public ClientManager(ChannelControlleur channel, Socket sck, ListUser listeUtilisateurs, Configuration config) throws IOException {
        this.listeUtilisateurs = listeUtilisateurs;
        this.config = config;
        clientInfo = new Client(increment++);
        
        channelManager = channel;
        socket = sck;
        
        response = new SentResponse(this);
     
        ecouteur = new ThreadEcouteur(clientInfo, sck, this, channelManager);
        sortie = new ThreadSortie(sck, config.getThreadSleepSeconds() * 1000);
        initCommands();

        ecouteur.start();
        sortie.start();
    }

    private void initCommands() {
        etat = new HashMap<>();

        etat.put(0,new Waiting(this, clientInfo, listeUtilisateurs, response, channelManager));
        etat.put(3,new Connecte(clientInfo, channelManager, response, this,config));
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
            clientInfo.setOpened(false);
            socket.close();            
            ecouteur.close();
            sortie.close();
        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);            
            channelManager.afficher(response.getError(101));
        }
    }

}
