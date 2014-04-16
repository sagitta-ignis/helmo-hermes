/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.protocole.message;

import hermes.format.abnf.ABNF;
import hermes.format.abnf.Lexique;
import hermes.format.abnf.parser.ABNFSyntaxicalParser;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class MessageProtocoleFiller extends ABNFSyntaxicalParser {

    private final static int initial = 0;
    private final static int optional = 1;
    private final static int cancel_optional = 2;
    private final static int repetition = 3;
    private final static int alternative = 4;

    private StringBuilder message;
    private Map<ABNF, Object> affectations;
    private int etat;
    private Queue<String> keywords;

    public String fillWith(String input, Map<ABNF, Object> affectations) throws ParseException, IOException, Exception {
        message = new StringBuilder();
        initAffectations(affectations);
        etat = initial;
        keywords = new ConcurrentLinkedQueue();
        parseFrom(input);
        return message.toString();
    }

    private void initAffectations(Map<ABNF, Object> affectations) {
        this.affectations = new HashMap<>(affectations);
        this.affectations.put(Lexique.space, " ");
        this.affectations.put(Lexique.crlf, "\r\n");
        this.affectations.put(Lexique.cr, "\r");
        this.affectations.put(Lexique.lf, "\n");
    }

    protected void parseLiteral() {
        super.parseLiteral();
        message.append(rules.peek());
    }

    protected void parseOptional() throws IOException, ParseException {
        StringBuilder temp = message;
        message = new StringBuilder();
        etat = optional;
        super.parseOptional();
        etat = initial;
        message = temp.append(message);
    }

    protected void parseGroup() throws IOException, ParseException {
        StringBuilder temp = message;
        message = new StringBuilder();
        super.parseGroup();
        message = temp.append(message);
    }

    protected void parseAlternative() throws IOException, ParseException {

    }

    protected void parseRepetition() throws IOException, ParseException {
        StringBuilder temp = message;
        message = new StringBuilder();
        etat = repetition;
        super.parseRepetition();
        StringBuilder ruleInRepetition = message;
        message = temp;
        String repetitions = appendRepetitions(ruleInRepetition.toString(), Integer.parseInt(a), Integer.parseInt(b));
        etat = initial;
        message.append(repetitions);
    }

    private String appendRepetitions(String repetition, int min, int max) throws ParseException {
        int i;
        StringBuilder repetitions = new StringBuilder();
        for (i = 0; i < max; i++) {
            String appendedRepetition = appendARepetition(repetition, i);
            if (appendedRepetition == null) {
                break;
            }
            repetitions.append(appendedRepetition);
        }
        if (i < min) {
            throw new ParseException("not enough key-value given in affectation's map : " + i + ", expected at least " + min, getCharCount());
        }
        return repetitions.toString();
    }

    private String appendARepetition(String repetition, int iteration) throws ParseException {
        for (String keyword : keywords) {
            boolean correct = true;
            Object value = findValue(keyword);
            if (value instanceof String || value instanceof List) {
                if (value instanceof List) {
                    List list = (List) value;
                    if (iteration >= list.size()) {
                        return null;
                    }
                    value = list.get(iteration);
                    if (!(value instanceof String)) {
                        correct = false;
                    }
                }
            } else {
                correct = false;
            }
            if (correct) {
                repetition = replace(keyword, repetition, (String) value);
            } else {
                throw new ParseException("value given for keyword " + keyword + " must be instance of String or List<String>", getCharCount());
            }
        }
        return repetition;
    }

    private String replace(String keyword, String input, String value) {
        return Pattern.compile(keyword).matcher(input).replaceAll(value);
    }

    protected void parseConcatenation() throws IOException, ParseException {

    }

    protected void parseTerminalValue() throws IOException, ParseException {

    }

    protected void parseKeyword() throws ParseException {
        super.parseKeyword();
        String keyword = getParsedString();
        String value;
        switch (etat) {
            case repetition:
                message.append(keyword);
                keywords.offer(keyword);
                break;
            case optional:
                value = (String) findValue(keyword);
                if (value == null) {
                    message = new StringBuilder();
                    etat = cancel_optional;
                } else {
                    message.append(value);
                }
                break;
            case cancel_optional:

                break;
            case alternative:

                break;
            default:
                value = (String) findValue(keyword);
                if (value == null) {
                    throw new ParseException("keyword " + keyword + " expected in affectation's map : no key-value found", getCharCount());
                }
                message.append(value);
                break;
        }
    }

    private Object findValue(String keyword) {
        for (Map.Entry<ABNF, Object> entry : affectations.entrySet()) {
            ABNF abnf = entry.getKey();
            Object value = entry.getValue();
            if (abnf.getName().equals(keyword)) {
                return value;
            }
        }
        return null;
    }

}
