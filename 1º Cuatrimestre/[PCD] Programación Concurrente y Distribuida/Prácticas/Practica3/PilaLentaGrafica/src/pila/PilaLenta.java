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
    public synchronized void Apila(Object elemento) throws InterruptedException, Exception  {
        if (!pilallena()) {

            sleep(100);
            datos[cima] = elemento;
            sleep(100);
            numelementos++;
            sleep(100);
            cima++;
            
            
            
            lienzo.representa(datos, cima, numelementos);
            if (numelementos == capacidad) lienzo.avisa("PILA LLENA");
        } else {            
            //System.out.println("PILA LLENA");
            lienzo.avisa("PILA LLENA");
            
            throw new Exception("PILA LLENA");
        }

    }

    /**
     * Devuelve el objeto que se encuentre en la cima de la pila y lo elimina.
     *
     * @return Objeto que se encuentra en la cima de la pila.
     * @throws java.lang.InterruptedException
     * @throws Exception Si la pila está vacía lanza un mensaje de error.
     */
    @Override
    public synchronized Object Desapila() throws InterruptedException, Exception  {
        Object elemento = null;

        
        if (!pilavacia()) {
           
            sleep(100);
            elemento = new Object();
            elemento = datos[cima - 1];
            sleep(100);
            cima--;
            sleep(100);
            numelementos--;
            
            
            
            lienzo.representa(datos, cima, numelementos);
            if(numelementos == 0) lienzo.avisa("PILA VACÍA");
        } else {
            //System.out.println("PILA VACIA");
            lienzo.avisa("PILA VACÍA");
            throw new Exception("PILA VACÍA");
            
        }

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
    public Object Primero() throws Exception  {
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
     * @return Devuelve si la pila  está llena o no.
     */
    private boolean pilallena() {
        return (numelementos == capacidad);
    }


}
