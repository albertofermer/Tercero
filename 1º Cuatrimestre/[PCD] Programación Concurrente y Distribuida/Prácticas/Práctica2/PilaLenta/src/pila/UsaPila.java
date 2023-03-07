package pila;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * <p>
 * Aplicación que utiliza la clase Pila.
 * </p>
 *
 * <p>
 * Si genera un número par se añade a la pila.</p>
 * <p>
 * Si genera un número impar se desapila el elemento cima de la pila.</p>
 *
 * <p>
 * Si la pila está llena y se añade un elemento más, generará una excepción.</p>
 * <p>
 * Si la pila está vacía y se desapila un elemento más, generará una
 * excepción.</p>
 *
 *
 * @author Alberto Fernández
 * @version 16.10.21
 *
 */
public class UsaPila {

    public static void main(String[] args) throws InterruptedException {

        int capacidad = 10;

        PilaLenta pila = new PilaLenta(capacidad);

        productor p1 = new productor(pila); //extends Thread
        productor p2 = new productor(pila);

        Thread consumidor1 = new Thread(new consumidor(pila)); // implements Runnable
        Thread consumidor2 = new Thread(new consumidor(pila));

        p1.start();
        p2.start();
        
        //sleep(100);
        
        consumidor1.start();
        consumidor2.start();

        consumidor1.join();
        consumidor2.join();

        p1.join();
        p2.join();

    }
}
