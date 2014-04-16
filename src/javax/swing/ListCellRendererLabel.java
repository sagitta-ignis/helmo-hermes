/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.swing;

import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ListCellRendererLabel extends JLabel implements ListCellRenderer<Object> {

    private final static int unselected = 0;
    private final static int selected = 1;
    private final static int current = 2;
    
    private int state = 0;
    
    public ListCellRendererLabel() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        setText(value.toString());

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {
            state = current;
            currentDnD();
            // check if this cell is selected
        } else if (isSelected) {
            state = selected;
            selected();
            // unselected, and not the DnD drop location
        } else {
            state = unselected;
            unselected();
        }

        return this;
    }
    
    public void stateBack() {
        switch(state) {
            case current:
                currentDnD();
                break;
            case selected:
                selected();
                break;
            case unselected:
                unselected();
                break;
        }
    } 

    void currentDnD() {
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
    }

    void selected() {
        setBackground(Color.RED);
        setForeground(Color.WHITE);
    }

    void unselected() {
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
    }
}
