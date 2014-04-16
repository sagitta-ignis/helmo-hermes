/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat;

import hermes.client.Client;
import hermes.client.StatusHandler;
import hermes.client.Utilisateurs;
import pattern.command.CommandArgument;
import hermes.client.command.CommandMapper;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class AbstractChat implements Observer, StatusHandler, Chat {

    private boolean typing;
    private final CommandMapper statusReader;

    public boolean isTyping() {
        return typing;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }
    
    public AbstractChat() {
        typing = false;
        statusReader = new CommandMapper();
        initEtats();
    }
    
    public void ajouterStatus(Object key, CommandArgument command) {
        statusReader.ajouter(key, command);
    }

    private void initEtats() {
        statusReader.ajouter(String.valueOf(Client.UnknownRequestReceived), new CommandArgument() {
            @Override
            public void execute() {
                unknownRequest();
            }
        });
        statusReader.ajouter(String.valueOf(Client.UnknownUser), new CommandArgument() {
            @Override
            public void execute() {
                unknownUser();
            }
        });
        statusReader.ajouter(String.valueOf(Client.MSGToSelf), new CommandArgument() {
            @Override
            public void execute() {
                msgToSelf();
            }
        });
        statusReader.ajouter(String.valueOf(Client.LoggedIn), new CommandArgument() {
            @Override
            public void execute() {
                loggedIn();
            }
        });
        statusReader.ajouter(String.valueOf(Client.RESPONSE), new CommandArgument() {
            @Override
            public void execute() {
                String digit = (String) args[0];
                String message = (String) args[1];
                response(digit, message);
            }
        });
        statusReader.ajouter(String.valueOf(Client.SALL), new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                String msg = (String) args[1];
                sAll(user, msg);
            }
        });
        statusReader.ajouter(String.valueOf(Client.MSG), new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                String msg = (String) args[1];
                msg(user, msg);
            }
        });
        statusReader.ajouter(String.valueOf(Client.SMSG), new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                String msg = (String) args[1];
                sMsg(user, msg);
            }
        });
        statusReader.ajouter(Utilisateurs.SUsers, new CommandArgument() {
            @Override
            public void execute() {
                Utilisateurs users = (Utilisateurs) args[0];
                sUsers(users);
            }
        });
        statusReader.ajouter(Utilisateurs.Join, new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                join(user);
            }
        });
        statusReader.ajouter(Utilisateurs.Leave, new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                leave(user);
            }
        });
    }

    public void unknownRequest() {
        afficher("-- unknown request received");
    }

    public void unknownUser() {
        afficher("-- utilisateur inconnu");
    }

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

    @Override
    public synchronized void update(Observable o, Object args) {
        if (o instanceof Client) {
            String etat = String.valueOf(((Client) o).getEtat());
            statusReader.execute(etat, (Object[]) args);
        }
        if (o instanceof Utilisateurs) {
            Object arguments[] = (Object[]) args;
            statusReader.execute(arguments[0], Arrays.copyOfRange(arguments, 1, arguments.length));
        }
    }
}
