/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.AbstractChat;
import hermes.chat.controleur.Chatter;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author David
 */
public class Overlay extends AbstractChat {

    private JFrame frame;
    private JTextField fieldEnvoyer;
    private final Chatter chat;
    private int pX;
    private int pY;
    private final ArrayList<JLabel> labels;
    private final int CHARPARLIGNE = 60;

    public Overlay(Chatter chatter) {
        frame = new JFrame("Overlay");
        labels = new ArrayList<>();
        chat = chatter;
    }

    public void initialiser(int dimension) {
        initialisationInterfaceGraphique(dimension);
        setEnvoyerRecevoirListener();
        setDeplacerListener();
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    private void initialisationInterfaceGraphique(int dimension) {

        GridLayout experimentLayout = new GridLayout(dimension + 1, 0);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setVisible(false);
        frame = new JFrame("Overlay");
        labels.clear();

        frame.setUndecorated(true);
        frame.setOpacity((float) 0.8);
        frame.setSize(350, 30 * (dimension));
        frame.setLayout(experimentLayout);

        fieldEnvoyer = new JTextField();
        for (int i = 0; i < dimension; i++) {
            JLabel label = new JLabel();
            labels.add(label);
            frame.add(label);
        }
        Collections.reverse(labels);
        // frame.add(fieldEnvoyer);
        frame.setAlwaysOnTop(true);

        int x = (int) dim.getWidth() / 2 - 150;
        frame.setLocation(x, 0);
        frame.setVisible(true);
    }

    private void setEnvoyerRecevoirListener() {

        fieldEnvoyer.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    envoyer();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void setDeplacerListener() {
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                // Get x,y and store them
                pX = me.getX();
                pY = me.getY();
            }
        });

        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                frame.setLocation(frame.getLocation().x + me.getX() - pX, frame.getLocation().y + me.getY() - pY);
            }
        });
    }

    private void envoyer() {
        chat.ecrire(SERVER, fieldEnvoyer.getText(), true);
        fieldEnvoyer.setText("");
    }

    @Override
    public void afficher(String channel, String user, String text) {
        String mesage = String.format("[%s][%s] %s", channel, user, text);
        List<String> myLittlePoney = decomposer(mesage);
        String oldLady[] = new String[labels.size()];
        int compter = 0;
        int furiousCompter = 0;

        for (JLabel label : labels) {
            if (compter < myLittlePoney.size()) {
                oldLady[compter] = label.getText();
                label.setText(myLittlePoney.get(compter));

            } else {
                oldLady[compter] = label.getText();
                label.setText(oldLady[furiousCompter]);
                furiousCompter++;
            }
            compter++;
        }

    }
    
    @Override
    public void avertir(String titre, String message) {
    }

    private List<String> decomposer(String text) {
        List<String> tempo = new ArrayList<>();

        int div = (int) Math.ceil(text.length() / CHARPARLIGNE) + 1;
               
        if (div > labels.size()) {
            div = labels.size();
        }
        
        for (int i = 0; i < div; i++) {
            if (i == div - 1) {
                tempo.add(text.substring(i * CHARPARLIGNE, text.length()));
            } else {
                tempo.add(text.substring(i * CHARPARLIGNE, (i + 1) * CHARPARLIGNE));
            }
        }
        
        Collections.reverse(tempo);
        return tempo;
    }

    @Override
    public void entrer(String channel) {}

    @Override
    public void sortir(String channel) {}
}
