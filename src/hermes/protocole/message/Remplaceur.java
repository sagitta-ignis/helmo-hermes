/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.protocole.message;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Remplaceur {
    
    private final Map<Class,CommandArgument> remplaceurs;

    public Remplaceur() {
        remplaceurs = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        remplaceurs.put(String.class, new CommandArgument() {
            @Override
            public void execute() {
            }
        });
    }    
    
    public String remplacer(String message, String variable, Object valeur) {
        Class classeKey = String.class;
        Class classeValeur = valeur.getClass();
        if(classeKey.isAssignableFrom(classeValeur)) {
            int i = 0;
        }
        return null;
    }
    
    private String remplacerParString(String message, String variable, String valeur) {
        Matcher chercher = Pattern.compile(variable).matcher(message);
        return chercher.replaceAll(valeur);
    }
}
