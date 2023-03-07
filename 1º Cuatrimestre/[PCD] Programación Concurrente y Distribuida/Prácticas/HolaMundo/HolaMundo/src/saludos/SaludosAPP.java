/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saludos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class SaludosAPP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Saluda s = new Saluda(2);
        
        try {
            s.saludar("Hola Mundo con clase");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("Fin de la aplicacion");
        
}
    
}
