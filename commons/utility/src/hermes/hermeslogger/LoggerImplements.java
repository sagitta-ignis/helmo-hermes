/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.hermeslogger;

import hermes.hermeslogger.models.Configuration;
import hermes.hermeslogger.models.ListMessages;
import hermes.hermeslogger.models.Message;
import hermes.hermeslogger.models.ParcourirFichiers;
import hermes.hermeslogger.models.Verification;
import hermes.xml.Xml;
import hermes.xml.XmlImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author David
 */
public class LoggerImplements implements HermesLogger {

    private static final Xml xml = new XmlImpl();
    private static final Verification verification = new Verification();

    private final ListMessages listeMessages;
    private final String channel;
    private int messagesAttentes;
    private final Calendar currentDate;

    public LoggerImplements(String channel) {
        currentDate = new GregorianCalendar();
        this.channel = channel;
        listeMessages = verification.fichierExistant(new File(nomFichierAEcrire()));
        listeMessages.setTitre(channel);
        listeMessages.setDate(formaterDate());

        messagesAttentes = 0;
    }

    @Override
    public void ajouterMessage(String auteur, String message) throws IOException, JAXBException {
        if (verifierChamps(auteur, message)) {
            listeMessages.ajouterMessage(new Message(auteur, message));
            messagesAttentes++;
            verificationNbMessages();
        }
    }

    @Override
    public void close() throws IOException, JAXBException {
        ecrireFichier();
    }

    @Override
    public List<String> listeLogsSauvegarde() {
        ParcourirFichiers parcourir = new ParcourirFichiers();

        if (!verification.verifierExistanceDossier()) {
            return null;
        }
        return parcourir.obtenirListeFichiers();
    }

    @Override
    public ListMessages lireLogXml(String nom) throws FileNotFoundException, Exception {
        ListMessages messages = null;

        messages = (ListMessages) xml.read(new FileInputStream(Configuration.DOSSIER + nom), ListMessages.class);

        return messages;
    }

    private void verificationNbMessages() throws IOException, JAXBException {
        if (messagesAttentes >= Configuration.MAXMESSAGESATTENTES) {
            ecrireFichier();
        }
    }

    private void ecrireFichier() throws IOException, JAXBException {
        verifierDateFichier();
        verification.verifierExistanceDossier();

        xml.write(listeMessages, ListMessages.class, new File(nomFichierAEcrire()));
        messagesAttentes = 0;
    }

    private String nomFichierAEcrire() {
        StringBuilder builder = new StringBuilder();

        builder.append(Configuration.DOSSIER);
        builder.append(formaterDate()).append(" ");
        builder.append(channel).append(".xml");

        return builder.toString();
    }

    private String formaterDate() {
        StringBuilder builder = new StringBuilder();

        String jour = String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH));
        String mois = String.valueOf(currentDate.get(Calendar.MONTH) + 1);
        String annee = String.valueOf(currentDate.get(Calendar.YEAR));

        builder.append(jour).append("-");
        builder.append(mois).append("-");
        builder.append(annee);

        return builder.toString();
    }

    private boolean verifierChamps(String auteur, String message) {

        if (message == null || message.length() < 1) {
            System.err.println("[LOGGER] Problème message vide ou null");
            return false;
        }
        if (auteur == null || auteur.length() < 1) {
            System.err.println("[LOGGER] Problème auteur vide ou null");
            return false;
        }

        return true;
    }

    private void verifierDateFichier() {
        Calendar newDate = new GregorianCalendar();
        if (currentDate.get(Calendar.DAY_OF_MONTH) != newDate.get(Calendar.DAY_OF_MONTH)) {
            nomFichierAEcrire();
        }
    }

}
