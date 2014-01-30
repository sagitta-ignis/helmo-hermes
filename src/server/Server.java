package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import server.com.ClientManager;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Server {

    public static final int PORT = 12345;
    ServerSocket server;
    Scanner input;
    Writer output;
    List<ClientManager> clients;

    public Server(Scanner input, Writer output) {
        try {
            server = new ServerSocket(PORT);
            clients = new ArrayList<>();
            this.input = input;
            this.output = output;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            afficher("[error] création du socket serveur a échouée");
        }
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = server.accept();
                ClientManager client = new ClientManager(this, clientSocket);
                clients.add(client);
                Thread t= new Thread(client);
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            afficher("[error] connexion au client a échouée");
        }
    }

    public void transmettre(String message) {
        afficher(message);
        for (ClientManager client : clients) {
            client.envoyer(message);
        }
    }

    public void afficher(String message) {
        try {
            output.write(message+"\n");
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[warning] flux de sortie du serveur n'a pu être utilisé");
            System.out.println(message);
        }
    }
    
    public boolean retirer(ClientManager client) {
        return clients.remove(client);
    }

    private void fermer() throws IOException {
        for (ClientManager client : clients) {
            client.close();
        }
        server.close();
    }

    public static void main(String[] args) {
        try {
            Server s = new Server(new Scanner(System.in), new PrintWriter(System.out));
            System.out.println("-- serveur a démarré");
            s.run();
            s.fermer();
            System.out.println("-- serveur fermé");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
}
