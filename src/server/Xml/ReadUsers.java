/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.Xml;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import server.com.ListUser;

/**
 *
 * @author David
 */
public class ReadUsers {    
    public static ListUser UnmarshalConfig(InputStream _source) throws Exception {

        try {
            JAXBContext context = JAXBContext.newInstance(ListUser.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ListUser) unmarshaller.unmarshal(_source);

        } catch (JAXBException e) {
            throw new Exception("[ReadXML][UnmarshalConfig] Error during unmarshalling: " + e.getMessage());
        }
    }
}
