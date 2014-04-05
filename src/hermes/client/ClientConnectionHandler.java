/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.client;

import hermes.client.exception.UnreachableServerExeception;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ClientConnectionHandler {

    private Socket socket;
    private boolean connected;
    private boolean logged;
    private boolean opened;

    public boolean connect(String host, int port) throws UnreachableServerExeception {
        try {
            socket = new Socket(host, port);
            connected = true;
        } catch (IOException ex) {
            throw new UnreachableServerExeception();
        }
        return connected;
    }

    public Socket getSocket() {
        return socket;
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

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean canRun() {
        return connected && logged && opened;
    }
}
