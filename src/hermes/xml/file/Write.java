/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hermes.xml.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author David
 */
public class Write {

    public static void marshal(Object objectToWrite, Class className, File selectedFile) throws IOException, JAXBException {
        JAXBContext context;
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(selectedFile));
        context = JAXBContext.newInstance(className);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(objectToWrite, writer);
        writer.close();
    }
}
