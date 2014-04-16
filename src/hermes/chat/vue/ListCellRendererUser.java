/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.vue;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRendererLabel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ListCellRendererUser extends ListCellRendererLabel {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof CellUser) {
            CellUser user = (CellUser) value;
            if(user.isOn()) {
                on();
            } else {
                off();
            }
        }
        return this;
    }
    
    private void on() {
        setForeground(Color.BLUE);
    }
    
    private void off() {
        stateBack();
    }
}
