package server.controlleurs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import server.entry.ThreadInput;
import hermes.xml.Xml;
import hermes.xml.XmlImpl;
import server.client.ClientManager;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.response.SentResponse;
import server.response.SentShutDown;
import server.configuration.Configuration;
import server.configuration.ListUser;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServeurControlleur {

    private ServerSocket server;

    private final Writer output;
    private final SentResponse responseNS;
    private final ChannelControlleur channelManager;
    private ListUser users;
    private Configuration config;

    public ServeurControlleur() {
        channelManager = new ChannelControlleur(this);
        users = new ListUser();
        responseNS = new SentResponse();
        this.output = new PrintWriter(System.out);

        lectureFichiers();
        connection();

        new ThreadInput(this).start();
    }

    public void lancerServeur() {
        while (true) {
            try {
                Socket clientSocket = server.accept();
                ClientManager client = new ClientManager(channelManager, clientSocket, users, config);
                channelManager.nouveauClient(client);
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
            server = new ServerSocket(config.getPort());
        } catch (IOException ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
            afficher(responseNS.getError(104));
        }
    }

    private void lectureFichiers() {
        Xml xml = new XmlImpl();
        try {
            config = (Configuration) xml.read(new FileInputStream("./config.xml"), Configuration.class);
            users = (ListUser) xml.read(new FileInputStream(config.getUserFileName()), ListUser.class);
        } catch (Exception ex) {
            Logger.getLogger(ServeurControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
