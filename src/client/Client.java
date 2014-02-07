package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import pattern.Command;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Client {

    public static final int PORT = 12345;
    private Socket socket;
    private Sender dialogue;
    private Prompter affichage;
    
    private Map<String, Command> commands;

    private String nom;
    private int id;
    private boolean opened = true;

    public Client(String host, Scanner in, PrintStream out) {
        try {
            socket = new Socket(host, PORT);
            dialogue = new Sender(in);
            affichage = new Prompter(out);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            out.println("[error] création du socket client a échoué");
        }
    }
    
    public Client(String nom, String host, Scanner in, PrintStream out) {
        this(host, in, out);
        authentifier(nom);
    }

    public final void authentifier(String nom) {
        this.nom = nom;
        dialogue.send("/connect "+nom);
    }
    
    public synchronized void open() {
        dialogue.start();
        affichage.start();
        while(dialogue.isAlive() || affichage.isAlive()) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                
            }
        }
    }

    public void close() {
        
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            affichage.print("[error] création du socket client a échoué");
        }
        dialogue.close();
        affichage.close();
    }
    
    private class Sender extends Thread {

        Scanner input;
        PrintWriter outToServer;

        public Sender(Scanner console) throws IOException {
            outToServer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(),
                            Charset.forName("UTF-8")),
                    true
            );
            input = console;
        }

        @Override
        public void run() {
            while (opened) {
                String message = input.nextLine();
                send(message);
                opened = !message.equals("/quit");
            }
        }

        public void send(String message) {
            outToServer.println(message);
        }

        public void close() {
            if (outToServer != null) {
                outToServer.close();
                outToServer = null;
            }
        }
    }
    private class Prompter extends Thread {

        BufferedReader inFromServer;
        PrintStream output;

        public Prompter(PrintStream stream) throws IOException {
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    Charset.forName("UTF-8")));
            output = stream;
        }

        @Override
        public void run() {
            while (opened) {
                opened = listen();
            }
        }

        public void print(String message) {
            output.println(message);
        }

        private boolean listen() {
            String message;
            try {
                message = inFromServer.readLine();
            }catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                message = "[error] reception depuis serveur a échoué";
            }
            if(message != null) {
                print(message);
                return !message.startsWith("[error]");
            }
            return false;
        }

        public void close() {
            try {
                if (inFromServer != null) {
                    inFromServer.close();
                    inFromServer = null;
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                print("[error] socket avec serveur mal fermé");
                inFromServer = null;
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); 
        Client c = new Client("127.0.0.1", input, System.out);
        System.out.println("-- client a démarré");
        System.out.println("Entrer un nom : ");
        String nom = input.nextLine();
        c.authentifier(nom);
        c.open();
        c.close();
        System.exit(0);
    }
}
