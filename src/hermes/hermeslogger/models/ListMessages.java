/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.hermeslogger.models;

import java.util.ArrayList;
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

    @XmlElement(name = "message", type = String.class)
    private final ArrayList<String> listeMessage;

    public ListMessages() {
        listeMessage = new ArrayList<>();
    }

    public ListMessages(ArrayList<String> listeMessage) {
        this.listeMessage = listeMessage;
    }

    public ArrayList<String> getListe() {
        return listeMessage;
    }

    public void ajouterMessage(String message) {
        listeMessage.add(message);
    }

}
