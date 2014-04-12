/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.protocole.message;

import hermes.format.abnf.ABNF;
import hermes.format.abnf.Lexique;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MessageProtocole implements Comparable<MessageProtocole> {
    private static final String EMPTY = "empty";
    private static final Remplaceur remplaceur = new Remplaceur();
    
    private final ABNF format;
    
    private String message;
    private String sequence;
    private final Map<String,Object> affections;

    public MessageProtocole(Lexique lexique, String nom, String synthaxe, ABNF... variables) {
        format = lexique.compiler(nom, synthaxe, variables);
        affections = new HashMap<>();
        initVariables(variables);
    }
    
    private void initVariables(ABNF[] variables) {
        for (ABNF abnf : variables) {
            affections.put(abnf.getDefinition(), "empty");
        }
    }

    public boolean set(ABNF variable, String valeur){
        if(!existe(variable)) {
            return false;
        }
        if(!verifier(valeur, variable)) {
            return false;
        }
        affections.put(variable.getDefinition(), valeur);
        return true;
    }
    
    public boolean existe(ABNF variable) {
        return affections.containsKey(variable.getDefinition());
    }
    
    public String remplir() throws Exception {
        message = format.getSynthax();
        for (Map.Entry<String, Object> entry : affections.entrySet()) {
            String variable = entry.getKey();
            Object valeur = entry.getValue();
            if(valeur.equals(EMPTY)) {
                throw new Exception(variable + "not initialized");
            }
            message = remplaceur.remplacer(message, variable, valeur);
        }
        return message;
    }
    
    public void effacer() {
        for (Map.Entry<String, Object> entry : affections.entrySet()) {
            String key = entry.getKey();
            affections.put(key, EMPTY);
        }
    }
    
    public boolean scanner(String sequence) {
        boolean verification = verifier(sequence, format);
        if(verification) {
            this.sequence = sequence;
        }
        return verification;
    }
    
    private boolean verifier(String sequence, ABNF format) {
        return Pattern.compile(format.getPattern()).matcher(sequence).matches();
    }
    
    public String get(ABNF variable) {
        if(sequence != null) {
            if(existe(variable)) {
                return extraire(variable.getDefinition());
            }
        }
        return null;
    }
    
    private String extraire(String variable) {
        Matcher m =  Pattern.compile(format.getPattern()).matcher(sequence);
        m.matches();
        return m.group(variable);
    }

    @Override
    public int compareTo(MessageProtocole t) {
        return getNom().compareTo(t.getNom());
    }

    public String getNom() {
        return format.getDefinition();
    }
}
