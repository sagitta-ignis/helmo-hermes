/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hermes.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBException;
import hermes.xml.file.Read;
import hermes.xml.file.Write;

/**
 *
 * @author David
 */
public class XmlImpl implements Xml{

    @Override
    public void write(Object objectToWrite,Class className, File selectedFile) throws IOException, JAXBException {
        Write.marshal(objectToWrite, className, selectedFile);
    }

    @Override
    public Object read(InputStream url, Class className) throws Exception {
        return Read.UnmarshalConfig(url, className);
    }
    
}
