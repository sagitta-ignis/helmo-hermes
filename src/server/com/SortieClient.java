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

    private final PrintWriter outToClient;
    private Queue<String> fileMessages ;

    public SortieClient(Socket sck) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(sck.getOutputStream(), Charset.forName("UTF-8"));
        outToClient = new PrintWriter(osw, true);
        fileMessages = new LinkedList<String>();
    }

    public void ajouter(String message) {
        fileMessages.add(message);
    }

    public void close() {
        outToClient.close();
    }

    private void envoyer() {
        outToClient.println(fileMessages.poll());
        outToClient.flush();
    }

    @Override
    public void run() {
        while (true) {
            if (fileMessages.size() != 0) {
                envoyer();
            } else {
                try {
                    sleep(Configuration.threadSleepMillisec);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SortieClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
