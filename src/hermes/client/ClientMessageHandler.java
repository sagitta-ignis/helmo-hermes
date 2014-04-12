/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client;

import pattern.command.CommandArgument;
import hermes.client.command.message.*;
import hermes.client.controleur.Chatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientMessageHandler {

    private final Chatter chatter;
    private final Map<String, CommandArgument> requetes;

    public ClientMessageHandler(Chatter chatter) {
        this.chatter = chatter;
        requetes = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        requetes.put("/quit", new Quit(chatter.getClient()));
        requetes.put("/hello", new Hello(chatter.getClient()));
        requetes.put("/msg", new Msg(chatter.getClient()));
    }

    public boolean traiter(String requete) {
        for (Map.Entry<String, CommandArgument> entry : requetes.entrySet()) {
            String pattern = entry.getKey();
            if (Pattern.compile(pattern).matcher(requete).matches()) {
                CommandArgument command = entry.getValue();
                command.setArgs((Object[]) requete.split(" "));
                command.execute();
                return true;
            }
        }
        return false;
    }

}
