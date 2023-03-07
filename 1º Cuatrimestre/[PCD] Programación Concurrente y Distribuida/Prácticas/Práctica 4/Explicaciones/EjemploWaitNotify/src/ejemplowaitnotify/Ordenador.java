/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplowaitnotify;

/**
 *
 * @author usuario
 */
public class Ordenador {
    private int turno = 0;
    
    public synchronized void cogeturno(int id){
        
        while (turno != id) try { //En vez de un if es recomendable poner un while. 
            wait();  // Si se usa wait siempre hay que protegerlo con while().
            System.out.println("El hilo " + id + " comprueba el turno");
        } catch (InterruptedException ex) {}
    }
    
    public synchronized void pasaturno(){
        turno = (turno + 1) % 5;
        notifyAll();  //notify Despierta a uno aleatorio.  
        //Cuando despierto a todos, todos van a pasar por el while de forma ordenada. A todo el que no le toque se dormirá, pero si le toca saldrá el mensaje.
    } 
}
