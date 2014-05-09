/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.client.utilisateurs;

import java.util.Observable;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Utilisateur extends Observable {
    public final static String Typing = "typing";
    
    private final String name;
    private boolean typing;

    protected Utilisateur(String name) {
        this.name = name;
        typing = false;
    }

    public String getName() {
        return name;
    }

    public boolean isTyping() {
        return typing;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
        setChanged();
        notifyObservers(Typing);
    }
    
    protected String toLabel() {
        return name;
    }

    @Override
    public String toString() {
        return toLabel();
    }
}
