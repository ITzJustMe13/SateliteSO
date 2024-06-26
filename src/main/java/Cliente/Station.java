/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diogo
 */
public class Station implements Runnable{

    private int porto;
    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;
    
    public Station(int porto){
        this.porto = porto;
    }
    
     public void doConnections() throws IOException{
        InetAddress endereco = InetAddress.getLocalHost();
        this.socket = new Socket(endereco, porto);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }
    
    @Override
    public void run() {
        try{
            doConnections();
            while(true){
               //simular envio de multiplas mensagens
               
               try {
               requestTemperature();
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Station.class.getName()).log(Level.SEVERE, null, ex);
                }
                requestCoords();
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Station.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                requestCount();
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Station.class.getName()).log(Level.SEVERE, null, ex);
                }
                        
 
                
               }catch(IOException e){
                   System.out.println("Erro na conexão com o servidor (Middleware)");
               }
                
            }
            
        }catch(IOException e){
            
        }
    }
    
    
    
    public static void main(String args[]){
        
        Station sat = new Station(4001);
        new Thread(sat).start();
    }

    
    
    private String sendMessages(String msg) throws IOException {
        
        System.out.println("A enviar a mensagem: "+ msg+"...");
        out.write(msg+"\n");
        out.flush();
        
        
        while(!in.ready()){
            try {
               Thread.currentThread().sleep(50);
           } catch (InterruptedException ex) {}
        }
       
        
        String response = in.readLine();
        System.out.println("Server response: "+response);
        return response;
 
    }


    public String requestTemperature() throws IOException{
        return sendMessages("getTemp");
    }
    
    
    public String requestCoords() throws IOException{
        return sendMessages("getCoords");
    }
    
    public String requestCount() throws IOException{
        return sendMessages("count");
    }
    
    public String requestName() throws IOException {
        return sendMessages("getName");
    }
    
    public String requestSpeed() throws IOException {
        return sendMessages("getSpeed");
    }
    
    public String requestAll() throws IOException {
        return sendMessages("getAll");
    }
    
}
