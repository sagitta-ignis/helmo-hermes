/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.com.commands;

import server.com.ClientManager;
import pattern.Command;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Set implements Command {

    private final ClientManager client;
    
    public Set(ClientManager client) {
        this.client = client;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void execute(String args) {
        
    }
    
    
    @Override
    public String desciption() {
       return "";
    }
}
