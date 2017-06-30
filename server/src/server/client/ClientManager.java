/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.client;

import hermes.hermeslogger.HermesLogger;
import hermes.hermeslogger.LoggerImplements;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import server.channels.Channel;
import server.configuration.Configuration;
import server.configuration.ListUser;
import server.controlleurs.ChannelControlleur;
import server.controlleurs.ServeurControlleur;
import server.etat.Connecte;
import server.etat.EtatAbstract;
import server.etat.Waiting;
import server.response.SentResponse;
import server.response.channels.SentLeaveChannel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientManager {

    private static int increment = 1;

    private final Map<String, Channel> channels;
    private final ChannelControlleur channelManager;
    private final Configuration config;

    private final ThreadEcouteur ecouteur;
    private final ThreadSortie sortie;
    private final Client clientInfo;
    private final Socket socket;
    private final ListUser listeUtilisateurs;

    private final SentResponse response;
    private HermesLogger log;
    private Map<Integer, EtatAbstract> etat;

    public ClientManager(ChannelControlleur channel, Socket sck, ListUser listeUtilisateurs, Configuration config) throws IOException {
        this.listeUtilisateurs = listeUtilisateurs;
        this.config = config;

        channels = new ConcurrentHashMap<>();
        clientInfo = new Client(increment++);

        channelManager = channel;
        socket = sck;

        response = new SentResponse(this);

        ecouteur = new ThreadEcouteur(clientInfo, sck, this, channelManager);
        sortie = new ThreadSortie(sck, clientInfo, (int) (config.getThreadSleepSeconds() * 1000));
        initCommands();

        ecouteur.start();
        sortie.start();
    }

    private void initCommands() {
        etat = new HashMap<>();

        etat.put(0, new Waiting(this, clientInfo, listeUtilisateurs, response, channelManager));
        etat.put(3, new Connecte(clientInfo, channelManager, response, this, config,listeUtilisateurs));
    }

    public Client getClient() {
        return clientInfo;
    }

    public void traiter(String message) {
        EtatAbstract etatReaction = etat.get(clientInfo.getEtat());
        etatReaction.verifier(message);
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    public void envoyer(String message) {
        sortie.ajouter(message);
    }

    public void envoitImmediat(String message) {
        sortie.envoyer(message);
    }
    
    
    public void retirerChannel(String nom) {
        channels.remove(nom);
    }

    public void logClient(String auteur, String message) {
        if (log == null) {
            log = new LoggerImplements(clientInfo.getUsername());
        }

        try {
            log.ajouterMessage(auteur, message);
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            if (clientInfo.isOpened()) {
                if (log != null) {
                    log.close();
                    log = null;
                }

                clientInfo.setOpened(false);
                socket.close();
                ecouteur.close();
                sortie.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
            channelManager.afficher(response.getError(101));
        } catch (JAXBException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sayGoodByeToConnectedChannels() {
        SentLeaveChannel leaveChannel = new SentLeaveChannel(channelManager);

        for (String mapKey : channels.keySet()) {
            leaveChannel.sent(mapKey, clientInfo.getUsername());
            channels.get(mapKey).retirerUtilisateurChannel(this);
        }
    }

}
