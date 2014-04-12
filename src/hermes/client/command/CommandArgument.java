/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client.command;

import pattern.command.Command;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public abstract class CommandArgument implements Command {

    protected Object[] args;

    public void setArgs(Object... args) {
        this.args = args;
    }
    
    protected boolean verifierArguments(int nombre) {
        return verifierArguments(nombre, nombre);
    }

    protected boolean verifierArguments(int min, int max) {
        if (args == null && min == 0) {
            return true;
        }
        if (args.length < min || args.length > max) {
            return false;
        }
        for (Object arg : args) {
            if (!(arg instanceof String)) {
                return false;
            }
        }
        return true;
    }
}
