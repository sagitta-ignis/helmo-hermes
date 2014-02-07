/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Emetteur {

    Client client;
    PrintWriter outToServer;

    public Emetteur(Client clt) {
        client = clt;
    }    

    public void lier(Socket socket) throws IOException {
        outToServer = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(),
                        Charset.forName("UTF-8")),
                true
        );
    }

    public void envoyer(String message) {
        outToServer.println(message);
        client.setLogged(!message.equals("/quit"));
    }

    public void fermer() {
        if (outToServer != null) {
            outToServer.close();
            outToServer = null;
        }
    }
}
