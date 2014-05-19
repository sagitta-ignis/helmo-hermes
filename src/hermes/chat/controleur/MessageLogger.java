/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.controleur;

import hermes.chat.AbstractChat;
import hermes.chat.vue.ChatIRC;
import hermes.chat.vue.FileExplorer;
import hermes.hermeslogger.HermesLogger;
import hermes.hermeslogger.LoggerImplements;
import hermes.hermeslogger.models.Configuration;
import hermes.hermeslogger.models.ListMessages;
import hermes.hermeslogger.models.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MessageLogger extends AbstractChat implements ActionListener {

    private FileExplorer explorateur;
    private final ChatIRC chat;

    private final Map<String, HermesLogger> loggers;

    public MessageLogger(ChatIRC circ) {
        chat = circ;
        loggers = new ConcurrentHashMap<>();
    }

    @Override
    public void afficher(String channel, String user, String texte) {
        try {
            HermesLogger logger = get(channel);
            logger.ajouterMessage(user, texte);
        } catch (IOException | JAXBException ex) {
            java.util.logging.Logger.getLogger(MessageLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private HermesLogger get(String channel) {
        return loggers.get(channel);
    }

    @Override
    public void avertir(String titre, String message) {
    }

    public void close() throws IOException, JAXBException {
        for (HermesLogger logger : loggers.values()) {
            logger.close();
        }
        loggers.clear();
    }

    @Override
    public void entrer(String channel) {
        if (!loggers.containsKey(channel)) {
            loggers.put(channel, new LoggerImplements(channel));
        }
    }

    @Override
    public void sortir(String channel) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) e.getSource();
            String action = item.getText();
            switch (action) {
                case "Parcourir":
                    newFileExplorer();
                    break;
            }
        } else if (e.getSource() instanceof JFileChooser) {
            switch (e.getActionCommand()) {
                case "CancelSelection":
                    dropFileExplorer();
                    break;
                case "ApproveSelection":
                    loadFile();
                    dropFileExplorer();
                    break;
            }
        }
    }

    private void newFileExplorer() {
        explorateur = new FileExplorer(Configuration.DOSSIER);
        explorateur.addActionListener(this);
        explorateur.setVisible(true);
    }

    private void loadFile() {
        ListMessages messages = null;
        String name = "";
        try {
            File f = explorateur.getFichier();
            HermesLogger hermes = new LoggerImplements("Historique");
            name = f.getName();
            messages = hermes.lireLogXml(name);
        } catch (Exception ex) {
            chat.avertir("Erreur", "ce fichier n'a pas pu être ouvert");
            messages = null;
        } finally {
            if (messages != null) {
                afficherMessags(messages);
            }
        }
    }

    private void afficherMessags(ListMessages lm) {
        String date = lm.getDate();
        String titre = "Historique de "+lm.getTitre();
        chat.ouvrirHistorique(titre, true);
        chat.afficher(titre, "Conversation créée le", date);
        List<Message> messages = lm.getListe();
        for (Message m : messages) {
            chat.afficher(titre, m.getAuteur(), m.getMessage());
        }
    }

    private void dropFileExplorer() {
        explorateur.setVisible(false);
        explorateur.dispose();
    }

}
