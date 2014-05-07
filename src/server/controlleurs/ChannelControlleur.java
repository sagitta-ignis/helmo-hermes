/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controlleurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.channels.Channel;
import server.client.ClientManager;

/**
 *
 * @author David
 */
public class ChannelControlleur {

    private final List<ClientManager> clients;
    private final Map<String, Channel> channelList;
    private final ServeurControlleur server;

    public ChannelControlleur(ServeurControlleur server) {
        this.server = server;
        clients = new ArrayList<>();
        channelList = new HashMap<>();
        ajouterChannel("Acceuil");
    }

    public Channel ajouterChannel(String nom) {
        Channel nouveauChannel = new Channel(nom, server);
        channelList.put(nom, nouveauChannel);
        return nouveauChannel;
    }

    public void supprimerChannel(String nom) {
        channelList.remove(nom);
    }

    public List getChannelList() {
        List<String> listeDesChannels = new ArrayList<>();

        for (String mapKey : channelList.keySet()) {
            listeDesChannels.add(mapKey);
        }
        return listeDesChannels;
    }

    public void nouveauClient(ClientManager client) {
        clients.add(client);
        channelList.get("Acceuil").ajouterUtilisateurChannel(client);
    }

    public void transmettre(String message) {
        for (ClientManager client : clients) {
            if (client.getClient().getEtat() != 0) {
                client.envoyer(message);
            }
        }
    }

    public void transmettreChannel(String channel, String message) {
        channelList.get(channel).transmettre(message);
    }

    
    public void afficher(String message) {
        server.afficher(message);
    }
    

    public ClientManager clientConnected(String pseudo) {
        for (ClientManager client : clients) {
            if (client.getClient().getUsername().equals(pseudo)) {
                return client;
            }
        }
        return null;
    }

    public List<ClientManager> getConnected() {
        return clients;
    }

    public Channel getChannel(String nom) {
        return channelList.get(nom);
    }

    public void retirerUtilisateurChannel(String nom, ClientManager client) {
        Channel channel = channelList.get(nom);
        channel.retirerUtilisateurChannel(client);
        if(channel.getClientSize() == 0 &&  channel.isTemporaire()){
            supprimerChannel(nom);
        }
    }

    public void close() {
        for (ClientManager unClient : clients) {
            unClient.close();            
        }
        clients.removeAll(clients);
    }
 
    public void closeLogger() {
        for (String mapKey : channelList.keySet()) {
            channelList.get(mapKey).closeLogger();
        }
    }

}
