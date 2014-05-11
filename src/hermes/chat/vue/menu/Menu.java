/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue.menu;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;

/**
 *
 * @author d120041
 */
public abstract class Menu<T> {
    private final List<JMenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }
    
    public void ajouterItem(String label, ActionListener... listeners) {
        JMenuItem item = new JMenuItem(label);
        for (ActionListener listener : listeners) {
            item.addActionListener(listener);
        }
        items.add(item);
    }
    
    public abstract List<JMenuItem> filtrerItemsPour(T Model);

    public List<JMenuItem> getItems() {
        return items;
    }
}
