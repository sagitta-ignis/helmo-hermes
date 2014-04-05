/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client;

import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Encodeur extends Thread {

    private final Client client;
    private final Scanner input;

    public Encodeur(Client client, InputStream in) {
        this.client = client;
        input = new Scanner(in);
    }
    
    @Override
    public void run() {
        while (client.canRun()) {
            String message = input.nextLine();
            client.envoyer("all", message);
        }
    }
}
