
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerControleur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class Starter {

    public static void main(String[] args) {
        try {
            ServerControleur s = new ServerControleur();

            System.out.println("-- serveur a démarré");
            s.lancerServeur();
            
            s.fermer();
            System.out.println("-- serveur fermé");
            System.exit(0);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerControleur.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
}
