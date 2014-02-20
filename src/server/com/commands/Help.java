/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com.commands;

import java.util.Iterator;
import java.util.Map;
import pattern.Command;
import server.com.ClientManager;

/**
 *
 * @Author David M
 */
public class Help implements Command {

    private final ClientManager clientManager;
    private final Map<String, Command> commands;
    private final Map<String, Command> commandsAdmin;

    public Help(ClientManager client, Map<String, Command> commands, Map<String, Command> commandsAdmin) {
        this.clientManager = client;
        this.commands = commands;
        this.commandsAdmin = commandsAdmin;
    }

    @Override
    public void execute() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("====Commmandes disponibles===");
        
        Iterator it = commands.entrySet().iterator();
        sb.append(parcourirElements(it));
        
        sb.append("\n====Commmandes admins===");
       
        it = commandsAdmin.entrySet().iterator();        
        sb.append(parcourirElements(it));
 
        
        clientManager.envoyer(sb.toString()); 
    }
   
    private String parcourirElements(Iterator it){
        StringBuilder sb = new StringBuilder();
        
         while (it.hasNext()) {            
            Map.Entry entry = (Map.Entry)it.next();
            String cle = (String)entry.getKey();
            Command cmd = (Command)entry.getValue();
            
            sb.append("\n").append(cle).append(" ");
            sb.append(cmd.desciption());
            
        }
         return sb.toString();
    }
    @Override
    public void execute(String args) {
        execute();
    }

    @Override
    public String desciption() {
        return "Affiche les commandes";
    }
}
