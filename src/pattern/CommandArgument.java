/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pattern;

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
}
