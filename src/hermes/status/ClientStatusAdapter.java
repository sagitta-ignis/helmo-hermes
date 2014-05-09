/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.status;

import hermes.client.Client;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientStatusAdapter extends StatusHandler implements ClientStatusHandler {



    public ClientStatusAdapter() {
        initEtats();
    }

    private void initEtats() {
        ajouterStatus(String.valueOf(Client.UnknownRequestReceived), new CommandArgument() {
            @Override
            public void execute() {
                unknownRequest();
            }
        });
        ajouterStatus(String.valueOf(Client.UnknownUser), new CommandArgument() {
            @Override
            public void execute() {
                unknownUser();
            }
        });
        ajouterStatus(String.valueOf(Client.MSGToSelf), new CommandArgument() {
            @Override
            public void execute() {
                msgToSelf();
            }
        });
        ajouterStatus(String.valueOf(Client.LoggedIn), new CommandArgument() {
            @Override
            public void execute() {
                loggedIn();
            }
        });
        ajouterStatus(String.valueOf(Client.AlreadyLoggedIn), new CommandArgument() {
            @Override
            public void execute() {
                alreadyLoggedIn();
            }
        });
        ajouterStatus(String.valueOf(Client.ConnexionBroken), new CommandArgument() {
            @Override
            public void execute() {
                connexionBroken();
            }
        });
        ajouterStatus(String.valueOf(Client.ConnexionLost), new CommandArgument() {
            @Override
            public void execute() {
                connexionLost();
            }
        });
        ajouterStatus(String.valueOf(Client.ServerShutDown), new CommandArgument() {
            @Override
            public void execute() {
                serverShutDown();
            }
        });
        ajouterStatus(String.valueOf(Client.RESPONSE), new CommandArgument() {
            @Override
            public void execute() {
                String digit = (String) args[0];
                String message = (String) args[1];
                response(digit, message);
            }
        });
        ajouterStatus(String.valueOf(Client.SALL), new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                String msg = (String) args[1];
                sAll(user, msg);
            }
        });
        ajouterStatus(String.valueOf(Client.MSG), new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                String msg = (String) args[1];
                msg(user, msg);
            }
        });
        ajouterStatus(String.valueOf(Client.SMSG), new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                String msg = (String) args[1];
                sMsg(user, msg);
            }
        });
        ajouterStatus(String.valueOf(Client.SDISCUSS), new CommandArgument() {
            @Override
            public void execute() {
                String channel = (String) args[0];
                String user = (String) args[1];
                String msg = (String) args[2];
                sDiscuss(channel, user, msg);
            }
        });
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
    public void sDiscuss(String channel, String user, String message) {
        
    }
}
