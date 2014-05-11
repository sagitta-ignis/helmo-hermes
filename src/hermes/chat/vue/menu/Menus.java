/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.menu;

import hermes.chat.controleur.Chatter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author d120041
 */
public class Menus {
    private final Map<Class, Menu> menus;

    public Menus(Chatter chat) {
        menus = new HashMap<>();
    }
    
    public void ajouter(Class model, Menu menu) {
        menus.put(model, menu);
    }
    
    public Menu get(Class type) {
        return menus.get(type);
    }
}
