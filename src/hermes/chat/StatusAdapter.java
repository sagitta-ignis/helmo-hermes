/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat;

import hermes.client.Client;
import hermes.client.StatusHandler;
import hermes.client.Utilisateurs;
import hermes.client.command.CommandMapper;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class StatusAdapter implements Observer, StatusHandler {

    private final CommandMapper statusReader;

    public StatusAdapter() {
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
        statusReader.ajouter(String.valueOf(Client.AlreadyLoggedIn), new CommandArgument() {
            @Override
            public void execute() {
                alreadyLoggedIn();
            }
        });
        statusReader.ajouter(String.valueOf(Client.ConnexionBroken), new CommandArgument() {
            @Override
            public void execute() {
                connexionBroken();
            }
        });
        statusReader.ajouter(String.valueOf(Client.ConnexionLost), new CommandArgument() {
            @Override
            public void execute() {
                connexionLost();
            }
        });
        statusReader.ajouter(String.valueOf(Client.ServerShutDown), new CommandArgument() {
            @Override
            public void execute() {
                serverShutDown();
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

    @Override
    public void unknownRequest() {

    }

    @Override
    public void unknownUser() {

    }

    @Override
    public void msgToSelf() {

    }

    @Override
    public void loggedIn() {

    }
    
    @Override
    public void alreadyLoggedIn() {
        
    }

    @Override
    public void connexionBroken() {

    }

    @Override
    public void connexionLost() {

    }

    @Override
    public void serverShutDown() {

    }

    @Override
    public void response(String digit, String message) {

    }

    @Override
    public void sAll(String user, String message) {

    }

    @Override
    public void msg(String user, String message) {

    }

    @Override
    public void sMsg(String user, String message) {

    }

    @Override
    public void sUsers(Utilisateurs users) {

    }

    @Override
    public void join(String user) {

    }

    @Override
    public void leave(String user) {

    }

}
