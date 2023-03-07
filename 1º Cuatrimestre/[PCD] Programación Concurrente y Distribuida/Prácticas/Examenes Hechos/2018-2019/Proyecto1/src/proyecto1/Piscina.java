package proyecto1;

/**
 * Mantendrá el estado de ocupación de la piscina e implementará los métodos:
 * Capacidad: 5 personas Material: 6 aletas y 5 palas
 *
 * @author Alberto Fernández
 */
public class Piscina {

    private int personasDentro;
    private int cantidadAletas;
    private int cantidadPalas;

    public Piscina() {
        personasDentro = 0;
        cantidadAletas = 6;
        cantidadPalas = 5;
    }

    public synchronized void entraPiscina(int id) throws InterruptedException {
        while (personasDentro == 5) {
            System.out.println("Nadador " + id + " está esperando");
            wait();
        }
        System.out.println("Nadador " + id + " entra en la piscina");
        personasDentro++;

    }

    public synchronized void salePiscina(int id) {
        System.out.println("Nadador " + id + " sale de la piscina");
        personasDentro--;
        notifyAll();
    }

    public synchronized void cogeMaterial(int id) throws InterruptedException {

        while (cantidadAletas < 2 || cantidadPalas < 2) {
            System.out.println("No hay recursos suficientes para el nadador " + id);
            wait();
        }
        System.out.println("El nadador " + id + " coge el material");
        cantidadAletas -= 2;
        cantidadPalas -= 2;
    }

    public synchronized void sueltaMaterial(int id) {
        System.out.println("El nadador " + id + " deja el material");
        cantidadAletas += 2;
        cantidadPalas += 2;
        notifyAll();
    }
}
