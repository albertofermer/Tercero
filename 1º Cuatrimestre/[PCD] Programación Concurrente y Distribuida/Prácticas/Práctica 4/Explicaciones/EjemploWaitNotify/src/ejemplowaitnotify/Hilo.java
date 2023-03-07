package ejemplowaitnotify;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author usuario
 */
public class Hilo extends Thread{
    private final int id;
    private Ordenador o;
    public Hilo(int id, Ordenador ord){
        this.id = id;
        o = ord;
    }
    
    @Override
    public void run(){ 
        
        for (int i = 0; i < 5; i++) {
            o.cogeturno(id);
            System.out.println("Soy el hilo " + id);
            o.pasaturno();
        }
        
        if (id == 2) {
            o.cogeturno(id);
            System.out.println("Soy el hilo " + id);
            o.pasaturno();
        }
        
    }
    
    
    
    
}
