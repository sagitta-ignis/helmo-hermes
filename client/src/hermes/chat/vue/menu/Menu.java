/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.menu;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JMenuItem;

/**
 *
 * @author d120041
 */
public abstract class Menu<T> {
    private final Map<String, JMenuItem> items;

    public Menu() {
        items = new HashMap<>();
    }
    
    public void ajouterItem(String label, ActionListener... listeners) {
        JMenuItem item = new JMenuItem(label);
        for (ActionListener listener : listeners) {
            item.addActionListener(listener);
        }
        items.put(label, item);
    }
    
    public JMenuItem get(String label) {
        return items.get(label);
    }
    
    public abstract List<JMenuItem> filtrerItemsPour(T Model);

    public List<JMenuItem> getItems() {
        return new ArrayList<>(items.values());
    }
}
