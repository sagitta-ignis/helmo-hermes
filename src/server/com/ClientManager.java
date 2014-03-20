/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import hermes.protocole.MessageProtocole;
import hermes.protocole.ProtocoleSwinen;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pattern.CommandArgument;
import pattern.command.Command;
import server.ServerControleur;
import server.com.commands.*;
import server.com.etat.Connecte;
import server.com.etat.Waiting;
import server.com.response.SentAll;
import server.com.response.SentResponse;
import server.configuration.ListUser;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientManager {

    private static int increment = 1;

    private final ServerControleur server;

    private final EcouteurClient ecouteur;
    private final SortieClient sortie;
    private final Client clientInfo;
    private final Socket socket;
    private final ListUser listeUtilisateurs;

    private final SentResponse response;
    private final SentAll sentAll;
    private final Connecte connecte;
    private final Waiting waiting;

    private Map<MessageProtocole, Command> commandsProtocole;

    public ClientManager(ServerControleur srv, Socket sck, ListUser listeUtilisateurs) throws IOException {
        this.listeUtilisateurs = listeUtilisateurs;
        clientInfo = new Client(increment++);
        
        server = srv;
        socket = sck;
        
        response = new SentResponse(this);
        sentAll = new SentAll(server);
        
        connecte = new Connecte(this, response);

        ecouteur = new EcouteurClient(clientInfo, sck, this, server);
        sortie = new SortieClient(sck);
        initCommands();
        waiting = new Waiting(this, response);

        ecouteur.start();
        sortie.start();
    }

    private void initCommands() {
        commandsProtocole = new HashMap<>();

        commandsProtocole.put(ProtocoleSwinen.ALL, new All(sentAll, clientInfo));
        commandsProtocole.put(ProtocoleSwinen.HELLO, new Hello(this, clientInfo, listeUtilisateurs, response, sentAll));
        commandsProtocole.put(ProtocoleSwinen.MSG, new Msg(server, response));
        commandsProtocole.put(ProtocoleSwinen.QUIT, new Quit(this, sentAll));
    }

    public Client getClient() {
        return clientInfo;
    }

    public void traiter(String message) {
        message = nettoyer(message, false);
        if (clientInfo.isAccepte()) {
            connecte.traiter(message);
        } else {
            waiting.traiter(message);
        }
    }

    public void executer(MessageProtocole message) {
        Command command = commandsProtocole.get(message);
        ((CommandArgument)command).setArgs(message);
        command.execute();
    }

    public void envoyer(String message) {
        message = nettoyer(message, true);
        sortie.ajouter(message);

    }

    public void envoitImmediat(String message) {
        message = nettoyer(message, true);
        sortie.envoyer(message);
    }

    public void close() {
        try {
            server.retirer(this);
            clientInfo.setOpened(false);
            ecouteur.close();
            sortie.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            server.afficher("[error] socket avec " + toString() + " mal fermé");
        }
    }

    private String nettoyer(String text, boolean envoi) {
        String n = "\n";
        String r = "\r";
        String nPropre = "-n";
        String rPropre = "-r";
        if (!envoi) {
            n = "-n";
            r = "-r";
            nPropre = "\n";
            rPropre = "\r";
        }
        text = remplacer(n, text, nPropre);
        return remplacer(r, text, rPropre);

    }

    private String remplacer(String definition, String sequence, String synthax) {
        Matcher chercher = Pattern.compile(definition).matcher(sequence);
        return chercher.replaceAll(synthax);
    }

}
