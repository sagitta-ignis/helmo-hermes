/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.Chat;
import hermes.chat.controleur.Chatter;
import hermes.chat.controleur.Overlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public final class ChatGUI extends javax.swing.JFrame implements Chat {

    private final Overlayer overlay;
    private final Chatter chat;
    private boolean typing;
    
    /**
     * Creates new form Chat
     *
     * @param chatter
     * @param overlayer
     * @param users
     */
    public ChatGUI(Chatter chatter, Overlayer overlayer, DefaultListModel users) {
        initComponents();
        utilisateurs.setModel(users);
        chat = chatter;
        overlay = overlayer;
        setChatListener();
        
        // colorise les cellules de la liste
        utilisateurs.setCellRenderer(new ListCellRendererUser());

        //centre la frame
        setLocationRelativeTo(getRootPane());

        //Scroll automatiquement en bas du textarea
        DefaultCaret caret = (DefaultCaret) conversation.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public String getUtilisateur() {
        return utilisateur.getText();
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur.setText(utilisateur);
    }

    public boolean isTyping() {
        return typing;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }

    private void setChatListener() {
        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyChar() == '\n') {
                    envoyer.doClick();
                } else {
                    String text = message.getText();
                    if(!isTyping() && !text.isEmpty()) {
                        chat.setTyping(true);
                    } else if (isTyping() && text.isEmpty()) {
                        chat.setTyping(false);
                    }
                }
            }
        });
        envoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chat.setTyping(false);
                chat.entrer(utilisateur.getText(), message.getText());
                message.setText("");
            }
        });
    }

    @Override
    public void afficher(String text) {
        overlay.afficher(text);
        conversation.append(text);
        conversation.append("\n");
    }

    @Override
    public void avertir(String titre, String message) {
        JOptionPane.showMessageDialog(this, message, titre,
                JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void setVisible(boolean bln) {
        super.setVisible(bln);
        if(!bln) {
            conversation.setText(null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dialogue = new javax.swing.JPanel();
        message = new javax.swing.JTextField();
        envoyer = new javax.swing.JButton();
        utilisateur = new javax.swing.JTextField();
        lbUtilisateur = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        conversation = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        utilisateurs = new javax.swing.JList();
        menu = new javax.swing.JMenuBar();
        jmIRC = new javax.swing.JMenu();
        jmiQuitter = new javax.swing.JMenuItem();
        jmOverlay = new javax.swing.JMenu();
        jmiOverlay1 = new javax.swing.JMenuItem();
        jmiOverlay3 = new javax.swing.JMenuItem();
        jmiOverlay5 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiOverlayDesactiver = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("IRC Helmo");
        setName("client"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                fermer(evt);
            }
        });

        dialogue.setPreferredSize(new java.awt.Dimension(477, 40));
        dialogue.setLayout(new java.awt.GridBagLayout());

        message.setPreferredSize(new java.awt.Dimension(400, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 0.1;
        dialogue.add(message, gridBagConstraints);

        envoyer.setText("Envoyer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weightx = 0.1;
        dialogue.add(envoyer, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        dialogue.add(utilisateur, gridBagConstraints);

        lbUtilisateur.setText("Utilisateur");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        dialogue.add(lbUtilisateur, gridBagConstraints);

        getContentPane().add(dialogue, java.awt.BorderLayout.PAGE_END);

        conversation.setEditable(false);
        conversation.setColumns(20);
        conversation.setRows(5);
        scrollPane.setViewportView(conversation);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(100, 27));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 130));

        jScrollPane1.setViewportView(utilisateurs);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.EAST);

        jmIRC.setText("IRC");

        jmiQuitter.setText("Quitter");
        jmiQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQuitterActionPerformed(evt);
            }
        });
        jmIRC.add(jmiQuitter);

        menu.add(jmIRC);

        jmOverlay.setText("Overlay");

        jmiOverlay1.setText("Overlay 1");
        jmiOverlay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Overlay1(evt);
            }
        });
        jmOverlay.add(jmiOverlay1);

        jmiOverlay3.setText("Overlay 3");
        jmiOverlay3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Overlay3(evt);
            }
        });
        jmOverlay.add(jmiOverlay3);

        jmiOverlay5.setText("Overlay 5");
        jmiOverlay5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Overlay5(evt);
            }
        });
        jmOverlay.add(jmiOverlay5);
        jmOverlay.add(jSeparator1);

        jmiOverlayDesactiver.setText("Desactiver");
        jmiOverlayDesactiver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OverlayDesactiver(evt);
            }
        });
        jmOverlay.add(jmiOverlayDesactiver);

        menu.add(jmOverlay);

        setJMenuBar(menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fermer(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_fermer
        chat.quitter();
    }//GEN-LAST:event_fermer

    private void Overlay1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Overlay1
        overlay.activer(1);
    }//GEN-LAST:event_Overlay1

    private void Overlay3(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Overlay3
        overlay.activer(3);
    }//GEN-LAST:event_Overlay3

    private void Overlay5(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Overlay5
        overlay.activer(5);
    }//GEN-LAST:event_Overlay5

    private void OverlayDesactiver(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OverlayDesactiver
        overlay.desactiver();
    }//GEN-LAST:event_OverlayDesactiver

    private void jmiQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQuitterActionPerformed
        chat.quitter();
    }//GEN-LAST:event_jmiQuitterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea conversation;
    private javax.swing.JPanel dialogue;
    private javax.swing.JButton envoyer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu jmIRC;
    private javax.swing.JMenu jmOverlay;
    private javax.swing.JMenuItem jmiOverlay1;
    private javax.swing.JMenuItem jmiOverlay3;
    private javax.swing.JMenuItem jmiOverlay5;
    private javax.swing.JMenuItem jmiOverlayDesactiver;
    private javax.swing.JMenuItem jmiQuitter;
    private javax.swing.JLabel lbUtilisateur;
    private javax.swing.JMenuBar menu;
    private javax.swing.JTextField message;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextField utilisateur;
    private javax.swing.JList utilisateurs;
    // End of variables declaration//GEN-END:variables

}
