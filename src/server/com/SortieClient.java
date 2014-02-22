/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author David
 */
public class SortieClient {

    private final PrintWriter outToClient;

    public SortieClient(Socket sck) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(sck.getOutputStream(), Charset.forName("UTF-8"));
        outToClient = new PrintWriter(osw, true);
    }

    public void envoyer(String message) {
        outToClient.println(message);
        outToClient.flush();
    }
    
    public void close(){
        outToClient.close();
    }
}
