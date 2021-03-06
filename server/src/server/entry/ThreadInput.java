/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.entry;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controlleurs.ServeurControlleur;

/**
 *
 * @author David
 */
public class ThreadInput extends Thread {

    private final Scanner input;
    private final ServeurControlleur server;

    public ThreadInput(ServeurControlleur server) {
        this.server = server;
        this.input = new Scanner(System.in);
    }

    @Override
    public synchronized void run() {
        while (true) {
            if (input.nextLine().equals("quit")) {
                server.fermer();
                break;
            }
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadInput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
