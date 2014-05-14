/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.swing;

import java.awt.event.ActionListener;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class JCloseableTabComponent extends JPanel {

    private final JTabbedPane pane;
    private final JLabel label;
    private final JCloseButton button;

    public JCloseableTabComponent(JTabbedPane pane, String title) {
        this.pane = pane;
        this.label = new JLabel(title);
        this.button = new JCloseButton();
        setName(title);
        setMaximumSize(new java.awt.Dimension(32767, 30));
        setMinimumSize(new java.awt.Dimension(80, 20));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 2, 2));
        add(label);
        add(button);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        label.setText(name);
    }

    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }
    
    public void remmoveActionListener(ActionListener listener) {
        button.removeActionListener(listener);
    }

}
