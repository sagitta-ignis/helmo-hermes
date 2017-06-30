/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.format.abnf;

import hermes.format.abnf.parser.ABNFSyntaxicalParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ABNF {

    public final static Lexique lexique = new Lexique();
    public static ABNF compiler(String rule, ABNF... variables) {
        ABNF abnf = null;
        try {
            ABNFSyntaxicalParser parser = new ABNFSyntaxicalParser();
            parser.setLexique(lexique);
            setCapturable(variables, true);
            abnf = parser.parseFrom(rule);
            setCapturable(variables, false);
        } catch (IOException ex) {
            Logger.getLogger(Lexique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Lexique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return abnf;
    }
    public static ABNF compilerEtAjouter(String rule, ABNF... variables) {
        ABNF abnf = compiler(rule, variables);
        lexique.ajouter(abnf);
        return abnf;
    }

    private static void setCapturable(ABNF variables[], boolean capturable) throws Exception {
        for (ABNF variable : variables) {
            ABNF var = lexique.obtenir(variable.getName());
            if(var == null) throw new Exception("Variable "+variable.getName()+" not found in lexique");
            var.capturable = capturable;
        }
    }

    private final String name;
    private final String rule;
    private final String pattern;
    private boolean capturable;

    public ABNF(String name, String rule, String pattern) {
        this.name = name;
        this.rule = rule;
        this.pattern = pattern;
        capturable = false;
    }

    public String getPattern() {
        return pattern;
    }

    public String getRule() {
        return rule;
    }

    public boolean isCapturable() {
        return capturable;
    }

    public void setCapturable(boolean capturable) {
        this.capturable = capturable;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return rule;
    }
}
