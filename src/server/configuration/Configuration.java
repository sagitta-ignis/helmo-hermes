/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.configuration;

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
    private double threadSleepSeconds;
    
    @XmlElement(name = "defaultChannel", required = true)
    private String defaultChannel;

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

    public void setThreadSleepSeconds(double threadSleepSeconds) {
        this.threadSleepSeconds = threadSleepSeconds;
    }

    public double getThreadSleepSeconds() {
        return threadSleepSeconds;
    }

    /**
     * @return the defaultChannel
     */
    public String getDefaultChannel() {
        return defaultChannel;
    }

    /**
     * @param defaultChannel the defaultChannel to set
     */
    public void setDefaultChannel(String defaultChannel) {
        this.defaultChannel = defaultChannel;
    }
    
    
}
