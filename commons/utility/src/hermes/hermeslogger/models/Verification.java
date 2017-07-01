/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.hermeslogger.models;

import hermes.xml.Xml;
import hermes.xml.XmlImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Verification {

    private Xml xml;

    public Verification() {
        xml = new XmlImpl();
    }

    public boolean verifierExistanceDossier() {
        File f = new File(Configuration.DOSSIER);

        if (!f.exists()) {
            new File(Configuration.DOSSIER).mkdir();
            return false;
        }
        return true;
    }

    public ListMessages fichierExistant(File fichier) {
        try {
            if (fichier.exists()) {
                return (ListMessages) xml.read(new FileInputStream(fichier), ListMessages.class);
            }

        } catch (Exception ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ListMessages();
    }
}
