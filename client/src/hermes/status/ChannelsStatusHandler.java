/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.status;

import hermes.client.channels.Channels;
import hermes.client.utilisateurs.Utilisateurs;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public interface ChannelsStatusHandler {
    
    void sChannel(Channels channels);
    
    void createChannel(String channel);
    
    void deleteChannel(String channel);
    
    void sUsersChannel(String channel, Utilisateurs users);

    void joinChannel(String channel, String user);

    void leaveChannel(String channel, String user);
}
