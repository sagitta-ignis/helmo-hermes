
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controlleur.ServeurControlleur;

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

        ServeurControlleur s = new ServeurControlleur();

        System.out.println(">> Serveur a démarré");
        s.lancerServeur();

    }
}
