/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

/**
 *
 * @author pedro
 */
public class Monton {

    int cantidad = 4, esperagrande = 0;

    public synchronized void CargaPoco() throws InterruptedException {
        while (cantidad == 0 || (esperagrande > 0 && cantidad == 2)) {
            wait();
        }
        cantidad--;
    }

    public synchronized void CargaMucho() throws InterruptedException {
        esperagrande++;
        while (cantidad < 2) {
            wait();
        }
        esperagrande--;
        cantidad = cantidad - 2;
        notifyAll();
    }

    public synchronized void Rellena(int cuanto) {
        cantidad = cantidad + cuanto;
        System.out.println("Dejo " + cuanto + " y hay " + cantidad);
        notifyAll();
    }
}
