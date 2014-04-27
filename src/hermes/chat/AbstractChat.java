/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat;

import hermes.client.Utilisateurs;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class AbstractChat extends StatusAdapter implements Chat {

    private boolean typing;

    public boolean isTyping() {
        return typing;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }
    
    public AbstractChat() {
        typing = false;
    }

    @Override
    public void unknownRequest() {
        afficher("-- unknown request received");
    }

    @Override
    public void unknownUser() {
        afficher("-- utilisateur inconnu");
    }

    @Override
    public void msgToSelf() {
        afficher("-- impossible d'envoyer un message à soi-même (avez-vous besoin d'un psychologue ?)");
    }

    @Override
    public void loggedIn() {
        afficher("-- user logged in");
    }

    @Override
    public void response(String digit, String message) {
        if (!digit.equals("0")) {
            afficher("-- " + message);
        } else {
            System.out.println("-- " + message);
        }
    }

    @Override
    public void sAll(String user, String message) {
        afficher(user + " : " + message);
    }

    @Override
    public void msg(String user, String message) {
        afficher("[pm to] " + user + " : " + message);
    }

    @Override
    public void sMsg(String user, String message) {
        afficher("[pm from] " + user + " : " + message);
    }

    @Override
    public void sUsers(Utilisateurs users) {
        afficher("-- connectés : "+users.toString());
    }

    @Override
    public void join(String user) {
        afficher("-- " + user + " a rejoint le serveur");
    }

    @Override
    public void leave(String user) {
        afficher("-- " + user + " a quitté le serveur");
    }
}
