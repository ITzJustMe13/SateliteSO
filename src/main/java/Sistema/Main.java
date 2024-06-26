/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistema;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diogo
 */
public class Main {
    
    public static void main(String[] args){
        Middleware mw = new Middleware(new Kernel());
        try {
            mw.start();
        } catch (IOException ex) {
           System.out.println("Servidor terminou com IOException");
        }
        
    }
    
}
