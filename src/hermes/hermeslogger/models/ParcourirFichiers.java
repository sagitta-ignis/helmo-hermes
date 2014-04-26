/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.hermeslogger.models;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class ParcourirFichiers {
    
    public ArrayList<String> obtenirListeFichiers(){
        ArrayList<String> nomFichiers = new ArrayList<>();
        
        File temp = new File(Configuration.DOSSIER);
        File[] tab = temp.listFiles();
        
        for(int i=0; i<tab.length;i++){
            if(tab[i].getAbsoluteFile().getAbsolutePath().endsWith(".xml")){
                nomFichiers.add(tab[i].getName());
            }
        }
        
        return nomFichiers;
    }
}
