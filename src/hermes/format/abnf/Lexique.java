/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.format.abnf;

import java.util.*;
import java.util.regex.*;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Lexique {

    public static final ABNF letter = new ABNF("letter", "[a-zA-Z]");
    public static final ABNF character = new ABNF("character", "\\p{Print}");
    public static final ABNF space = new ABNF("space", " ");
    public static final ABNF digit = new ABNF("digit", "[0-9]");
    public static final ABNF crlf = new ABNF("crlf", "\r\n");
    public static final ABNF passchar = new ABNF("passchar", "[\\p{Print}&&[^ ]]");

    private final Map<String, ABNF> base;
    private final Map<String, ABNF> lexique;
    private final List<ABNF> capturees;
    private final Map<Pattern, Traducteur> traducteurs;

    public Lexique() {
        base = new HashMap<>();
        lexique = new HashMap<>();
        capturees = new ArrayList();
        traducteurs = new HashMap<>();
        initBase();
        initTraducteurs();
    }

    private void initBase() {
        ajouterBase(character);
        ajouterBase(crlf);
        ajouterBase(digit);
        ajouterBase(letter);
        ajouterBase(passchar);
        ajouterBase(space);
    }

    @Deprecated
    private void initTraducteurs() {

    }

    protected void ajouterBase(ABNF regle) {
        base.put(regle.getDefinition(), regle);
    }

    public void ajouter(ABNF regle) {
        lexique.put(regle.getDefinition(), regle);
    }

    public void ajouter(Traducteur traducteur) {
        traducteurs.put(traducteur.getPattern(), traducteur);
    }
    
    public ABNF compiler(String definition, String synthaxe, ABNF... variables) {
        capturees.clear();
        if(variables != null && variables.length != 0) {
            capturees.addAll(Arrays.asList(variables));
        }
        String pattern;
        synthaxe = remplacer(nettoyer(synthaxe), base, false);
        // pattern = traduire(synthaxe);
        // pattern = remplacer(pattern, lexique, true);
        pattern = remplacer(synthaxe, lexique, true);
        return new ABNF(definition, synthaxe, pattern);
    }

    public ABNF chercher(String definition) {
        return lexique.get(definition);
    }

    /**
     * Nettoie la séquence des espaces.
     *
     * @param sequence
     * @return
     */
    private String nettoyer(String sequence) {
        Matcher matcher = Pattern.compile(" ").matcher(sequence);
        return matcher.replaceAll("");
    }

    /**
     * Remplace dans la séquence les mots de bases par leur pattern.
     *
     * @param sequence
     * @return
     */
    private String remplacer(String sequence, Map<String,ABNF> lexique, boolean backslashes) {
        for (Map.Entry<String, ABNF> entry : lexique.entrySet()) {
            String definition = entry.getKey();
            ABNF abnf = entry.getValue();
            String synthax = abnf.getPattern();
            if(capturees.contains(abnf)) {
                synthax = abnf.getNamedPattern();
            }
            Matcher chercher = Pattern.compile(definition).matcher(sequence);
            sequence = chercher.replaceAll(synthax);
        }
        return backslashes ? backslash(sequence):sequence;
    }
    
    /**
     * Réinsère les backslashes ignorés par la fonction Matcher.replaceAll.
     * Ceci concerne les classes de caractères POSIX : \p{posix}.
     * @see Matcher#replaceAll(java.lang.String) 
     * @param sequence
     * @return 
     */
    private String backslash(String sequence) {
        Matcher chercher = Pattern.compile("p(?<posix>\\{[a-zA-Z{}]+\\})").matcher(sequence);
        sequence = chercher.replaceAll("\\\\p${posix}");
        return sequence;
    }

    /**
     * TODO : implémenter la gestion et utilisation des traducteurs ABNF vers le
     * format du pattern Java.
     *
     * @param mot
     * @return
     * @deprecated
     */
    @Deprecated
    private String traduire(String mot) {
        Matcher matcher = Pattern.compile("").matcher(mot);
        for (Map.Entry<Pattern, Traducteur> entry : traducteurs.entrySet()) {
            Pattern pattern = entry.getKey();
            Traducteur traducteur = entry.getValue();
            matcher.usePattern(pattern);
            if (matcher.matches()) {
                return traducteur.traduire(mot);
            }
        }
        return "";
    }

    /**
     * A déplacer dans un traducteur Quantifieur : digit*digit(sequence).
     *
     * @param abnf
     * @return
     * @deprecated
     */
    @Deprecated
    private String quantifier(String abnf) {
        int nombre1, nombre2;
        String sequence;
        Pattern quantifieur = Pattern.compile("(?<nb1>[0-9]+)\\Q*\\E(?<nb2>[0-9]+)(?<seq>[\\p{Print}]+)");
        Matcher extracteur = quantifieur.matcher(abnf);
        if (extracteur.matches()) {
            nombre1 = Integer.parseInt(extracteur.group("nb1"));
            nombre2 = Integer.parseInt(extracteur.group("nb2"));
            sequence = extracteur.group("seq");
            return String.format("(%s){%d,%d}+", sequence, nombre1, nombre2);
        }
        return null;
    }
}
