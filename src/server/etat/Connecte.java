/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.etat;

import hermes.protocole.ProtocoleSwinen;
import server.controlleurs.ChannelControlleur;
import server.client.Client;
import server.client.ClientManager;
import server.commands.All;
import server.commands.Msg;
import server.commands.Quit;
import server.commands.Typing;
import server.commands.Users;
import server.commands.channels.Channels;
import server.commands.channels.CreateChannel;
import server.commands.channels.DeleteChannel;
import server.commands.channels.Discuss;
import server.commands.channels.Enter;
import server.commands.channels.Exit;
import server.commands.channels.InfoChannel;
import server.commands.channels.WhereAmI;
import server.response.SentResponse;
import server.configuration.Configuration;

/**
 *
 * @author David
 */
public class Connecte extends EtatAbstract {

    private final Client client;
    private final ChannelControlleur channelManager;
    private final SentResponse response;
    private final ClientManager manager;
    private final Configuration config;

    public Connecte(Client client, ChannelControlleur channelManager, SentResponse response, ClientManager manager, Configuration config) {
        super(response);
        this.client = client;
        this.channelManager = channelManager;
        this.response = response;
        this.manager = manager;
        this.config = config;
    }

    @Override
    public void initialiserCommandes() {
        commandsProtocole.put(ProtocoleSwinen.ALL, new All(channelManager,manager));
        commandsProtocole.put(ProtocoleSwinen.MSG, new Msg(client, channelManager, response));
        commandsProtocole.put(ProtocoleSwinen.QUIT, new Quit(manager, channelManager));
        commandsProtocole.put(ProtocoleSwinen.USERS, new Users(manager, channelManager));
        commandsProtocole.put(ProtocoleSwinen.TYPING, new Typing(channelManager, client));
        initialiserCommandesChannels();
    }

    private void initialiserCommandesChannels() {
        commandsProtocole.put(ProtocoleSwinen.CHANNELS, new Channels(channelManager, manager));
        commandsProtocole.put(ProtocoleSwinen.CREATECHANNEL, new CreateChannel(channelManager, manager));
        commandsProtocole.put(ProtocoleSwinen.DELETECHANNEL, new DeleteChannel(channelManager, manager));
        commandsProtocole.put(ProtocoleSwinen.DISCUSS, new Discuss(channelManager, manager));
        commandsProtocole.put(ProtocoleSwinen.ENTER, new Enter(channelManager, manager));
        commandsProtocole.put(ProtocoleSwinen.EXIT, new Exit(channelManager, manager));
        commandsProtocole.put(ProtocoleSwinen.INFOCHANNEL, new InfoChannel(channelManager, manager));
        commandsProtocole.put(ProtocoleSwinen.WHEREAMI, new WhereAmI(manager,config.getDefaultChannel()));
    }
}
