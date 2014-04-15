/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.protocole.message;

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
public class MessageProtocole implements Comparable<MessageProtocole> {

    public static final String EMPTY = "empty";
    public static final String NULL = null;

    private final ABNF format;
    
    private String sequence;
    private final Map<ABNF, Object> affections;

    public MessageProtocole(String rule, ABNF... variables) {
        this.format = ABNF.compiler(rule, variables);
        affections = new HashMap<>();
        initVariables(variables);
    }

    private void initVariables(ABNF[] variables) {
        for (ABNF abnf : variables) {
            affections.put(abnf, NULL);
        }
    }

    public boolean set(ABNF variable, Object valeur) {
        if (!existe(variable)) {
            return false;
        }
        if (!verifier(valeur, variable)) {
            return false;
        }
        affections.put(variable, valeur);
        return true;
    }

    public boolean existe(ABNF variable) {
        return affections.containsKey(variable);
    }

    public String remplir() throws Exception {
        MessageProtocoleFiller filler = new MessageProtocoleFiller();
        filler.setLexique(ABNF.lexique);
        return filler.fillWith(format.getRule(), affections);
    }

    public void effacer() {
        for (Map.Entry<ABNF, Object> entry : affections.entrySet()) {
            ABNF key = entry.getKey();
            affections.put(key, NULL);
        }
    }
    
    public boolean scanner(String sequence) {
        boolean verification = verifier(sequence, format);
        if(verification) {
            this.sequence = sequence;
        }
        return verification;
    }
    
    private boolean verifier(Object valeur, ABNF format) {
        if(valeur instanceof String || valeur instanceof List) {
            if(valeur instanceof String) {
                return match((String)valeur, format);
            }
            if (valeur instanceof List) {
                List list = (List) valeur;
                for (Object object : list) {
                    if(!verifier(object, format)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    private boolean match(String valeur, ABNF format) {
        return Pattern.compile(format.getPattern()).matcher(valeur).matches();
    }
    
    public String get(ABNF variable) {
        if(sequence != null) {
            if(existe(variable)) {
                return extraire(variable);
            }
        }
        return null;
    }
    
    public List<String> getAll(ABNF variable) {
        if(sequence != null) {
            if(existe(variable)) {
                return extraireAll(variable);
            }
        }
        return null;
    }
    
    private String extraire(ABNF variable) {
        Matcher m =  Pattern.compile(format.getPattern()).matcher(sequence);
        m.matches();
        return m.group(variable.getName());
    }
    
    private List<String> extraireAll(ABNF variable) {
        List<String> list = new ArrayList<>();
        String element;
        Matcher m =  Pattern.compile("("+variable.getPattern()+")").matcher(sequence);
        while(m.find()) {
            element = m.group();
            if(element != null) {
                list.add(element);
            }
        }
        return list;
    }

    @Override
    public int compareTo(MessageProtocole t) {
        return getNom().compareTo(t.getNom());
    }

    public String getNom() {
        return format.getName();
    }
}
