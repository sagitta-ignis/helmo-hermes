package server.controlleurs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import hermes.security.ssl.SSL;
import hermes.xml.Xml;
import hermes.xml.XmlImpl;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.xml.bind.JAXBException;
import server.client.ClientManager;
import server.configuration.Configuration;
import server.configuration.ListUser;
import server.configuration.User;
import server.entry.ThreadInput;
import server.response.SentResponse;
import server.response.SentShutDown;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServeurControlleur {

    private ServerSocket server;

    private final Writer output;
    private final SentResponse responseNS;
    private final ChannelControlleur channelManager;
    private final  Xml xml;
    private ListUser users;
    private Configuration config;

    public ServeurControlleur() {
        xml = new XmlImpl();
        output = new PrintWriter(System.out);
        users = new ListUser();
        lectureFichiers();
        responseNS = new SentResponse();
        
        channelManager = new ChannelControlleur(this);
        connection();
    }

    public Configuration getConfig() {
        return config;
    }

    public void lancerServeur() {
        new ThreadInput(this).start();
        while (true) {
            try {
                Socket clientSocket = server.accept();
                ClientManager client = new ClientManager(channelManager, clientSocket, users, config);
                
            } catch (SocketException ex) {
                break;
            } catch (IOException ex) {
                Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
                afficher(responseNS.getError(105));
            }
        }
    }

    public void afficher(String message) {
        if (message == null) {
            return;
        }
        try {
            output.write(message);
            output.flush();

        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[warning] flux de sortie du serveur n'a pu être utilisé");
            System.out.println(message);
        }
    }

    public boolean enregitsrerUtilisateur(String pseudo, String password){
        if(users.getUsers().get(pseudo)  == null){
           users.getUsers().put(pseudo,new User(password));
           ecrireListeUsers();
           return true;
        }
        return false;        
    }
    
    public synchronized void fermer() {

        try {
            System.err.println(">> Fermeture du serveur");

            new SentShutDown(channelManager).sent();

            channelManager.closeLogger();
            int secondesAvantFermeture = 5;

            while (secondesAvantFermeture != 0) {
                System.err.println(">> Fermeture du serveur dans " + secondesAvantFermeture-- + " secs");
                wait(1000);
            }

            channelManager.close();
            server.close();

            System.err.println(">> Le serveur est éteint");
            System.exit(0);

        } catch (InterruptedException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void connection() {
        try {
            ServerSocketFactory ssf = SSL.getServerSocketFactory();
            int port = config.getPort();
            server = ssf.createServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
            afficher(responseNS.getError(104));
        }
    }

    private void lectureFichiers() {        
        try {
            config = (Configuration) xml.read(new FileInputStream("./config.xml"), Configuration.class);
            users = (ListUser) xml.read(new FileInputStream(config.getUserFileName()), ListUser.class);
        } catch (Exception ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ecrireListeUsers(){
        try {
            xml.write(users, ListUser.class,new File(config.getUserFileName()));
        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
