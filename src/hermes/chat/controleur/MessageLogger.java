/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.chat.controleur;

import hermes.chat.AbstractChat;
import hermes.logger.Logger;
import hermes.logger.LoggerImplements;
import java.io.IOException;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MessageLogger extends AbstractChat {

    private final Logger logger;

    public MessageLogger() {
        logger = new LoggerImplements("General");
    }
    
    @Override
    public void afficher(String texte) {
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
}
