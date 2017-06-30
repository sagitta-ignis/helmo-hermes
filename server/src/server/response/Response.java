/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.response;

/**
 *
 * @author David
 */
public class Response {
   
    private final String id;
    private final String message;
    
    public Response(String id, String message){
        this.id = id;
        this.message = message;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    
    
}
