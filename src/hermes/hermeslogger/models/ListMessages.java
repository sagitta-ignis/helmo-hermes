/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.hermeslogger.models;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Conversation")
public class ListMessages {

    @XmlElement(name = "titre", type = String.class)
    private String titre;
    @XmlElement(name = "date", type = String.class)
    private String date;
    @XmlElement(name = "message", type = Message.class)
    private final List<Message> listeMessage;
    
    public ListMessages() {
        this(new ArrayList<Message>());
    }

    public ListMessages(List<Message> listeMessage) {
        titre = "";
        date = "";
        this.listeMessage = listeMessage;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Message> getListe() {
        return listeMessage;
    }

    public void ajouterMessage(Message message) {
        listeMessage.add(message);
    }

}
