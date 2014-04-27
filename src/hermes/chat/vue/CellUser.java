/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class CellUser {
    private boolean on;
    private final String username;

    public CellUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public void toggle() {
        toggle(!on);
    }

    public void toggle(boolean on) {
        this.on = on;
    }

    public boolean isOn() {
        return on;
    }

    @Override
    public String toString() {
        return username; //To change body of generated methods, choose Tools | Templates.
    }
}
