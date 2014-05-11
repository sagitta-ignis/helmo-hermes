/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.status;

import hermes.client.utilisateurs.Utilisateurs;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public interface ClientStatusHandler {

    void unknownRequest();

    void unknownUser();
    
    void alreadyLoggedIn();

    void msgToSelf();

    void loggedIn();
    
    void loggedOut();

    void connexionBroken();

    void connexionLost();

    void serverShutDown();

    void response(String digit, String message);

    void sAll(String user, String message);

    void msg(String user, String message);

    void sMsg(String user, String message);
    
    void sDiscuss(String channel, String user, String message);
}
