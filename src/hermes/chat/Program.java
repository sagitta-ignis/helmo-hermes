package hermes.chat;

import hermes.chat.controleur.Authentifier;
import hermes.client.Client;
import hermes.client.Encodeur;
import hermes.client.Utilisateurs;
import hermes.client.exception.NotConnectedException;
import hermes.client.exception.UnopenableExecption;
import hermes.client.exception.UnreachableServerExeception;
import hermes.chat.vue.Login;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ouvrir the template in the editor.
 */
/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Program {

    public void startConsole() {
        Scanner input = new Scanner(System.in);
        Utilisateurs u = new Utilisateurs();
        Client c = new Client(u);
        try {
            c.connect("127.0.0.1", 12345);
            System.out.println("-- client a démarré");
            System.out.println("Entrer un nom : ");
            String nom = input.nextLine();
            if (c.login(nom, null)) {
                Encodeur enc = new Encodeur(c, System.in);
                Observer o = new Observer() {
                    @Override
                    public void update(Observable o, Object o1) {
                        if (o instanceof Client) {
                            Client client = (Client)o;
                            String text = lire(client.getEtat());
                            System.out.println(text);
                        }
                    }

                    private String lire(int etat) {
                        return "";
                    }
                };
                c.addObserver(o);
                enc.start();
                c.ouvrir();
            }
            c.fermer();
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Authentifier logger = new Authentifier();
            }
        });
    }

    public static void main(String[] args) {
        Program p = new Program();
        p.startInterface();
    }
}
