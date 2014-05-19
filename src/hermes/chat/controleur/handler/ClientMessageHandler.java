/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.controleur.handler;

import hermes.command.message.base.*;
import hermes.command.message.channel.*;
import hermes.chat.controleur.Chatter;
import hermes.command.message.channel.WhereIAm;
import java.util.*;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientMessageHandler {

    private final Chatter chat;
    private final Map<String, CommandArgument> requetes;
    private final Map<String, CommandArgument> requetesPubliques;

    public ClientMessageHandler(Chatter chat) {
        this.chat = chat;
        requetes = new HashMap<>();
        requetesPubliques = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        requetes.put("hello", new Hello(chat));
        requetesPubliques.put("quit", new Quit(chat));
        requetes.put("users", new Users(chat));
        requetes.put("channels", new Channels(chat));
        requetes.put("userschannel", new UsersChannel(chat));
        requetes.put("infochannel", new InfoChannel(chat));
        requetes.put("createchannel", new CreateChannel(chat));
        requetes.put("deletechannel", new DeleteChannel(chat));
        requetes.put("enter", new Enter(chat));
        requetes.put("exit", new Exit(chat));
        requetes.put("whereiam", new WhereIAm(chat));
        requetesPubliques.put("msg", new Msg(chat));
        requetesPubliques.put("discuss", new Discuss(chat));
        requetes.put("typing", new Typing(chat));
        requetes.put("register", new Register(chat));
    }

    public boolean traiter(String sequence) {
        Object[] arguments = getArguments(sequence);
        String requete = checkAndClean((String) arguments[0]);
        if(requete == null) return false;
        Object[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        CommandArgument command = find(requete);
        return execute(command, args);
    }
    
    public boolean execute(String sequence, Object... args) {
        String requete = checkAndClean(sequence);
        if(requete == null) return false;
        CommandArgument command = get(requete);
        return execute(command, args);
    }
    
    private String checkAndClean(String requete) {
        if (!requete.startsWith("/")) {return null;}
        return requete.replace("/", "");
    }
    
    private boolean execute(CommandArgument command, Object... args) {
        if(command == null) {return false;}
        command.setArgs(args);
        command.execute();
        return true;
    }

    private Object[] getArguments(String requete) {
        Object arguments[] = (Object[]) requete.split(" ");
        if (arguments.length == 0) {
            arguments = new Object[0];
        }
        return arguments;
    }
    
    public CommandArgument find(String requete) {
        return requetesPubliques.get(requete);
    }

    public CommandArgument get(String requete) {
        CommandArgument c = requetes.get(requete);
        if(c == null) {
            c = requetesPubliques.get(requete);
        }
        return c;
    }

}
