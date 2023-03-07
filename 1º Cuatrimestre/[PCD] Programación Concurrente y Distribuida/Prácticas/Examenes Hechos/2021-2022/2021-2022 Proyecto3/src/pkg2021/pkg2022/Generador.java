package pkg2021.pkg2022;

import Remoto.Tunel;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Contendrá el método main y será el quien comience la ejecución de lprograma.
 * Debe lanzar 10 vehículos a intervalos de tiempo de entre 1 a 2 segundos. El
 * tipo de vehículo generado será aleatorio. La ejecución finalziará cuando
 * todos los hilos hayan terminado.
 *
 * @author Alberto Fernández
 */
public class Generador {

    static int NUM_VEHICULOS = 10;
    static int TIEMPO_MIN = 1000;
    static int TIEMPO_MAX = 2000;

    public static void main(String[] args) throws RemoteException, IOException, InterruptedException {

//        Random r = new Random();
//        int NUM_COCHES = 0;
//        int NUM_FURGOS = 0;
//        ArrayList<Thread> coches = new ArrayList();
//        ArrayList<Thread> furgos = new ArrayList();
//        Tunel tunel = new Tunel();
//        for (int i = 0; i < NUM_VEHICULOS; i++) {
//
//            if (r.nextInt() % 2 == 0) {
//                coches.add(new Coche(tunel));
//                coches.get(NUM_COCHES).start();
//                NUM_COCHES++;
//            } else {
//                furgos.add(new Thread(new Furgo(tunel)));
//                furgos.get(NUM_FURGOS).start();
//                NUM_FURGOS++;
//            }
//            sleep((abs(r.nextInt()) % (TIEMPO_MAX-TIEMPO_MIN)+TIEMPO_MIN));
//        }
//
//        for (int i = 0; i < NUM_COCHES; i++) {
//            try {
//                coches.get(i).join();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        for (int i = 0; i < NUM_FURGOS; i++) {
//            try {
//                furgos.get(i).join();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        Registry reg = LocateRegistry.createRegistry(2022);
        reg.rebind("tunelRemoto", new Tunel());
        System.out.println("Servidor ejecutándose...");
        System.in.read();
        System.out.println("Saliendo del servidor ...");
        System.exit(0);
        System.out.println("Termina la ejecucion del programa");
        sleep(1000);
        System.exit(0);

    }
}
