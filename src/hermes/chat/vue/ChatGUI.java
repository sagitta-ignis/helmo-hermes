/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.Chat;
import static hermes.chat.Chat.CURRENT;
import hermes.chat.controleur.Chatter;
import hermes.chat.controleur.Overlayer;
import hermes.chat.vue.listeners.ClicDroitTree;
import hermes.chat.vue.listeners.Ecrire;
import hermes.chat.vue.listeners.Envoyer;
import hermes.chat.vue.listeners.Fermer;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCloseableTabComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JTextField;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public final class ChatGUI extends javax.swing.JFrame implements Chat {

    private Overlayer overlay;
    private final Chatter chat;

    private final JMenu jmOverlay = new javax.swing.JMenu();
    private final JMenuItem jmiOverlay1 = new javax.swing.JMenuItem();
    private final JMenuItem jmiOverlay3 = new javax.swing.JMenuItem();
    private final JMenuItem jmiOverlay5 = new javax.swing.JMenuItem();
    private final Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();
    private final JMenuItem jmiOverlayDesactiver = new javax.swing.JMenuItem();

    private final Map<String, Conversation> conversations;
    private boolean typing;

    /**
     * Creates new form Chat
     *
     * @param chatter
     */
    public ChatGUI(Chatter chatter) {
        initComponents();

        chat = chatter;
        conversations = new HashMap<>();

        KeyAdapter ecris = new Ecrire(this, chat);
        message.addKeyListener(ecris);
        ActionListener envoi = new Envoyer(this, chat);
        envoyer.addActionListener(envoi);
        channels.setCellRenderer(new UserTreeCellRenderer());
        channels.addMouseListener(new ClicDroitTree(chat, channels));

        //centre la frame
        setLocationRelativeTo(getRootPane());
    }

    void setOverlayer(Overlayer o) {
        if (overlay == null) {
            makeOverlayMenu();
            menu.add(jmOverlay);
        } else {
            removeOverlayMenuListeners(overlay);
        }
        if (o != null) {
            addOverlayMenuListeners(o);
        } else {
            menu.remove(jmOverlay);
        }
        overlay = o;
    }

    private void makeOverlayMenu() {
        jmOverlay.setText("Overlay");

        jmiOverlay1.setText("Overlay 1");
        jmOverlay.add(jmiOverlay1);

        jmiOverlay3.setText("Overlay 3");
        jmOverlay.add(jmiOverlay3);

        jmiOverlay5.setText("Overlay 5");
        jmOverlay.add(jmiOverlay5);
        
        jmOverlay.add(jSeparator1);

        jmiOverlayDesactiver.setText("Desactiver");
        jmiOverlayDesactiver.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(
                        java.awt.event.KeyEvent.VK_O,
                        java.awt.event.InputEvent.SHIFT_MASK
                        | java.awt.event.InputEvent.CTRL_MASK));
        jmOverlay.add(jmiOverlayDesactiver);
    }

    private void addOverlayMenuListeners(ActionListener al) {
        jmiOverlay1.addActionListener(al);
        jmiOverlay3.addActionListener(al);
        jmiOverlay5.addActionListener(al);
        jmiOverlayDesactiver.addActionListener(al);
    }
    
    private void removeOverlayMenuListeners(ActionListener al) {
        jmiOverlay1.removeActionListener(al);
        jmiOverlay3.removeActionListener(al);
        jmiOverlay5.removeActionListener(al);
        jmiOverlayDesactiver.removeActionListener(al);
    }

    void setChannels(TreeModel channels) {
        this.channels.setModel(channels);
    }
    
    void addConversation(Conversation c) {
        if(!conversations.containsKey(c.getName())) {
            conversations.put(c.getName(), c);
        }
    }

    void removeConversation(String conversation) {
        conversations.remove(conversation);
    }

    public boolean isTyping() {
        return typing;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }

    @Override
    public void entrer(String channel) {
        if (conversations.containsKey(channel)) {
            Conversation c = conversations.get(channel);
            ajouterOnglet(c);
        }
    }

    @Override
    public void sortir(String channel) {
        if (conversations.containsKey(channel)) {
            Conversation c = conversations.get(channel);
            retirerOnglet(c);
        }
    }

    private void ajouterOnglet(Conversation c) {
        onglets.addTab(c.getName(), c);
        int index = onglets.indexOfTab(c.getName());
        JCloseableTabComponent tab = new JCloseableTabComponent(onglets, c.getName());
        tab.addActionListener(new Fermer(chat, tab));
        onglets.setTabComponentAt(index, tab);
        c.setShowed(true);
    }

    private void retirerOnglet(Conversation c) {
        int index = onglets.indexOfTab(c.getName());
        if (index != -1) {
            onglets.removeTabAt(index);
            c.setShowed(false);
        }
    }

    public Conversation getConversation(String nom) {
        if (!nom.equals(CURRENT)) {
            return conversations.get(nom);
        }
        Conversation c = (Conversation) onglets.getSelectedComponent();
        return c;
    }

    @Override
    public void afficher(String channel, String user, String text) {
        Conversation c;
        c = getConversation(channel);
        if (c != null) {
            if (!c.isShowed()) {
                ajouterOnglet(c);
            }
            c.afficher(user, text);
            overlay.afficher(channel, user, text);
        }
    }

    @Override
    public void avertir(String titre, String message) {
        JOptionPane.showMessageDialog(this, message, titre,
                JOptionPane.WARNING_MESSAGE);
    }
    
    @Override
    public String demander(String titre, String message) {
        return JOptionPane.showInputDialog(this, message, titre, -1);
    }
    
    
    int confirmer(String titre, String message, int type) {
        return  JOptionPane.showConfirmDialog(this, message, titre, type);
    }


    @Override
    public void setVisible(boolean bln) {
        super.setVisible(bln);
        if (!bln) {
            getConversation(CURRENT).clear();
        }
    }
    
    void clear() {
        conversations.clear();
        onglets.removeAll();
    }

    public JButton getEnvoyer() {
        return envoyer;
    }

    public JTextField getMessage() {
        return message;
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
        jSplitPane1 = new javax.swing.JSplitPane();
        onglets = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        channels = new javax.swing.JTree();
        menu = new javax.swing.JMenuBar();
        jmIRC = new javax.swing.JMenu();
        jmiQuitter = new javax.swing.JMenuItem();

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

        getContentPane().add(dialogue, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setRightComponent(onglets);

        jScrollPane2.setViewportView(channels);

        jSplitPane1.setLeftComponent(jScrollPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jmIRC.setText("IRC");

        jmiQuitter.setText("Quitter");
        jmiQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQuitterActionPerformed(evt);
            }
        });
        jmIRC.add(jmiQuitter);

        menu.add(jmIRC);

        setJMenuBar(menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fermer(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_fermer
        chat.quitter();
    }//GEN-LAST:event_fermer

    private void jmiQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQuitterActionPerformed
        chat.quitter();
    }//GEN-LAST:event_jmiQuitterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree channels;
    private javax.swing.JPanel dialogue;
    private javax.swing.JButton envoyer;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenu jmIRC;
    private javax.swing.JMenuItem jmiQuitter;
    private javax.swing.JMenuBar menu;
    private javax.swing.JTextField message;
    private javax.swing.JTabbedPane onglets;
    // End of variables declaration//GEN-END:variables


}
