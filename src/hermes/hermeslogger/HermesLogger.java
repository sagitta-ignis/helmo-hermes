/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.hermeslogger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBException;

/**
 *
 * @author David
 */
public interface HermesLogger {
    
    void ajouterMessage(String message)  throws IOException, JAXBException;
    
    ArrayList<String> listeLogsSauvegarde();
    ArrayList<String> lireLogXml(String nom) throws FileNotFoundException,Exception;
    
    void close() throws IOException, JAXBException;
}
