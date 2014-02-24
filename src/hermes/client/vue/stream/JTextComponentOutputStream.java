/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.vue.stream;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Menini Thomas (d120041) <t.menini@student.helmo.be>
 */
public class JTextComponentOutputStream extends OutputStream {

    private final JTextComponent textComponent;
    private final StringBuilder sb = new StringBuilder();

    public JTextComponentOutputStream(final JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

    @Override
    public void flush() {
        final String text = sb.toString() + "\n";
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textComponent.setText(text);
            }
        });
        sb.setLength(0);
    }

    @Override
    public void close() {
    }

    @Override
    public void write(int b) throws IOException {
        if (b == '\r') {
            return;
        }
        if (b == '\n') {
            flush();
            return;
        }
        sb.append((char) b);
    }
}
