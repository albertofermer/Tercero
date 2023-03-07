package proyecto1;

/**
 *
 * @author Alberto Fernández
 */
public class Mina {

    public static void main(String[] args) throws InterruptedException {
        Monton m = new Monton();
        Thread cargadoraP1 = new Pequenya(m, 1);
        Thread cargadoraP2 = new Pequenya(m, 2);
        Thread cargadoraG1 = new Thread(new Grande(m, 1));
        Thread cinta = new Cinta(m);

        cinta.start();
        cargadoraP1.start();
        cargadoraP2.start();
        cargadoraG1.start();

        cargadoraG1.join();
        cargadoraP1.join();
        cargadoraP2.join();

        cinta.interrupt();

    }
}
