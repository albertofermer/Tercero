/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase2;

/**
 *
 * @author usuario
 */
public class Recurso { //Recurso por el que van a correr varios hilos.
    
    private int suma = 0;
    
    public synchronized void incrementa(){ //Con la palabra synchronized hace exclusión mutua.
        suma++; //Sección crítica. No pueden hacerse a la vez.
    }
    public void versuma(){
        System.out.println("Suma: " + suma);
    }
    
}
