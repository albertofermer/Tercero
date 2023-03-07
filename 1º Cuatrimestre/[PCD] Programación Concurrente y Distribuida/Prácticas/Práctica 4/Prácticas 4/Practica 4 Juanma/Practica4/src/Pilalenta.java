/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.Exception;
import static java.lang.Thread.sleep;

/**
 *
 * @author theen
 */
public class Pilalenta implements IPila {

    private int cima;
    private int capacidad;
    private int numelementos;
    private final Object[] datos;
    private final CanvasPila canvas;
    private int numdesap;

    public Pilalenta(int capacidad, CanvasPila canvas) {
        this.cima = 0;
        this.capacidad = capacidad;
        this.datos = new Object[capacidad];
        this.numelementos = 0;
        this.canvas = canvas;
        this.numdesap = 0;
    }

    public boolean pilavacia() {
        return numelementos == 0;
    }

    public boolean pilallena() {
        return numelementos == capacidad;
    }

    @Override
    public int GetNum() {
        return numelementos;
    }

    @Override
    public synchronized void Apila(Object elemento) throws Exception {
        int intento = 0;
        if (numdesap == 15)
        {
            canvas.avisa("");
            throw new Exception("El hilo consumidor ha muerto");
        }
        while(pilallena()){
            if (intento >= 3)
            {
                canvas.avisa("PILA LLENA");
                throw new Exception("No hay hueco en la pila para insertar un nuevo elemento");
            }
            System.out.println("Hilo " + Thread.currentThread().getId() + " en espera para insertar elemento");
            intento++;
            wait();
        }
            sleep(300);
            datos[numelementos] = elemento;
            cima = numelementos;
            numelementos++;
            canvas.representa(datos, cima, numelementos);
            notifyAll();
    }

    @Override
    public synchronized Object Desapila() throws Exception {
        int intento = 0;
        while(pilavacia())
        {
            if (intento >= 3)
            {
                canvas.avisa("PILA VACIA");
                throw new Exception("La pila está vacía");
            }
            System.out.println("Hilo " + Thread.currentThread().getId() + " en espera para extraer elemento");
            intento++;
            wait();
        }
            sleep(300);
            Object elemento = datos[cima];
            numelementos--;
            datos[cima] = null;
            cima--;
            numdesap++;
            canvas.representa(datos, cima, numelementos);
            notifyAll();
            return elemento;
    }

    @Override
    public Object Primero() throws Exception {
        if (pilavacia()) {
            throw new Exception("La pila está vacía");
        } else {
            return datos[cima];
        }
    }

}
