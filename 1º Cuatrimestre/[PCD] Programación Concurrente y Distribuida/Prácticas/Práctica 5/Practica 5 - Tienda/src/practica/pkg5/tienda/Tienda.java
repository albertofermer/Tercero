package practica.pkg5.tienda;

import javax.swing.ImageIcon;

/**
 *
 * @author Alberto Fernández
 */
public class Tienda {

    private int personasEsperando;
    private boolean vendedorLibre;
    private boolean tecnicoLibre;
    private CanvasTienda lienzo;
    private long idClienteActual;

    public Tienda(CanvasTienda c) {
        lienzo = c;
        vendedorLibre = true;
        tecnicoLibre = true;
        personasEsperando = 0;
    }

    public synchronized char EntraComprar(long id, ImageIcon imagen) throws InterruptedException {
        personasEsperando++;
        lienzo.pintaEspera(id, imagen);
        char quienAtiende; // v: vendedor t: tecnico
        while (!vendedorLibre && (personasEsperando <= 2 || !tecnicoLibre)) {
            System.out.println("El cliente " + id + " está esperando...");
            wait();
        }
        
        lienzo.borraEspera(id, imagen);
        if (personasEsperando <= 2) {
            lienzo.pintaComprar(id, imagen);
            vendedorLibre = false;
            quienAtiende = 'v';
        } else {
            System.out.println("El cliente " + id + " entra a comprar con el técnico");
            lienzo.pintaReparar(id, imagen);
            tecnicoLibre = false;
            quienAtiende = 't';
        }
        
        personasEsperando--;

        return quienAtiende;
    }

    public synchronized void SaleComprar(char quienAtiende, long id, ImageIcon imagen) {

        if (quienAtiende == 'v') {
            lienzo.borraComprar(id, imagen);
            vendedorLibre = true;
        } else {
            lienzo.borraReparar(id, imagen);
            tecnicoLibre = true;
        }

        notifyAll();
    }

    public synchronized void EntraReparar(long id, ImageIcon imagen) throws InterruptedException {
        lienzo.pintaEspera(id, imagen);
        while (!tecnicoLibre || personasEsperando > 2) {
            System.out.println("El cliente " + id + " está esperando...");
            wait();
        }
        lienzo.borraEspera(id, imagen);
        lienzo.pintaReparar(id, imagen);
        tecnicoLibre = false;
    }

    public synchronized void SaleReparar(long id, ImageIcon imagen) {
        lienzo.borraReparar(id, imagen);
        tecnicoLibre = true;
        notifyAll();
    }

}
