/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.status;

import hermes.client.utilisateurs.Utilisateurs;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public interface UtilisateursStatusHandler {

    void sUsers(Utilisateurs users);

    void join(String user);

    void leave(String user);
}
