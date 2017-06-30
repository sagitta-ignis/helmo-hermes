/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.xml.file;

import hermes.xml.Xml;
import hermes.xml.XmlImpl;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author David
 */
public class Read {

    public static Object UnmarshalConfig(InputStream url, Class className) throws Exception {
        JAXBContext context = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(url);
    }
}
