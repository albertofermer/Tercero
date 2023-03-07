package pila;

import static java.lang.Thread.sleep;

/**
 *
 * Implementación de una Pila utilizando un vector de Objetos. inicialmente el
 * vector estará vacío y la cima apuntará a la primera posición del vector.
 *
 *
 * <p>
 * Cuando se inserta un elemento, si la pila no está llena, se colocará en la
 * posición de la cima y se incrementará el valor del puntero.
 * </p>
 *
 * <p>
 * Al extraer un elemento, si la pila no está vacía, se tomará el elemento
 * anterior a la cima y decrementará el valor del puntero.
 * </p>
 *
 * @author Alberto Fernández Merchán
 * @version 15.10.21
 *
 */
public class PilaLenta implements IPila {

    private int cima;
    private final int capacidad;
    private int numelementos;
    private final Object[] datos;
    private final CanvasPila lienzo;

    public PilaLenta(int capacidad, CanvasPila elcanvas) {

        this.capacidad = capacidad;
        numelementos = 0;
        cima = 0;

        lienzo = elcanvas;

        datos = new Object[capacidad];

    }

    /**
     * Devuelve el número de elementos que hay en la pila.
     *
     * @return Número de elementos que hay en la pila.
     */
    @Override
    public int GetNum() {
        return numelementos;
    }

    /**
     * Añade el elemento a la pila si no está llena.
     *
     * @param elemento Es el objeto que almacena en la pila
     * @throws java.lang.InterruptedException
     * @throws Exception Si la pila está llena lanza un mensaje de error.
     */
    @Override
    public synchronized void Apila(Object elemento) throws InterruptedException, Exception {
        int intentos = 0;
        while (pilallena()) {
            System.out.println("Productor [" + Thread.currentThread().getId() + "] esperando");
            intentos++;
            System.out.println("Productor [" + Thread.currentThread().getId() + "] intentos: " + intentos);
            wait();
            if (intentos >= 3) {
                //lienzo.avisa("PILA LLENA"); // cambiar a otro mensaje
                throw new Exception("Productor [" + Thread.currentThread().getId() + "] ha abandonado"); // cambiar a otro mensaje
            }
        }

        sleep(100);
        datos[cima] = elemento;
        System.out.println("productor [" + Thread.currentThread().getId() + "] apila: " + elemento);
        sleep(100);
        numelementos++;
        sleep(100);
        cima++;
        lienzo.representa(datos, cima, numelementos);
        if (numelementos == capacidad) {
            lienzo.avisa("PILA LLENA");
        }
        notifyAll();

    }

    /**
     * Devuelve el objeto que se encuentre en la cima de la pila y lo elimina.
     *
     * @return Objeto que se encuentra en la cima de la pila.
     * @throws java.lang.InterruptedException
     * @throws Exception Si la pila está vacía lanza un mensaje de error.
     */
    @Override
    public synchronized Object Desapila() throws InterruptedException, Exception {
        Object elemento = null;
        int intentos = 0;
        while (pilavacia()) {
            System.out.println("Consumidor [" + Thread.currentThread().getId() + "] esperando...");
            intentos++;
            System.out.println("Consumidor [" + Thread.currentThread().getId() + "] intentos: " + intentos);
            wait();
            if (intentos == 3) {
                //lienzo.avisa("PILA VACIA"); // cambiar a otro mensaje
                throw new Exception("Consumidor [" + Thread.currentThread().getId() + "] ha abandonado"); // cambiar a otro mensaje               
            }
        }

        sleep(100);
        elemento = new Object();
        elemento = datos[cima - 1];
        System.out.println("Consumidor [" + Thread.currentThread().getId() + "] desapila: " + elemento);
        sleep(100);
        cima--;
        sleep(100);
        numelementos--;

        lienzo.representa(datos, cima, numelementos);
        if (numelementos == 0) {
            lienzo.avisa("PILA VACÍA");
        }
        notifyAll();

        return elemento;
    }

    /**
     * Devuelve el elemento que se encuentre en la cima de la pila sin
     * eliminarlo.
     *
     * @return Objeto que se encuentra en la cima de la pila.
     * @throws Exception Si la pila está vacía lanza un mensaje de error.
     */
    @Override
    public Object Primero() throws Exception {
        Object elemento = null;

        if (!pilavacia()) {

            elemento = datos[cima - 1];
        } else {
            lienzo.avisa("PILA VACÍA");
            throw new Exception("PILA VACÍA");
        }

        return elemento;
    }

    /**
     * Este método sirve para saber si la pila está vacía.
     *
     * @return Devuelve si la pila está vacía o no.
     */
    private boolean pilavacia() {
        return (numelementos == 0);
    }

    /**
     * Este método sirve para saber si la pila está llena.
     *
     * @return Devuelve si la pila está llena o no.
     */
    private boolean pilallena() {
        return (numelementos == capacidad);
    }

}
