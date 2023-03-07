package pila;

import static java.lang.Math.abs;
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

    public static void main(String[] args) {

        int capacidad = 10;
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        r.setSeed(System.nanoTime());
        int elemento;
        // System.out.print("Introduce la capacidad de la pila: ");
        // capacidad = sc.nextInt();
        Pila p = new Pila(capacidad);
        try {
            for (int i = 0; i < capacidad; i++) {
                elemento = abs(r.nextInt() % 100);
                //System.out.println("Se ha generado el elemento: " + elemento);

                if (elemento % 2 == 0) {

                    System.out.println("Ha salido el número: " + elemento + "(par)");
                    p.Apila(elemento);
                    System.out.println((i + 1) + ") Se ha apilado el elemento: " + p.Primero());

                } else {
                    System.out.println("Ha salido el número: " + elemento + "(impar)");
                    System.out.println((i + 1) + ") Se ha desapilado el elemento: " + p.Desapila());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
