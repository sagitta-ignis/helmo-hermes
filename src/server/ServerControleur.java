package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import server.com.ClientManager;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Xml.ReadConfiguration;
import server.Xml.ReadUsers;
import server.com.Client;
import server.com.Configuration;
import server.com.User;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Server {

    ServerSocket server;
    Scanner input;
    Writer output;
    List<ClientManager> clients;
    List<User> users;
    Configuration config;

    public Server() {
        server = null;
        users = new ArrayList();
        clients = new ArrayList<>();
        this.input = new Scanner(System.in);
        this.output = new PrintWriter(System.out);
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = server.accept();
                ClientManager client = new ClientManager(this, clientSocket);
                clients.add(client);
                Thread t = new Thread(client);
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            afficher("[error] connexion au client a échouée");
        }
    }

    public void transmettre(String message) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("[k:mm:s]");

        StringBuilder sb = new StringBuilder();
        sb.append(dateFormat.format(date));
        sb.append(message);

        afficher(sb.toString());
        for (ClientManager client : clients) {
            client.envoyer(sb.toString());
        }
    }

    public void afficher(String message) {
        try {
            output.write(message + "\n");
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
            Server s = new Server();
            s.lectureFichiers();
            s.connection();

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

    public Client trouverClient(int id) {
        for (ClientManager clientM : clients) {
            if (clientM.getClient().getId() == id) {
                return clientM.getClient();
            }
        }
        return null;
    }

    public List<ClientManager> getConnected() {
        return clients;
    }

    private void connection() {
        try {
            server = new ServerSocket(config.getPort());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            afficher("[error] création du socket serveur a échouée");
        }
    }

    private void lectureFichiers() {
        try {
            config = ReadConfiguration.UnmarshalConfig(new FileInputStream("./config.xml"));
            users = ReadUsers.UnmarshalConfig(new FileInputStream(config.getUserFileName())).getUsers();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void receptionConnection() {

    }
}
