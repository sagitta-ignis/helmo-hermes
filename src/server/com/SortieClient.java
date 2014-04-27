/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.configuration.Configuration;

/**
 *
 * @author David
 */
public class SortieClient extends Thread {

    private final int sleepMilliSec;
    private final PrintWriter outToClient;
    private Queue<String> fileMessages ;

    public SortieClient(Socket sck, int sleep) throws IOException {
        sleepMilliSec = sleep;
        OutputStreamWriter osw = new OutputStreamWriter(sck.getOutputStream(), Charset.forName("UTF-8"));
        outToClient = new PrintWriter(osw, true);
        fileMessages = new LinkedList<String>();
    }

    public void ajouter(String message) {
        fileMessages.add(message);
    }

    public void close() {
        this.interrupt();
        outToClient.close();
    }

    public synchronized void envoyer(String message) {
        outToClient.println(message);
        outToClient.flush();
    }

    @Override
    public synchronized void run() {
        while (true) {
            if (fileMessages.size() != 0) {
                envoyer(fileMessages.poll());
            } else {
                try {
                    wait(sleepMilliSec);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
    }

}
