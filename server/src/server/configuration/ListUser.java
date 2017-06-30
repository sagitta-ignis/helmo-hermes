/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.configuration;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "list-users")
public class ListUser {
    
    @XmlElement(name = "user", required = true)   
    private Map<String,User> users;
    
    public Map<String,User> getUsers() {
        if(users == null){
            generateMap();
        }
        return users;
    }
 
    public void setUsers(Map<String,User> user) {
        this.users = user;
    }
    
    private void generateMap(){
        users = new HashMap<>();
    }
}
