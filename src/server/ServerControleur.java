package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import hermes.hermeslogger.HermesLogger;
import hermes.hermeslogger.LoggerImplements;
import hermes.xml.Xml;
import hermes.xml.XmlImpl;
import server.com.ClientManager;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import server.com.response.SentResponse;
import server.configuration.Configuration;
import server.configuration.ListUser;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ServerControleur {

    private ServerSocket server;

    private final Writer output;
    private final List<ClientManager> clients;
    private HermesLogger log;
    private ListUser users;
    private Configuration config;
    private final SentResponse responseNS;

    public ServerControleur() {
        server = null;
        clients = new ArrayList<>();
        users = new ListUser();
        responseNS = new SentResponse();
        this.output = new PrintWriter(System.out);
        log = new LoggerImplements("General");

        lectureFichiers();
        connection();

        new ThreadInput(this).start();
    }

    public void lancerServeur() {
        while (true) {
            try {
                Socket clientSocket = server.accept();
                ClientManager client = new ClientManager(this, clientSocket, users);
                clients.add(client);
            } catch (SocketException ex) {
                break;
            } catch (IOException ex) {
                Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
                afficher(responseNS.getError(105));
            }
        }
    }

    public void transmettre(String message) {
        for (ClientManager client : clients) {
            if (client.getClient().getEtat() != 0) {
                client.envoyer(message);
            }
        }
    }

    public void afficher(String message) {
        if (message == null) {
            return;
        }
        if(log == null){
            System.err.println("[LOGGER] Référence null - ServerControleur");
            return;
        }
        try {
            log.ajouterMessage(message);

            output.write(message);
            output.flush();

        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[warning] flux de sortie du serveur n'a pu être utilisé");
            System.out.println(message);
        } catch (JAXBException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean retirer(ClientManager client) {
        return clients.remove(client);
    }

    public void fermer() {
        
        try {
            log.close();
            log = null;
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).close();
            }
            clients.clear();
            server.close();
            System.exit(0);
        } catch (JAXBException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ClientManager> getConnected() {
        return clients;
    }

    private void connection() {
        try {
            server = new ServerSocket(config.getPort());
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            afficher(responseNS.getError(104));
        }
    }

    private void lectureFichiers() {
        Xml xml = new XmlImpl();
        try {
            config = (Configuration) xml.read(new FileInputStream("./config.xml"), Configuration.class);
            users = (ListUser) xml.read(new FileInputStream(config.getUserFileName()), ListUser.class);
        } catch (Exception ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
