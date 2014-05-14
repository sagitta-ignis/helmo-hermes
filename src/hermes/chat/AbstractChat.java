/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat;

import hermes.client.channels.Channels;
import hermes.client.channels.ChannelsStatus;
import hermes.client.utilisateurs.Utilisateurs;
import hermes.client.utilisateurs.UtilisateursStatus;
import hermes.status.*;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class AbstractChat extends ClientStatusAdapter 
    implements Chat, UtilisateursStatusHandler, ChannelsStatusHandler {
    
    public AbstractChat() {
        ajouterStatus(UtilisateursStatus.SUsers, new CommandArgument() {
            @Override
            public void execute() {
                Utilisateurs users = (Utilisateurs) args[0];
                sUsers(users);
            }
        });
        ajouterStatus(UtilisateursStatus.Join, new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                join(user);
            }
        });
        ajouterStatus(UtilisateursStatus.Leave, new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                leave(user);
            }
        });
        ajouterStatus(ChannelsStatus.SChannels, new CommandArgument() {
            @Override
            public void execute() {
                Channels channels = (Channels) args[0];
                sChannel(channels);
            }
        });
        ajouterStatus(ChannelsStatus.Create, new CommandArgument() {
            @Override
            public void execute() {
                String channel = (String) args[0];
                createChannel(channel);
            }
        });
        ajouterStatus(ChannelsStatus.Delete, new CommandArgument() {
            @Override
            public void execute() {
                String channel = (String) args[0];
                deleteChannel(channel);
            }
        });
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
    public void entrer(String channel) {}

    @Override
    public void sortir(String channel) {}

    @Override
    public String demander(String titre, String message) {return null;}
    
    @Override
    public void sChannel(Channels channels) {}

    @Override
    public void createChannel(String channel) {}

    @Override
    public void deleteChannel(String channel) {}

    @Override
    public void sUsersChannel(String channel, Utilisateurs users) {}

    @Override
    public void joinChannel(String channel, String user) {}

    @Override
    public void leaveChannel(String channel, String user) {}
    
}
