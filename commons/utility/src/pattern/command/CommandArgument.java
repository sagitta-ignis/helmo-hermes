/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.command;

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
        return args.length >= min && args.length <= max;
    }
    
    protected boolean verifierType(Class c, int i) {
        return c.isInstance(args[i]);
    }
    
    protected boolean verifierTousTypes(Class c) {
        for (int i = 0; i < args.length; i++) {
            if(!verifierType(c, i)) {
                return false;
            }
        }
        return true;
    }
}
