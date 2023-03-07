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
    private int capacidad;
    int numelementos;
    Object[] datos;

    public PilaLenta(int capacidad) {

        this.capacidad = capacidad;
        numelementos = 0;
        cima = 0;

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
     * @throws Exception Si la pila está llena lanza un mensaje de error.
     */
    @Override
    public synchronized void Apila(Object elemento) throws Exception {

        //try {
            if (!pilallena()) {

                sleep(100);
                datos[cima] = elemento;
                sleep(100);
                numelementos++;
                sleep(100);
                cima++;
            }
            else{
                throw new Exception("PILA LLENA");
            }
        //} catch (Exception e) {
            //System.out.println(e.getMessage());
 //       }

    }

    /**
     * Devuelve el objeto que se encuentre en la cima de la pila y lo elimina.
     *
     * @return Objeto que se encuentra en la cima de la pila.
     */
    @Override
    public synchronized Object Desapila() throws Exception {
        Object elemento = null;

 //       try {
            if (!pilavacia()) {

                sleep(100);
                elemento = new Object();
                elemento = datos[cima - 1];
                sleep(100);
                cima--;
                sleep(100);
                numelementos--;

            }
            else{
                throw new Exception("PILA VACIA");
            }
 //       } catch (InterruptedException e) {
 //           System.out.println(e.getMessage());
 //       }

        return elemento;
    }

    /**
     * Devuelve el elemento que se encuentre en la cima de la pila sin
     * eliminarlo.
     *
     * @return Objeto que se encuentra en la cima de la pila.
     */
    @Override
    public Object Primero() {
        Object elemento = null;

        try {
            if (!pilavacia()) {
                elemento = datos[cima - 1];
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

//    public static void main(String[] args) {
//        PilaLenta p = new PilaLenta(5);
//
//        try {
//            for (int i = 0; i < 5; i++) {
//                p.Apila(i + 1);
//            }
//
//            System.out.println("Primero: " + p.Desapila());
//            System.out.println("Primero: " + p.Desapila());
//            System.out.println("Primero: " + p.Desapila());
//            System.out.println("Primero: " + p.Desapila());
//            System.out.println("Primero: " + p.Desapila());
//            System.out.println("Primero: " + p.Desapila());
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
}
