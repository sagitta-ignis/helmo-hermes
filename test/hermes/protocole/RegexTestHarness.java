package hermes.protocole;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTestHarness {

    public static void main(String[] args){
        /*
        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        */
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.print("%nEnter your regex: ");
            Pattern pattern = 
            Pattern.compile(scanner.nextLine());

            System.out.print("Enter input string to search: ");
            Matcher matcher = 
            pattern.matcher(scanner.nextLine());

            boolean found = false;
            while (matcher.find()) {
                System.out.printf("I found the text" +
                    " \"%s\" starting at " +
                    "index %d and ending at index %d.%n",
                    matcher.group(),
                    matcher.start(),
                    matcher.end());
                found = true;
            }
            if(!found){
                System.out.printf("No match found.%n");
            }
        }
    }
}
