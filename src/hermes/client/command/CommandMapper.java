/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.command;

import pattern.command.CommandArgument;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class CommandMapper<T> {

    private final Map<T, CommandArgument> commandes;

    public CommandMapper() {
        this.commandes = new HashMap<>();
    }
    
    public void ajouter(T key, CommandArgument command) {
        commandes.put(key, command);
    }
    
    public void execute(T key, Object[] args) {
        CommandArgument command = commandes.get(key);
        if(command != null) {
            command.setArgs(args);
            command.execute();
        }
    }
}
