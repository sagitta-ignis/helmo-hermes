/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.format.abnf;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ABNF {

    private final String definition;
    private final String synthax;
    private final String pattern;

    public ABNF(String definition, String synthax, String pattern) {
        this.definition = definition;
        this.synthax = synthax;
        this.pattern = pattern;
    }

    public ABNF(String definition, String synthax) {
        this(definition, synthax, synthax);
    }

    public String getPattern() {
        return pattern;
    }

    public String getSynthax() {
        return synthax;
    }

    public String getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return definition;
    }
}
