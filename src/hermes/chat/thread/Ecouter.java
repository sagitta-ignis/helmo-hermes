/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.thread;

import hermes.chat.controleur.Chatter;
import hermes.chat.controleur.handler.MessageQueueHandler;
import hermes.chat.controleur.handler.ServerRequestHandler;
import hermes.client.Client;
import hermes.command.message.Message;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Ecouter  extends Thread {
    private final Chatter chat;
    private final MessageQueueHandler messageQueue;
    private final ServerRequestHandler serverRequest;
    
    public Ecouter(Chatter c) {
        chat = c;
        messageQueue = new MessageQueueHandler();
        serverRequest = new ServerRequestHandler(chat);
        setName("Ecouteur");
    }
    
    public void redirigerVers(Message m) {
        messageQueue.ajouter(m);
    }
    
    @Override
    public void run() {
        Client client = chat.getClient();
        while (client.getConnectionHandler().canRun()) {
            if(!recevoir()) {
                break;
            }
        }
    }

    public boolean recevoir() {
        Client client = chat.getClient();
        String message = client.getEcouteur().lire();
        if (message != null && message.startsWith("[error]")) {
            System.err.println(message);
            return false;
        }
        System.out.println(message);
        if (!messageQueue.traiter(message)) {
            if (!serverRequest.parser(message + "\r\n")) {
                client.setEtat(Client.UnknownRequestReceived, message);
            }
        }
        return true;
    }
}
