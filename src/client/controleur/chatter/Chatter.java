/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controleur.chatter;

import client.Client;
import client.ClientListener;
import client.exception.NotConnectedException;
import client.exception.UnopenableExecption;
import client.exception.UnreachableServerExeception;
import client.vue.Chat;
import client.vue.Overlay;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Chatter implements ClientListener {

    private final Client client;
    private final Chat fenetre;
    private final Overlay overlay;
    
    public Chatter() {
        this.fenetre = new Chat(this);
        this.overlay = new Overlay(this);
        this.client = new Client(this, System.in, System.out);
    }

    public boolean connect(String ip, int port) {
        try {
            client.connect(ip, port);
            return true;
        } catch (UnreachableServerExeception ex) {
            Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            client.login(username, password);
            return true;
        } catch (NotConnectedException ex) {
            Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void open() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.open();
                } catch (UnopenableExecption ex) {
                    Logger.getLogger(Chatter.class.getName()).log(Level.SEVERE, null, ex);
                    fenetre.avertir("Erreur", "le client n'a pas pu être ouvert");
                }
            }
        });
        t.start();
        if(client.isLogged()) {
            fenetre.setVisible(true);
        }
    }

    public void send(String text) {
        client.send(text);
    }

    @Override
    public void lire(String text) {
        fenetre.entrer(text);
        overlay.entrer(text);
    }

    public void close() {        
        fenetre.dispose();
        client.close();
        System.exit(0);
    }
    
    public void afficherOverlay(int dimension){
        overlay.initialiser(dimension);
    }
    public void desactiverOverlay(){
        overlay.setVisible(false);
    }
}
