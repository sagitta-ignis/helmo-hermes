/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.com;

import java.util.Date;

/**
 *
 * @author David M
 */
public class Client {
    
    private int id;
    private String username;
    private boolean opened;
    private int etat;
    private final Date time;
    
    public Client(int idClient){
        id = idClient;
        username = "";
        opened = true;
        etat = 0;
        time = new Date();
    }

    public int getTimeConnected(){
        Date now = new Date();
        long diffEnMilli  = now.getTime() - time.getTime();
        long diffEnMinutes = diffEnMilli/ (60 * 1000) % 60;
        return (int)diffEnMinutes;
        
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the opened
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * @param opened the opened to set
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }
    
    @Override
    public String toString() {
        return String.format("%s(%d)", getUsername(),id);
    }

    /**
     * @return the etat
     */
    public int getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(int etat) {
        this.etat = etat;
    }

}
