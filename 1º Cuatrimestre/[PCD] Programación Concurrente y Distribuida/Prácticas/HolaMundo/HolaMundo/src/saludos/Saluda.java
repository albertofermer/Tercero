/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saludos; //Clase de ejemplo

import java.util.Random;





/**
 * Esta clase
 * sirve de ejmplo
 * para java
 * @author usuario
 * @version 0.1
 */
public class Saluda implements ISaludable{

    private int tipo;

    /**
     * Se creará un objetosl
     * @param tipo es el tipo del objeto que tratamos
     */
    public Saluda(int tipo) {
        this.tipo = tipo;
    }

    /**
     * ESte metodo bla bla bla
     * @param mensaje que se pondrá por pantall
     * @throws Exception cuando el tupo no es 1 no 0
     * @since 19/10/2020
     */
    @Override
    public void saludar(String mensaje) throws Exception {
        Random r= new Random();
        r.setSeed(System.nanoTime());
        
        r.nextInt(2);
        switch (tipo) {
            case 0:
                System.out.println("Hola Mundo");
                break;
            case 1:
                System.out.println(mensaje);
                break;
            default:
                throw new Exception("Tipo no controlado");
        }
    }
}
