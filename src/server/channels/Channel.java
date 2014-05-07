/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.channels;

import hermes.hermeslogger.HermesLogger;
import hermes.hermeslogger.LoggerImplements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import server.controlleurs.ServeurControlleur;
import server.client.ClientManager;

/**
 *
 * @author David
 */
public class Channel {

    private final ServeurControlleur server;
    private HermesLogger log;

    private final List<ClientManager> clientsChannel;
    private final String nom;
    private String motDePasse;
    private String administrateur;
    private boolean temporaire;

    public Channel(String nom, ServeurControlleur server) {
        motDePasse = null;
        temporaire = true;
        this.server = server;
        this.nom = nom;
        log = new LoggerImplements(nom);
        clientsChannel = new ArrayList<>();
    }

    public void ajouterUtilisateurChannel(ClientManager client) {
        clientsChannel.add(client);
        client.getChannels().put(nom, this);
    }

    public void retirerUtilisateurChannel(ClientManager client) {
        clientsChannel.remove(client);
        client.getChannels().remove(nom);
    }

    public int getClientSize() {
        return clientsChannel.size();
    }

    public void transmettre(String message) {
        for (ClientManager client : clientsChannel) {
            client.envoyer(message);
        }
    }

    public void afficher(String message) {
        try {
            if (log != null) {
                log.ajouterMessage(message);
            }
            server.afficher(message);
        } catch (IOException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMotDePasse(String mdp) {
        motDePasse = mdp;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setAdministrateur(String admin) {
        administrateur = admin;
    }

    public String getAdministrateur() {
        return administrateur;
    }

    public void closeLogger() {
        try {
            log.close();
            log = null;
        } catch (IOException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the temporaire
     */
    public boolean isTemporaire() {
        return temporaire;
    }

    /**
     * @param temporaire the temporaire to set
     */
    public void setTemporaire(boolean temporaire) {
        this.temporaire = temporaire;
    }
}
