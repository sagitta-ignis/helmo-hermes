/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.chat.vue;

import hermes.chat.Chat;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class Conversation extends JScrollPane {

    private final JTextArea conversation;
    private boolean publique;

    public Conversation(String nom, boolean publique) {
        setName(nom);
        conversation = new JTextArea();
        this.publique = publique;
        setViewportView(conversation);
        //Scroll automatiquement en bas du textarea
        DefaultCaret caret = (DefaultCaret) conversation.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public Conversation(String nom) {
        this(nom, true);
    }
    
    public void clear() {
        conversation.setText(null);
    }

    public void afficher(String user, String text) {
        StringBuilder line = new StringBuilder();
        if (!user.equals(Chat.SERVER)) {
            line.append(user).append(" : ");
        }
        line.append(text);
        conversation.append(line.toString());
        conversation.append("\n");
    }

    public boolean isPublic() {
        return publique;
    }

    public boolean isPrivate() {
        return !publique;
    }
}
