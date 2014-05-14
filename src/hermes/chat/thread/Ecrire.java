/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
package hermes.chat.thread;

import hermes.chat.controleur.Chatter;
import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Ecrire extends Thread {

    private final Chatter chat;
    private final Scanner input;

    public Ecrire(Chatter chat, InputStream in) {
        this.chat = chat;
        input = new Scanner(in);
    }
    
    @Override
    public void run() {
        while (chat.getClient().canRun()) {
            String commandLine = input.nextLine();
            chat.getMessageHandler().traiter(commandLine);
        }
    }
}
