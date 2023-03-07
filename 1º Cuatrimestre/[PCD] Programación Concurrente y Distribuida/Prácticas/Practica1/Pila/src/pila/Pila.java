package pila;

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
public class Pila implements IPila {

    private int cima;
    private int capacidad;
    int numelementos;
    Object[] datos;

    public Pila(int capacidad) {

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
    public void Apila(Object elemento) throws Exception {
        if (!pilallena()) {

            datos[cima] = elemento;
            numelementos++;
            cima++;
        } else {
            throw new Exception("ERROR: Pila Llena");
        }

    }

    /**
     * Devuelve el objeto que se encuentre en la cima de la pila y lo elimina.
     *
     * @return Objeto que se encuentra en la cima de la pila.
     * @throws Exception Si la pila está vacía lanza un mensaje de error.
     */
    @Override
    public Object Desapila() throws Exception {
        Object elemento = null;

        if (!pilavacia()) {
            elemento = new Object();
            elemento = datos[cima - 1];
            cima--;
            numelementos--;

        } else {

            throw new Exception("ERROR: Pila vacía");
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
    public Object Primero() throws Exception {
        Object elemento = null;

        if (!pilavacia()) {

            elemento = datos[cima - 1];
        } else {
            throw new Exception("ERROR: Pila vacía");
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

//    public static void main(String[] args) {
//        Pila p = new Pila(5);
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
