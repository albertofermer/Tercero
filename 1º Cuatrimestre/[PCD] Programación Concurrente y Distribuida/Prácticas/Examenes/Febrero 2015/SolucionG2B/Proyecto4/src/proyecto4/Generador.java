/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

/**
 *
 * @author pedro
 */
public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        Thread cl[] = new Thread[10];
        Sala s = new Sala();

        Registry registro = LocateRegistry.createRegistry(2015);
        registro.rebind("salaremota", s);


        System.out.println("Lanzando pacientes .....");
        for (int i = 0; i < 10; i++) {
            if (rnd.nextInt(2) % 2 == 1) {
                cl[i] = new Concita(i, s);
            } else {
                cl[i] = new Thread(new Sincita(i, s));
            }
            cl[i].start();
            Thread.sleep((rnd.nextInt(2) + 1) * 1000);
        }
        for (int i = 0; i < 10; i++) {
            cl[i].join();
        }
        System.out.println("Todos los pacientes finalizados");

        System.out.println("Servidor sigue Funcionando ....");
        System.out.println("pulsa una tecla para finalizar");
        System.in.read();
        System.out.println("Saliendo del servidor ...");
        System.exit(0);
    }
}
