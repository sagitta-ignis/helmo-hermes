/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.format.abnf;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Lexique {

    /**
     * cr = %x0D ; caractères \r de Java & C.
     */
    public final static ABNF cr = new ABNF("cr", "cr = %x0D", "\\x0D");
    /**
     * lf = %x0A ; caractères \n de Java & C.
     */
    public final static ABNF lf = new ABNF("lf", "lf = %x0A", "\\x0D");
    /**
     * crlf = %x0D %x0A ; caractères \r\n de Java & C.
     */
    public final static ABNF crlf = new ABNF("crlf", "crlf = %x0D %x0A", "\\x0D\\x0A");
    /**
     * space = %x20 ; le caractère espace (code ASCII 20 en hexadécimal).
     */
    public final static ABNF space = new ABNF("space", "space = %x20", "\\x20");
    /**
     * digit = "0"/"1"/"2"/"3"/"4"/"5"/"6"/"7"/"8"/"9" ; un chiffre.
     */
    public final static ABNF digit = new ABNF("digit", "digit = %x30-39", "[0-9]");
    /**
     * letter = %x41-5A / %x61-7A ; lettre majuscule A-Z ou minuscule a-z.
     */
    public final static ABNF letter = new ABNF("letter", "letter = %x41-5A|%x61-7A", "[a-zA-Z]");
    /**
     * character = %x20-ff ; les caractères imprimables (espace compris).
     */
    public final static ABNF character = new ABNF("character", "character = %x20-ff", "[\\x20-\\xFF]");
    /**
     * passchar = %x21-ff ; les caractères imprimables sauf espace.
     */
    public final static ABNF passchar = new ABNF("passchar", "passchar = %x21-ff", "[\\x21-\\xFF]");

    private final Map<String, ABNF> lexique;

    public Lexique() {
        lexique = new HashMap<>();
        initCore();
    }

    private void initCore() {
        ajouter(cr);
        ajouter(lf);
        ajouter(crlf);
        ajouter(space);
        ajouter(digit);
        ajouter(letter);
        ajouter(character);
        ajouter(passchar);
    }

    public boolean ajouter(ABNF regle) {
        if (!lexique.containsKey(regle.getName())) {
            lexique.put(regle.getName(), regle);
            return true;
        }
        return false;
    }

    public ABNF obtenir(String name) {
        return lexique.get(name);
    }
}
