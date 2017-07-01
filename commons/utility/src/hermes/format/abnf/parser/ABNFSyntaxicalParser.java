/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.format.abnf.parser;

import hermes.format.abnf.Lexique;
import hermes.format.abnf.ABNF;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class ABNFSyntaxicalParser {

    private final static Pattern hexadecimal = Pattern.compile("x(?<value>[a-fA-F0-9]{1,2})");
    private final static Pattern binary = Pattern.compile("b(?<value>[01]{8})");
    private final static Pattern decimal = Pattern.compile("d(?<value>[0-9]{1,3})");
    private final static Map<Pattern, Integer> bases = new HashMap<>();

    static {
        bases.put(binary, 2);
        bases.put(decimal, 10);
        bases.put(hexadecimal, 16);
    }

    private final ABNFLexicalParser lexicalParser;
    /**
     * Contient des règles ABNF déjà parsées.
     * Ces règles ABNF seront réutilisées pour construire de nouvelles règles.
     */
    private Lexique lexique;
    
    protected final Stack<StringBuilder> rules;
    protected final Stack<String> values;
    protected String a;
    protected String b;

    private String name;
    private StringBuilder patternBuilder;

    public ABNFSyntaxicalParser(Lexique lexique) {
        this.lexique = lexique;
        lexicalParser = new ABNFLexicalParser();
        rules = new Stack<>();
        values = new Stack<>();
    }
    
    public ABNFSyntaxicalParser() {
        this(new Lexique());
    }

    public Lexique getLexique() {
        return lexique;
    }

    /**
     * Initialise un lexique de règles ABNF.
     * @param lexique le lexique contenant les règles ABNF
     */
    public void setLexique(Lexique lexique) {
        this.lexique = lexique;
    }

    /**
     * Vérifie que le token suivant correspond à ce qui est attendu.
     *
     * @param expected le token attendu
     * @param actual le token suivant
     * @throws ParseException {@link #JSONSyntaxicalParser() voir ici}
     * @throws IOException {@link #JSONSyntaxicalParser() voir ici}
     */
    protected void checkNextToken(int expected, int actual)
            throws ParseException, IOException {
        if (actual != expected) {
            throw new ParseException("Erreur de syntaxe : ("
                    + getParsedString() + ")'" + actual
                    + "', symbole attendu "
                    + expected, getCharCount());
        }
    }
    
    protected String getParsedString() {
        return lexicalParser.getParsedString();
    }
    
    protected int getCharCount() {
        return lexicalParser.getCharCount();
    }

    public final ABNF parseFrom(String input) throws ParseException, IOException, Exception {
        lexicalParser.setReader(new BufferedReader(new StringReader(input + "\r\n")));
        rules.clear();
        values.clear();
        parseABNF();
        ABNF abnf = new ABNF(name, input, patternBuilder.toString());
        return abnf;
    }

    protected void parseABNF() throws IOException, ParseException {
        int token;
        do {
            token = lexicalParser.nextToken();
            switch (token) {
                case TokenType.KEYWORD:
                    parseName();
                    break;
                case TokenType.EQUAL:
                    parseElements();
                    break;
            }
        } while (token != TokenType.LF);
    }

    protected void parseName() throws IOException, ParseException {
        name = getParsedString();
        checkNextToken(TokenType.SPACE, lexicalParser.nextToken());
    }

    protected void parseElements() throws IOException, ParseException {
        checkNextToken(TokenType.SPACE, lexicalParser.nextToken());
        int token = lexicalParser.nextToken();
        patternBuilder = new StringBuilder();
        do {
            rules.push(new StringBuilder());
            parseRule(token);
            token = lexicalParser.nextToken();
        } while (token != TokenType.CR);
        while (!rules.empty()) {
            patternBuilder.append(rules.pop());
        }
    }

    protected void parseRule(int token) throws IOException, ParseException {
        switch (token) {
            case TokenType.LITERAL:
                parseLiteral();
                break;
            case TokenType.OPEN_BRACE:
                parseOptional();
                break;
            case TokenType.OPEN_PARENTHESE:
                parseGroup();
                break;
            case TokenType.PIPE:
                parseAlternative();
                break;
            case TokenType.DIGIT:
                parseDigit();
                break;
            case TokenType.ASTERISK:
                parseRepetition();
                break;
            case TokenType.SPACE:
                parseConcatenation();
                break;
            case TokenType.PERCENT:
                parseTerminalValue();
                break;
            default:
                parseKeyword();
                break;
        }
    }

    protected void parseLiteral() {
        if(rules.peek().toString().length() != 0) {
            rules.push(new StringBuilder());
        }
        rules.push(rules.pop().append(getParsedString()));
    }
    
    protected void parseDigit() {
        values.add(getParsedString());
    }

    protected void parseOptional() throws IOException, ParseException {
        int token = lexicalParser.nextToken();
        do {
            parseRule(token);
            token = lexicalParser.nextToken();
        } while (token != TokenType.CLOSED_BRACE);
        StringBuilder builder = rules.pop();
        rules.push(new StringBuilder("(?:").append(builder.toString()).append(")?"));
    }

    protected void parseGroup() throws IOException, ParseException {
        int token = lexicalParser.nextToken();
        do {
            parseRule(token);
            token = lexicalParser.nextToken();
        } while (token != TokenType.CLOSED_PARENTHESE);
        StringBuilder builder = rules.pop();
        rules.push(new StringBuilder().append("(?:").append(builder.toString()).append(")"));
        int i = 0;
    }

    protected void parseAlternative() throws IOException, ParseException {
        parseRule(lexicalParser.nextToken());
        StringBuilder bRule = rules.pop();
        StringBuilder aRule = rules.pop();
        rules.push(new StringBuilder("[").append(aRule).append(bRule).append("]"));
    }

    protected void parseRepetition() throws IOException, ParseException {
        a = "0";
        b = String.valueOf(Integer.MAX_VALUE);
        if (!values.empty()) {
            a = values.pop();
        }
        int token = lexicalParser.nextToken();
        if(token == TokenType.DIGIT) {
            b = getParsedString();
            token = lexicalParser.nextToken();
        }
        parseRule(token);
        StringBuilder rule = rules.pop();
        String quantifier = makeQuantifier(a,b);
        rules.push(new StringBuilder(rule).append(quantifier));
    }
    
    private String makeQuantifier(String a, String b) {
        StringBuilder builder = new StringBuilder();
        if(a.equals(b)) {
            builder.append("{").append(a).append("}");
        } else if(!a.equals("0") && b.equals(String.valueOf(Integer.MAX_VALUE))) {
            builder.append("{").append(a).append(",}");
        } else if(a.equals("0") && b.equals(String.valueOf(Integer.MAX_VALUE))) {
            builder.append("*");
        } else {
            builder.append("{").append(a).append(",").append(b).append("}");
        }
        return builder.toString();
    }

    protected void parseConcatenation() throws IOException, ParseException {
        parseRule(lexicalParser.nextToken());
        StringBuilder bRule = rules.pop();
        StringBuilder aRule = rules.pop();
        aRule.append(bRule.toString());
        rules.push(aRule);
    }

    protected void parseTerminalValue() throws IOException, ParseException {
        checkNextToken(TokenType.KEYWORD, lexicalParser.nextToken());
        String parsed = getParsedString();
        if(rules.peek().toString().length() != 0) {
            rules.push(new StringBuilder());
        }
        Matcher m = Pattern.compile("(?<base>[xdb])(?<value1>[a-fA-F0-9]{1,2})-(?<value2>[a-fA-F0-9]{1,2})").matcher(parsed);
        if (m.matches()) {
            String base = m.group("base");
            String value1 = m.group("value1");
            String value2 = m.group("value2");
            rules.push(rules.pop().append("["));
            parseRangedValues(base+value1, base+value2);
            rules.push(rules.pop().append("]"));
        } else {
            parseValue(parsed);
        }
    }

    private void parseRangedValues(String value1, String value2) throws ParseException {
        parseValue(value1);
        rules.push(rules.pop().append("-"));
        parseValue(value2);
    }

    private void parseValue(String parsed) throws ParseException {
        String value = decimaltoHexadecimal(toDecimal(parsed));
        if (value == null) {
            throw new ParseException("Terminal value expected, parsed : " + parsed, getCharCount());
        }
        rules.push(rules.pop().append("\\x").append(value.toUpperCase()));
    }

    protected void parseKeyword() throws ParseException {
        String parsed = getParsedString();
        ABNF rule = lexique.obtenir(parsed);
        if (rule == null) {
            throw new ParseException("Keyword expected, parsed : " + parsed, getCharCount());
        }
        String pattern = rule.getPattern();
        if(rule.isCapturable()) {
            pattern = new StringBuilder("(?<").append(rule.getName()).append(">").append(pattern).append(")").toString();
        }
        if(rules.peek().toString().length() != 0) {
            rules.push(new StringBuilder(pattern));
        } else {
            rules.push(rules.pop().append(pattern));
        }
    }

    private String decimaltoHexadecimal(String value) {
        if (value == null) {
            return null;
        }
        value = Integer.toHexString(Integer.parseInt(value));
        if (value.length() < 2) {
            value = "0" + value;
        }
        return value;
    }

    protected String toDecimal(String value) {
        if (value == null) {
            return null;
        }
        for (Map.Entry<Pattern, Integer> entry : bases.entrySet()) {
            Pattern pattrn = entry.getKey();
            Integer base = entry.getValue();
            Matcher m = pattrn.matcher(value);
            if (m.matches()) {
                int dec = Integer.parseInt(m.group("value"), base);
                return String.valueOf(dec);
            }
        }
        return null;
    }
}
