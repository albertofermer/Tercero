/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto4;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here

        int ntt = 0;
        ExecutorService thp = Executors.newFixedThreadPool(2);
        ArrayList<Future<Integer>> result = new ArrayList<>();

        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        Lavadero lv = new Lavadero();
        Thread cl[] = new Thread[10];
        System.out.println("Lanzando vehiculos .....");
        for (int i = 0; i < 10; i++) {
            if (rnd.nextInt(2) % 2 == 1) {
                cl[ntt] = new Todoterreno(i, lv);
                cl[ntt].start();
                ntt++;

            } else {
                result.add(thp.submit(new Coche(i, lv)));
            }
            Thread.sleep((rnd.nextInt(3) + 1) * 1000);
        }
        for (int i = 0; i < ntt; i++) {
            cl[i].join();
        }
        thp.shutdown();
        int totalc = 0, totaltt = 0;
        for (int i = 0; i < result.size(); i++) {
            int res = 0;
            try {
                res = result.get(i).get();
            } catch (ExecutionException ex) {
                Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (res == 1) {
                totalc++;
            } else {
                totaltt++;
            }
        }
        System.out.println("COCHES: En el de coches han pasado " + totalc + " y en el de tt " + totaltt);

        System.out.println("Todos los vehiculos finalizados");
    }
}
