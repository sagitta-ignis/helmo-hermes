/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.com;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "server-config")
public class Configuration {

    @XmlElement(name = "server-port", required = true)
    private int port;
    @XmlElement(name = "user-filename", required = true)
    private String userFileName;
    @XmlElement(name = "sendingthread-sleepseconds", required = true)
    private int threadSleepSeconds;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setUserFileName(String userFileName) {
        this.userFileName = userFileName;
    }

    public String getUserFileName() {
        return userFileName;
    }

    public void setThreadSleepSeconds(int threadSleepSeconds) {
        this.threadSleepSeconds = threadSleepSeconds;
    }

    public int getThreadSleepSeconds() {
        return threadSleepSeconds;
    }
}
