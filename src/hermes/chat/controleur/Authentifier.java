/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.controleur;

import hermes.chat.vue.Enregistrer;
import hermes.status.ClientStatusAdapter;
import hermes.chat.vue.Login;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import pattern.command.Command;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Authentifier extends ClientStatusAdapter implements ActionListener {

    private Login login;
    private Enregistrer enregistrer;
    private final Chatter chat;

    private Map<String, Command> commands;

    public Authentifier() {
        chat = new Chatter(this);
        chat.getClient().addObserver(this);
        initCommands();
    }

    public void ouvrir() {
        login = new Login(this);
        login.setVisible(true);

        enregistrer = new Enregistrer(this);

    }

    private void initCommands() {
        commands = new HashMap<>();
        commands.put("connecter", new Command() {
            @Override
            public void execute() {
                String ip = login.getIp();
                String username = login.getUsername();
                String password = login.getPassword();
                int port = login.getPort();

                if (!chat.getClient().getConnectionHandler().isConnected() && chat.connect(ip, port)) {
                    if (chat.login(username, password)) {
                        login.afficher("chargement en cours ...", Color.BLUE);
                        chat.open();
                        login.dispose();
                    }
                } else {
                    login.afficher("Unreachable server");
                }
            }
        });
        commands.put("openEnregistrer", new Command() {
            @Override
            public void execute() {
                enregistrer.setVisible(true);
            }
        });

        commands.put("enregitrer", new Command() {
            @Override
            public void execute() {
                String ip = login.getIp();
                int port = login.getPort();

                String username = enregistrer.getPseudo();
                String password = enregistrer.getPassword();
                String passwordVerif = enregistrer.getPasswordVerif();
                
                if(!password.equals(passwordVerif)){
                  enregistrer.afficher("Les mots de passes sont différents");
                  return;                  
                }
                
                if (chat.connect(ip, port)) {
                    
                    chat.getMessageHandler().execute("/register", username,password);
                    
                } else {
                    enregistrer.afficher("Unreachable server");
                }
            }
        });
    }

    @Override

    public void loggedIn() {

    }

    @Override
    public void unknownUser() {
        login.afficher("Incorrect username or password");
    }

    @Override
    public void alreadyLoggedIn() {
        login.afficher("User already logged in");
    }

    @Override
    public void pseudoAlreadyUsed() {
        enregistrer.afficher("Pseudo déjà utilisé");
    }

    @Override
    public void registerComplete() {
        login.afficher("Vous avez enregistré un compte");
        enregistrer.setVisible(false);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        Command commande = commands.get(ae.getActionCommand());
        if (commande != null) {
            commande.execute();
        }
    }
}
