package proyecto1;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Alberto Fernández
 */
public class Generador {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Random r = new Random();
        Piscina p = new Piscina();
        ExecutorService threadpool = Executors.newFixedThreadPool(6);
        ArrayList< Future<Integer>> tiempo = new ArrayList();
        int tiempoTotal = 0;
        for (int i = 0; i < 10; i++) {
            tiempo.add(threadpool.submit(new Nadador(i, p)));
            sleep(r.nextInt() % (2000 - 1000) + 1000);
        }

        for (Future<Integer> tiempo1 : tiempo) {
            tiempoTotal += tiempo1.get();
        }
        System.out.println("tiempo Total: " + tiempoTotal + " milisegundos.");
        threadpool.shutdown();

    }
}
