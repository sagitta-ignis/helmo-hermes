package hermes;

import hermes.client.controleur.Logger;
import hermes.client.Client;
import hermes.client.ClientListener;
import hermes.client.Encodeur;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnopenableExecption;
import hermes.client.exception.UnreachableServerExeception;
import hermes.client.vue.Login;
import java.util.Scanner;
import java.util.logging.Level;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Program {

    public void startConsole() {
        Scanner input = new Scanner(System.in);
        Client c = new Client();
        try {
            c.connect("127.0.0.1", 12345);
            System.out.println("-- client a démarré");
            System.out.println("Entrer un nom : ");
            String nom = input.nextLine();
            if (c.login(nom, null)) {
                Encodeur enc = new Encodeur(c, System.in);
                ClientListener cl = new ClientListener() {
                    @Override
                    public void lire(String text) {
                        System.out.println(text);
                    }
                };
                c.addListener(cl);
                enc.start();
                c.open();
            }
            c.close();
        } catch (UnreachableServerExeception ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[error] impossible de se connecter : création du socket client a échoué");
        } catch (NotConnectedException ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[error] impossible de s'authentifier : client n'est pas connecté");
        } catch (UnopenableExecption ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[error] impossible de continuer : client n'est pas connecté ou authentifié");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[error] client s'est mal fermé");
        } finally {
            System.exit(0);
        }
    }

    public void startInterface() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Logger logger = new Logger();
            }
        });
    }

    public static void main(String[] args) {
        Program p = new Program();
        p.startInterface();
    }
}
