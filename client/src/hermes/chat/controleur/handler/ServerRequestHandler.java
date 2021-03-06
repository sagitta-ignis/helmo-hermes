/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.chat.controleur.handler;

import hermes.chat.controleur.Chatter;
import hermes.command.requete.channel.*;
import hermes.command.requete.notification.*;
import hermes.command.requete.base.*;
import hermes.protocole.ProtocoleSwinen;
import hermes.protocole.message.MessageProtocole;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServerRequestHandler {

    private final Chatter chat;

    private final Map<MessageProtocole, Requete> requeteDeServeur;

    public ServerRequestHandler(Chatter chat) {
        this.chat = chat;
        requeteDeServeur = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        requeteDeServeur.put(ProtocoleSwinen.JOIN, new Join(chat));
        requeteDeServeur.put(ProtocoleSwinen.LEAVE, new Leave(chat));
        requeteDeServeur.put(ProtocoleSwinen.RESPONSE, new Response(chat));
        requeteDeServeur.put(ProtocoleSwinen.SALL, new SAll(chat));
        requeteDeServeur.put(ProtocoleSwinen.SMSG, new SMsg(chat));
        requeteDeServeur.put(ProtocoleSwinen.STYPING, new STyping(chat));
        requeteDeServeur.put(ProtocoleSwinen.SUSERS, new SUsers(chat));
        requeteDeServeur.put(ProtocoleSwinen.SCHANNELS, new SChannels(chat));
        requeteDeServeur.put(ProtocoleSwinen.SUSERSCHANNEL, new SUsersChannel(chat));
        requeteDeServeur.put(ProtocoleSwinen.CHANNELADDED, new ChannelAdded(chat));
        requeteDeServeur.put(ProtocoleSwinen.CHANNELREMOVED, new ChannelRemoved(chat));
        requeteDeServeur.put(ProtocoleSwinen.SINFOCHANNEL, new SInfoChannel(chat));
        requeteDeServeur.put(ProtocoleSwinen.HERE, new Here(chat));
        requeteDeServeur.put(ProtocoleSwinen.SDISCUSS, new SDiscuss(chat));
        requeteDeServeur.put(ProtocoleSwinen.JOINCHANNEL, new JoinChannel(chat));
        requeteDeServeur.put(ProtocoleSwinen.LEAVECHANNEL, new LeaveChannel(chat));
        requeteDeServeur.put(ProtocoleSwinen.SERVERSHUTDOWN, new ServerShutDown(chat));
    }

    public boolean parser(String request) {
        if (request == null) {
            return false;
        }
        MessageProtocole messageProtocole = chat.getProtocole().search(request);
        if (messageProtocole != null) {
            Requete requete = requeteDeServeur.get(messageProtocole);
            if (requete != null) {
                requete.setArgs(request);
                requete.execute();
                return true;
            }
        }
        return false;
    }
}
