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
import server.Xml.ReadConfiguration;
import server.Xml.ReadUsers;
import server.configuration.Configuration;
import server.configuration.ListUser;
import server.configuration.User;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServerControleur {

    ServerSocket server;
    Scanner input;
    Writer output;
    List<ClientManager> clients;
    ListUser users;
    Configuration config;

    public ServerControleur() {
        server = null;
        clients = new ArrayList<>();
        users = new ListUser();
        this.input = new Scanner(System.in);
        this.output = new PrintWriter(System.out);
        
        
        lectureFichiers();
        connection();
    }

    public void lancerServeur() {
        try {
            while (true) {
                Socket clientSocket = server.accept();
                ClientManager client = new ClientManager(this, clientSocket,users);
                clients.add(client);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            afficher("[error] connexion au client a échouée");
        }
    }

    public void transmettre(String message) {
        for (ClientManager client : clients) {
            client.envoyer(message);
        }
    }

    public void afficher(String message) {
        try {
            output.write(message + "\n");
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[warning] flux de sortie du serveur n'a pu être utilisé");
            System.out.println(message);
        }
    }

    public boolean retirer(ClientManager client) {
        return clients.remove(client);
    }

    public void fermer() throws IOException {
        for (ClientManager client : clients) {
            client.close();
        }
        server.close();
    }


    public List<ClientManager> getConnected() {
        return clients;
    }

    private void connection() {
        try {
            server = new ServerSocket(config.getPort());
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            afficher("[error] création du socket serveur a échouée");
        }
    }

    private void lectureFichiers() {
        try {
            config = ReadConfiguration.UnmarshalConfig(new FileInputStream("./config.xml"));
            users.setUsers((List<User>)ReadUsers.UnmarshalConfig(new FileInputStream(config.getUserFileName())).getUsers());
        } catch (Exception ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
