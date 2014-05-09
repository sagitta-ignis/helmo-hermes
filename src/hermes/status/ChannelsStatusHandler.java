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
public interface ChannelsStatusHandler {
    
    void sUsersChannel(Utilisateurs users);

    void joinChannel(String channel, String user);

    void leaveChannel(String channel, String user);
}
