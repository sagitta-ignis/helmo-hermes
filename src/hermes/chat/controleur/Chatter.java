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
import hermes.client.channels.Channel;
import hermes.client.channels.Channels;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnreachableServerExeception;
import hermes.client.utilisateurs.Utilisateur;
import hermes.client.utilisateurs.Utilisateurs;
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
    private Utilisateur utilisateur;
    private final Utilisateurs utilisateurs;
    private final Channels channels;
    private Ecouter ecouteur;
    private final Authentifier authentifier;
    private final Overlayer overlayer;
    private final MessageLogger journal;
    private final ChatIRC fenetre;

    public Chatter(Authentifier a) {
        protocole = new ProtocoleSwinen();

        authentifier = a;
        overlayer = new Overlayer(this);
        messageHandler = new ClientMessageHandler(this);

        client = new Client(messageHandler);

        utilisateurs = new Utilisateurs();
        utilisateur = utilisateurs.instanciate("");
        channels = new ChannelsModel(utilisateurs);

        fenetre = new ChatIRC(this);
        journal = new MessageLogger(fenetre);

        fenetre.setOverlayer(overlayer);
        fenetre.setLogger(journal);
        fenetre.setChannels(((ChannelsModel) channels).getModel());

        utilisateurs.addObserver(fenetre);

        channels.addObserver(fenetre);

        ecouteur = new Ecouter(this);
    }

    public Client getClient() {
        return client;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
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
            utilisateur = utilisateurs.instanciate(username);
            fenetre.setTitre("IRC Helmo : " + username);
            return client.login(username, password);
        } catch (NotConnectedException ex) {
            Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void open() {
        if (client.canRun()) {
            client.addObserver(fenetre);
            client.addObserver(journal);
            client.addObserver(this);
            messageHandler.execute("/users");
            messageHandler.execute("/channels");
            messageHandler.execute("/whereiam");
            ecouteur = new Ecouter(this);
            ecouteur.start();
            fenetre.setVisible(true);
        } else {
            fenetre.avertir("Erreur", "le client n'a pas pu être ouvert");
        }
    }

    public boolean ecrire(String channel, String text, boolean publique) {
        if (text == null) {
            return false;
        }
        if (messageHandler.traiter(text)) {
            return true;
        }
        if (channel == null) {
            return false;
        }
        if (publique) {
            publique(channel, text);
        } else {
            prive(channel, text);
        }
        return true;
    }

    private void publique(String channel, String text) {
        CommandArgument message;
        message = messageHandler.get("discuss");
        message.setArgs(channel, text);
        message.execute();
    }

    private void prive(String user, String text) {
        CommandArgument message;
        message = messageHandler.get("msg");
        message.setArgs(user, text);
        message.execute();
    }

    public void setTyping(String channel, boolean b) {
        fenetre.setTyping(b);
        CommandArgument message;
        message = messageHandler.get("typing");
        message.setArgs(channel, b);
        message.execute();
    }

    public void fermer() {
        overlayer.fermer();
        try {
            journal.close();
            client.fermer();
        } catch (Exception ex) {
            String message = "le client n'a pas pu être fermé correctement";
            Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, message, ex);
            fenetre.avertir("Erreur", message);
        }
    }

    public void quitter() {
        messageHandler.traiter("/quit");
        System.exit(0);
    }

    private void retour() {
        retour(null, null);
    }

    private void retour(String titre, String message) {
        if (fenetre.isVisible()) {
            client.deleteObserver(fenetre);
            client.deleteObserver(journal);
            client.deleteObserver(this);
            if (titre != null && message != null) {
                fenetre.avertir(titre, message);
            }
            fenetre.setVisible(false);
            authentifier.ouvrir();
            fenetre.clear();
            channels.clear();
            utilisateurs.clear();
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

    @Override
    public void loggedOut() {
        retour();
        fermer();
    }

    public void entrer(String channel) {
        journal.entrer(channel);
        Channel c = channels.get(channel);
        if (c != null) {
            if (c.isProtege()) {
                String password = fenetre.demander(
                        "Channel protégé",
                        "Veuillez entrer un mot de passe :");
                if (password == null) {
                    return;
                }
                messageHandler.execute("/enter", channel, password);
            } else {
                messageHandler.execute("/enter", channel);
            }
        }
        Utilisateur u = utilisateurs.get(channel);
        if (u != null) {
            fenetre.entrer(channel);
        }
    }

    public void sortir(String channel) {
        Channel c = channels.get(channel);
        if (c != null) {
            messageHandler.execute("/exit", channel);
        }
        fenetre.sortir(channel);
    }
}
