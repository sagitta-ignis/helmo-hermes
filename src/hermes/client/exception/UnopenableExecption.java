/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.exception;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class UnopenableExecption extends Exception {

    public UnopenableExecption() {
        super("client not connected or logged");
    }
    
}
