/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client;

import hermes.client.exception.UnreachableServerExeception;
import hermes.ssl.SSL;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientConnectionHandler {
    
    private Socket socket;
    private boolean connected = false;
    private boolean logged = false;

    public boolean connect(String host, int port) throws UnreachableServerExeception {
        try {
            SocketFactory sslsocketfactory = SSL.getSocketFactory();
            socket = sslsocketfactory.createSocket(host, port);
            ((SSLSocket)socket).startHandshake();
            connected = true;
        } catch (IOException ex) {
            throw new UnreachableServerExeception();
        }
        return connected;
    }

    public Socket getSocket() {
        return socket;
    }
    
    public void shutdown() throws IOException {
        socket.shutdownInput();
        socket.shutdownOutput();
    }

    public boolean disconnect() throws IOException {
        logged = false;
        connected = false;
        socket.close();
        return canRun();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean canRun() {
        return connected && logged;
    }
}
