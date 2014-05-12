/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.controleur;

import hermes.status.ClientStatusAdapter;
import hermes.chat.vue.Login;
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
                if (chat.connect(ip,port)) {
                    if (chat.login(username, password)) {
                        chat.open();
                        login.dispose();
                    }
                } else {
                    login.print("Unreachable server");
                }
            }
        });
    }

    @Override
    public void loggedIn() {
        login.print("chargement en cours ...");
    }

    @Override
    public void unknownUser() {
        login.print("Incorrect username or password");
    }

    @Override
    public void alreadyLoggedIn() {
        login.print("User already logged in");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Command commande = commands.get(ae.getActionCommand());
        if(commande != null) {
            commande.execute();
        }
    }
}
