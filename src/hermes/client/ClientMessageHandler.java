/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client;

import pattern.command.CommandArgument;
import hermes.client.command.message.*;
import hermes.protocole.ProtocoleSwinen;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientMessageHandler {

    private final Client client;
    private final Map<String, CommandArgument> requetes;

    public ClientMessageHandler(Client client) {
        this.client = client;
        requetes = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        String user = ProtocoleSwinen.user.getPattern();
        String pass = ProtocoleSwinen.pass.getPattern();
        requetes.put("/hello "+user+" "+pass, new Hello(client));
        requetes.put("/quit", new Quit(client));
        requetes.put("/users", new Users(client));
        requetes.put("/typing", new Typing(client));
    }

    public boolean traiter(String requete) {
        Matcher matcher = Pattern.compile("").matcher(requete);
        for (Map.Entry<String, CommandArgument> entry : requetes.entrySet()) {
            String pattern = entry.getKey();
            matcher.usePattern(Pattern.compile(pattern));
            if (matcher.matches()) {
                CommandArgument command = entry.getValue();
                command.setArgs(getArguments(requete));
                command.execute();
                return true;
            }
        }
        return false;
    }
    
    private Object[] getArguments(String requete) {
        Object arguments[] = (Object[]) requete.split(" ");
        if(arguments.length == 0) {
            arguments = new Object[0];
        } else {
            arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        }
        return arguments;
    }

}
