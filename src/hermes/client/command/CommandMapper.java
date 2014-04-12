/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.command;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class CommandMapper {

    private final Map<String, CommandArgument> commandes;

    public CommandMapper() {
        this.commandes = new HashMap<>();
    }
    
    public void ajouter(String key, CommandArgument command) {
        commandes.put(key, command);
    }
    
    public void execute(String key, Object[] args) {
        CommandArgument command = commandes.get(key);
        if(command != null) {
            command.setArgs(args);
            command.execute();
        }
    }
}
