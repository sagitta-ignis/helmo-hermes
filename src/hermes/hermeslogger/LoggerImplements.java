/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.hermeslogger;

import hermes.hermeslogger.models.Configuration;
import hermes.hermeslogger.models.ListMessages;
import hermes.hermeslogger.models.ParcourirFichiers;
import hermes.hermeslogger.models.Verification;
import hermes.xml.Xml;
import hermes.xml.XmlImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBException;

/**
 *
 * @author David
 */
public class LoggerImplements implements HermesLogger {

    private final ListMessages listeMessages;
    private final Xml xml;
    private final String channel;
    private final Verification verification;
    private int messagesAttentes;

    public LoggerImplements(String channel) {
        xml = new XmlImpl();
        verification = new Verification();
        this.channel = channel;
        listeMessages = verification.fichierExistant(new File(nomFichierAEcrire()));
        
        messagesAttentes = 0;
    }

    @Override
    public void ajouterMessage(String message) throws IOException, JAXBException {
        if(message == null || message.length() < 1){
            System.err.println("[LOGGER] Problème message vide ou null");
            return;
        }
        listeMessages.ajouterMessage(message);
        messagesAttentes++;
        verificationNbMessages();
    }

    @Override
    public void close() throws IOException, JAXBException {
        ecrireFichier();
    }

    @Override
    public ArrayList<String> listeLogsSauvegarde() {
        ParcourirFichiers parcourir = new ParcourirFichiers();

        if (!verification.verifierExistanceDossier()) {
            return null;
        }
        return parcourir.obtenirListeFichiers();
    }

    @Override
    public ArrayList<String> lireLogXml(String nom) throws FileNotFoundException, Exception {
        ListMessages messages = null;

        messages = (ListMessages) xml.read(new FileInputStream(Configuration.DOSSIER + nom), ListMessages.class);

        if (messages != null) {
            return messages.getListe();
        }
        return null;
    }

    private void verificationNbMessages() throws IOException, JAXBException {
        if (messagesAttentes >= Configuration.MAXMESSAGESATTENTES) {
            ecrireFichier();
        }
    }

    private void ecrireFichier() throws IOException, JAXBException {
        verification.verifierExistanceDossier();

        xml.write(listeMessages, ListMessages.class, new File(nomFichierAEcrire()));
        messagesAttentes = 0;
    }

    private String nomFichierAEcrire() {
        Calendar date = new GregorianCalendar();
        StringBuilder builder = new StringBuilder();

        String jour = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        String mois = String.valueOf(date.get(Calendar.MONTH) + 1);
        String annee = String.valueOf(date.get(Calendar.YEAR));

        builder.append(Configuration.DOSSIER);
        builder.append(jour).append("-");
        builder.append(mois).append("-");
        builder.append(annee).append(" ");
        builder.append(channel).append(".xml");

        return builder.toString();
    }

}
