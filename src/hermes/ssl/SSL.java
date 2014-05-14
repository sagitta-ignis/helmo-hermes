/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author d120041
 */
public class SSL {
    private static String password = "sp92:cL3;QEn";
    public static boolean setPassword(String password) {
        if (password == null) return false;
        SSL.password = password;
        return true;
    }
    private static String filepath = "./keystore.jks";
    public static boolean setFilePath(String filepath) {
        if (filepath == null) return false;
        SSL.filepath = filepath;
        return true;
    }
    
    private static SSLContext getSSL() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(filepath), password.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, password.toCharArray());
            
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            return ctx;
        } catch (KeyStoreException ex) {
            Logger.getLogger(SSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(SSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(SSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(SSL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ServerSocketFactory getServerSocketFactory() {
        SSLContext context = getSSL();
        return context.getServerSocketFactory();
    }
    
    public static SocketFactory getSocketFactory() {
        SSLContext context = getSSL();
        return context.getSocketFactory();
    }
}
