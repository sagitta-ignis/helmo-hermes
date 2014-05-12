/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.controleur;

import hermes.chat.AbstractChat;
import hermes.hermeslogger.HermesLogger;
import hermes.hermeslogger.LoggerImplements;
import java.io.IOException;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MessageLogger extends AbstractChat {

    private final HermesLogger logger;

    public MessageLogger() {
        logger = new LoggerImplements("General");
    }
    
    @Override
    public void afficher(String channel, String user, String texte) {
        try {
            logger.ajouterMessage(texte);
        } catch (IOException | JAXBException ex) {
            java.util.logging.Logger.getLogger(MessageLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void avertir(String titre, String message) {}
    
    public void close() throws IOException, JAXBException {
        logger.close();
    }

    @Override
    public void entrer(String channel) {}

    @Override
    public void sortir(String channel) {}
}
