/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.chat.controleur;

import hermes.chat.StatusAdapter;
import hermes.client.Client;
import hermes.client.Utilisateurs;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnopenableExecption;
import hermes.client.exception.UnreachableServerExeception;
import hermes.chat.vue.IRCChat;
import hermes.client.command.message.All;
import hermes.client.command.message.Msg;
import hermes.client.command.message.Typing;
import java.util.logging.Level;
import java.util.logging.Logger;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Chatter extends StatusAdapter {

    private final Utilisateurs users;
    private final Client client;
    
    private final Authentifier authentifier;
    private final Overlayer overlayer;
    private final MessageLogger journal;
    
    private final IRCChat fenetre;

    public Chatter(Authentifier a) {
        authentifier = a;
        overlayer = new Overlayer(this);
        journal = new MessageLogger();
        
        fenetre = new IRCChat(this, overlayer);
        
        users = new Utilisateurs();
        users.addObserver(fenetre);
        client = new Client(users);
        client.addObserver(this);
        client.addObserver(fenetre);
        client.addObserver(journal);
    }

    public Client getClient() {
        return client;
    }

    public IRCChat getFenetre() {
        return fenetre;
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
        try {
            client.ouvrir();
            if (client.canRun()) {
                fenetre.setVisible(true);
            }
        } catch (UnopenableExecption ex) {
            Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, null, ex);
            fenetre.avertir("Erreur", "le client n'a pas pu être ouvert");
        }
    }

    public void entrer(String user, String text) {
        if (text == null) {
            return;
        }
        if (!client.getMessageHandler().traiter(text)) {
            if (user == null || user.isEmpty()) {
                envoyer(text);
            } else {
                envoyer(user, text);
            }
        }
    }
    
    private void envoyer(String text) {
        CommandArgument message;
        message = new All(client);
        message.setArgs(text);
        message.execute();
    }

    private void envoyer(String user, String text) {
        CommandArgument message;
        message = new Msg(client);
        message.setArgs(user, text);
        message.execute();
    }
    
    public void setTyping(boolean b) {
        fenetre.setTyping(b);
        CommandArgument message;
        message = new Typing(client);
        message.setArgs(b);
        message.execute();
    }

    public void fermer() {
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
        if(fenetre.isVisible()) {
            if(titre != null && message != null) {
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
}
