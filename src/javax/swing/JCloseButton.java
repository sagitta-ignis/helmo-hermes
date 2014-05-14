/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.swing;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class JCloseButton extends JButton {

    public JCloseButton() {
        setBackground(new java.awt.Color(255, 255, 255));
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/javax/swing/images/icons/close_icons.png"))); // NOI18N
        setBorderPainted(false);
        setContentAreaFilled(false);
        setMinimumSize(new java.awt.Dimension(5, 5));
        setPreferredSize(new java.awt.Dimension(20, 20));
        setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/javax/swing/images/icons/close_icons_mouseover.png"))); // NOI18N
    }

}
