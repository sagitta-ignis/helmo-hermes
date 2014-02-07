/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Encodeur extends Thread {

    private final Client client;
    private final Emetteur emetteur;
    private final Scanner input;

    public Encodeur(Client client, Emetteur emetteur, InputStream in) {
        this.client = client;
        this.emetteur = emetteur;
        this.input = new Scanner(in);
    }
    
    @Override
    public void run() {
        while (client.isLogged()) {
            String message = input.nextLine();
            emetteur.envoyer(message);
        }
    }
}
