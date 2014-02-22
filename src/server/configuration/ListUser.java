/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.configuration;

import server.configuration.User;
import java.util.List;
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
    private List<User> users;
    
    public List<User> getUsers() {
        return users;
    }
 
    public void setUsers(List<User> books) {
        this.users = books;
    }
}
