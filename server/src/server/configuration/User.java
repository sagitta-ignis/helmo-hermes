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
@XmlRootElement(name = "user")
public class User {


    
    @XmlElement(name = "password", required = true)
    private String password;

    public User(){        
    }
    
    public User(String password){
        this.password = password;
    }
        
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}
