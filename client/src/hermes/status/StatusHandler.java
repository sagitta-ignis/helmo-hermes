/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.status;

import hermes.command.CommandMapper;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class StatusHandler implements Observer {
    private final CommandMapper statusReader;
    private CommandArgument command;

    public StatusHandler() {
        statusReader = new CommandMapper();
    }

    public void ajouterStatus(Object key, CommandArgument command) {
        statusReader.ajouter(key, command);
    }
    
    protected CommandArgument getCommand() {
        return command;
    }
    
    @Override
    public synchronized void update(Observable o, Object args) {
        Object arguments[] = (Object[]) args;
        String status = (String) arguments[0];
        arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        statusReader.execute(status, arguments);
    }
}
