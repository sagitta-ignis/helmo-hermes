/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.controleur.handler;

import hermes.command.message.Message;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MessageQueueHandler {

    private final Queue<Message> file;

    public MessageQueueHandler() {
        file = new ConcurrentLinkedQueue<>();
    }
    
    public boolean isWaiting() {
        return !file.isEmpty();
    }
    
    public void ajouter(Message message) {
        file.offer(message);
    }

    public boolean traiter(String message) {
        Message m = file.poll();
        if(m != null) {
            m.response(message);
            return true;
        }
        return false;
    }
    
}
