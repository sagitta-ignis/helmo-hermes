/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat;

import hermes.client.utilisateurs.Utilisateurs;
import hermes.status.*;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class AbstractChat extends ClientStatusAdapter 
    implements Chat, UtilisateursStatusHandler {
    
    public AbstractChat() {

    }
   

    @Override
    public void unknownRequest() {
        afficher(CURRENT, SERVER, "-- unknown request received");
    }

    @Override
    public void unknownUser() {
        afficher(CURRENT, SERVER, "-- utilisateur inconnu");
    }

    @Override
    public void msgToSelf() {
        afficher(CURRENT, SERVER, "-- impossible d'envoyer un message à soi-même (avez-vous besoin d'un psychologue ?)");
    }

    @Override
    public void loggedIn() {
        afficher(CURRENT, SERVER, "-- user logged in");
    }

    @Override
    public void response(String digit, String message) {
        if (!digit.equals("0")) {
            afficher(CURRENT, SERVER, "-- " + message);
        } else {
            System.out.println("-- " + message);
        }
    }

    @Override
    public void sAll(String user, String message) {
        afficher(ALL, user, message);
    }

    @Override
    public void msg(String user, String message) {
        afficher(user, ME, message);
    }

    @Override
    public void sMsg(String user, String message) {
        afficher(user, user, message);
    }
    
    @Override
    public void sDiscuss(String channel, String user, String message) {
        afficher(channel, user, message);
    }

    @Override
    public void sUsers(Utilisateurs users) {
        afficher(SERVER, SERVER,"-- connectés : "+users.toString());
    }

    @Override
    public void join(String user) {
        afficher(SERVER, SERVER, "-- " + user + " a rejoint le serveur");
    }

    @Override
    public void leave(String user) {
        afficher(SERVER, SERVER, "-- " + user + " a quitté le serveur");
    }

    @Override
    public void afficher(String channel, String user, String texte) {}

    @Override
    public void avertir(String titre, String message) {}

    @Override
    public void alreadyLoggedIn() {}

    @Override
    public void connexionBroken() {}

    @Override
    public void connexionLost() {}

    @Override
    public void serverShutDown() {}

    @Override
    public void entrer(String channel, boolean publique) {}

    @Override
    public void sortir(String channel) {}
}
