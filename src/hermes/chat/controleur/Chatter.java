/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.chat.controleur;

import hermes.chat.controleur.handler.ClientMessageHandler;
import hermes.chat.model.ChannelsModel;
import hermes.chat.thread.Ecouter;
import hermes.chat.vue.ChatIRC;
import hermes.client.Client;
import hermes.client.channels.Channels;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnreachableServerExeception;
import hermes.client.utilisateurs.Utilisateurs;
import hermes.command.message.Discuss;
import hermes.command.message.Msg;
import hermes.command.message.Typing;
import hermes.protocole.Protocole;
import hermes.protocole.ProtocoleSwinen;
import hermes.status.ClientStatusAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Chatter extends ClientStatusAdapter {

    private final ClientMessageHandler messageHandler;

    private final Protocole protocole;
    private final Client client;
    private final Utilisateurs utilisateurs;
    private final Channels channels;

    private final Ecouter ecouteur;

    private final Authentifier authentifier;
    private final Overlayer overlayer;
    private final MessageLogger journal;

    private final ChatIRC fenetre;

    public Chatter(Authentifier a) {
        protocole = new ProtocoleSwinen();
        
        authentifier = a;
        overlayer = new Overlayer(this);
        messageHandler = new ClientMessageHandler(this);
        journal = new MessageLogger();
        
        channels = new ChannelsModel();
        
        fenetre = new ChatIRC(this, overlayer);
        fenetre.setChannels(((ChannelsModel)channels).getModel());
        
        utilisateurs = new Utilisateurs();
        utilisateurs.addObserver(fenetre);
        
        client = new Client(messageHandler);
        client.addObserver(this);
        client.addObserver(fenetre);
        client.addObserver(journal);
        
        ecouteur = new Ecouter(this);
    }

    public Client getClient() {
        return client;
    }

    public ChatIRC getFenetre() {
        return fenetre;
    }

    public Utilisateurs getUtilisateurs() {
        return utilisateurs;
    }

    public Channels getChannels() {
        return channels;
    }

    public Ecouter getEcouteur() {
        return ecouteur;
    }

    public Protocole getProtocole() {
        return protocole;
    }

    public ClientMessageHandler getMessageHandler() {
        return messageHandler;
    }

    public boolean connect(String ip, int port) {
        try {
            client.connect(ip, port);
            return true;
        } catch (UnreachableServerExeception ex) {
            // Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            return client.login(username, password);
        } catch (NotConnectedException ex) {
            Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void open() {
        if (client.canRun()) {
            messageHandler.traiter("/users");
            messageHandler.traiter("/channels");
            ecouteur.start();
            fenetre.setVisible(true);
        } else {
            fenetre.avertir("Erreur", "le client n'a pas pu être ouvert");
        }
    }

    public void ecrire(String channel, String text, boolean publique) {
        if (text == null || channel == null) {
            return;
        }
        if (!messageHandler.traiter(text)) {
            if (publique) {
                publique(channel, text);
            } else {
                prive(channel, text);
            }
        }
    }

    private void publique(String channel, String text) {
        CommandArgument message;
        message = new Discuss(this);
        message.setArgs(channel, text);
        message.execute();
    }

    private void prive(String user, String text) {
        CommandArgument message;
        message = new Msg(this);
        message.setArgs(user, text);
        message.execute();
    }

    public void setTyping(boolean b) {
        fenetre.setTyping(b);
        CommandArgument message;
        message = new Typing(this);
        message.setArgs(b);
        message.execute();
    }

    public void fermer() {
        messageHandler.traiter("/quit");
        overlayer.fermer();
        try {
            journal.close();
            client.fermer();
        } catch (Exception ex) {
            String message = "le client n'a pas pu être fermé correctement";
            //Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, message, ex);
            fenetre.avertir("Erreur", message);
        }
    }

    public void quitter() {
        fermer();
        System.exit(0);
    }

    private void retour() {
        retour(null, null);
    }

    private void retour(String titre, String message) {
        if (fenetre.isVisible()) {
            if (titre != null && message != null) {
                fenetre.avertir(titre, message);
            }
            fenetre.setVisible(false);
            authentifier.ouvrir();
        }
    }

    @Override
    public void connexionBroken() {
        retour("Connexion", "La connexion a été coupée");
    }

    @Override
    public void connexionLost() {
        retour("Connexion", "La connexion a été perdue");
    }

    @Override
    public void serverShutDown() {
        fermer();
        retour("Server", "Le serveur est en train de se fermer");
    }

    public void entrer(String channel, boolean publique) {
        fenetre.entrer(channel, publique);
    }

    public void sortir(String channel) {
        fenetre.sortir(channel);
    }
}
