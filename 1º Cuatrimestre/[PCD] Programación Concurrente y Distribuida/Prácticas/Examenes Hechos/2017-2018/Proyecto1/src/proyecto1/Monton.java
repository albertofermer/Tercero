package proyecto1;

/**
 * Gestiona la cantidad de mineral que hay en el montón. Inicialmente tiene 4
 * Tm.
 *
 * @author Alberto Fernández
 */
public class Monton {

    int cantidad;

    public Monton() {
        cantidad = 4000;
    }

    public synchronized void CargaPoco() throws InterruptedException {
        while (cantidad < 1000) {
            System.out.println("No ha suficiente material para que la cargadora pequeña funcione.");
            wait();
        }

        cantidad -= 1000;
    }

    public synchronized void CargaMucho() throws InterruptedException {
        while (cantidad < 2000) {
            System.out.println("No hay suficiente material para que la cargadora grande funcione.");
            wait();
        }

        cantidad -= 2000;
    }

    public synchronized void Rellena(int cantidad) {
        this.cantidad += cantidad;
        System.out.println("Se ha aumentado la cantidad del monton en " + cantidad);
        System.out.println("Actualmente hay: " + this.cantidad);

        notifyAll();
    }
}
