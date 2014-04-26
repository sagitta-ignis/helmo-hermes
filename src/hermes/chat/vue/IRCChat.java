/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.AbstractChat;
import hermes.chat.controleur.Chatter;
import hermes.client.Client;
import hermes.client.Utilisateurs;
import javax.swing.DefaultListModel;
import pattern.command.CommandArgument;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class IRCChat extends AbstractChat {

    private final ChatGUI chat;
    private final DefaultListModel<CellUser> utilisateurs;

    public IRCChat(Chatter chatter) {
        utilisateurs = new DefaultListModel<>();
        chat = new ChatGUI(chatter, utilisateurs);
        initStatus();
    }

    private void initStatus() {
        ajouterStatus(String.valueOf(Client.STYPING), new CommandArgument() {
            @Override
            public void execute() {
                String user = (String) args[0];
                String digit = (String) args[1];
                sTyping(user, digit.equals("1"));
            }
        });
    }

    @Override
    public void setTyping(boolean typing) {
        super.setTyping(typing);
        chat.setTyping(typing);
    }

    @Override
    public boolean isTyping() {
        return chat.isTyping();
    }

    public DefaultListModel getUtilisateurs() {
        return utilisateurs;
    }

    public void initUtilisateurs(Object users[]) {
        for (Object user : users) {
            ajouterUtilisateur(user);
        }
    }

    private void ajouterUtilisateur(Object user) {
        utilisateurs.addElement(new CellUser(user.toString()));
    }
    
    private int chercherUtilisateur(String user) {
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getUsername().equals(user)) {
                return i;
            }
        }
        return -1;
    }

    private void retirerUtilisateur(String user) {
        int index = chercherUtilisateur(user);
        if(index != -1) {
            utilisateurs.remove(index);
        }
    }
    
    public void sTyping(String user, boolean typing) {
        int index = chercherUtilisateur(user);
        CellUser cell = utilisateurs.remove(index);
        cell.toggle(typing);
        utilisateurs.add(index, cell);
    }

    @Override
    public void sUsers(Utilisateurs users) {
        utilisateurs.clear();
        for (Object user : users.toArray()) {
            ajouterUtilisateur(user);
        }
    }

    @Override
    public void join(String user) {
        ajouterUtilisateur(user);
        super.join(user);
    }

    @Override
    public void leave(String user) {
        retirerUtilisateur(user);
        super.leave(user);
    }

    @Override
    public void afficher(String texte) {
        chat.afficher(texte);
    }

    @Override
    public void avertir(String titre, String message) {
        chat.avertir(titre, message);
    }

    public void setVisible(boolean visible) {
        chat.setVisible(visible);
    }
}
