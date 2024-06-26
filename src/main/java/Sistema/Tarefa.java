/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistema;

/**
 *
 * @author Diogo
 */
public class Tarefa {
    
    private String request;
    private String response;
    private boolean ready;

    
    
    public Tarefa(String request){
        this.request = request;
        this.response = null;
        this.ready = false;
    }
    
    
    
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    public boolean isReady(){
        return this.ready;
    }
    
    public void setReady(boolean ready){
        this.ready = ready;
    }
    
}
