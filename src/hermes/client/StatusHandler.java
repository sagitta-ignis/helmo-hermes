/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public interface StatusHandler {

    void unknownRequest();

    void unknownUser();
    
    void alreadyLoggedIn();

    void msgToSelf();

    void loggedIn();

    void connexionBroken();

    void connexionLost();

    void serverShutDown();

    void response(String digit, String message);

    void sAll(String user, String message);

    void msg(String user, String message);

    void sMsg(String user, String message);

    void sUsers(Utilisateurs users);

    void join(String user);

    void leave(String user);
}
