/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3;

import java.util.Random;

/**
 *
 * @author pedro
 */
public class Proveedor implements Runnable{
    private int id;
    Almacen alm;
    
    public Proveedor(int i, Almacen al){
        id=i;
        alm=al;
    }
    @Override
    public void run(){
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            int cantidad=rnd.nextInt(4)+2;
            System.out.println(" .......................... Soy el Proveedor "+id+" y pongo "+ cantidad);
            alm.Deposita(id,cantidad);
            System.out.println("........................... Soy el Proveedor "+id+" marchandome");
    }

}
