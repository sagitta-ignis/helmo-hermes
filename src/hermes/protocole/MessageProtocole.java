/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.protocole;

import hermes.format.abnf.ABNF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MessageProtocole {
    private final ABNF format;
    
    private String message;
    private final List<String> variables;
    private final Map<String,String> affections;

    public MessageProtocole(ABNF format) {
        this.format = format;
        message = format.getSynthax();
        variables = new ArrayList<>();
        affections = new HashMap<>();
    }
    
    public void ajouter(ABNF variable) {
        variables.add(variable.getDefinition());
    }

    public boolean set(ABNF variable, String valeur){
        if(!variables.contains(variable.getDefinition())) {
            return false;
        }
        affections.put(variable.getDefinition(), valeur);
        return true;
    }
    
    public String remplir() {
        for (Map.Entry<String, String> entry : affections.entrySet()) {
            String variable = entry.getKey();
            String valeur = entry.getValue();
            remplacer(variable, valeur);
        }
        return message;
    }
    
    private void remplacer(String variable, String valeur) {
        Matcher chercher = Pattern.compile(variable).matcher(message);
        message = chercher.replaceAll(valeur);
    }
    
    public boolean verifier(String sequence) {
        return Pattern.compile(format.getPattern()).matcher(sequence).matches();
    }
}
