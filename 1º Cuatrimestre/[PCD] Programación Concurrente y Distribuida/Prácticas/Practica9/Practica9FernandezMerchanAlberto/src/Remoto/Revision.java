package Remoto;

import IRemoto.IRevision;
import java.awt.Image;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.ImageIcon;
import practica9.CanvasP7;

/**
 *
 * @author usuario
 */
public class Revision extends UnicastRemoteObject implements IRevision{

    Image alumnoTeoria;
    Image alumnoPracticas;
    int numPortatilLibre;
    int esperandoPracticas;
    boolean ProfesoresConPortatilLibre;
    boolean ProfesorSinPortatilLibre;
    private CanvasP7 lienzo;
    Lock lock;
    Condition teoria;
    Condition practicas;
    char quien;

    public Revision(CanvasP7 lienzo) throws RemoteException {
        this.alumnoTeoria = getIcono("src\\practica9\\AlumnoTeoria.png", 250, 250).getImage();
        this.alumnoPracticas = getIcono("src\\practica9\\AlumnoPractica.png", 250, 250).getImage();
        this.lienzo = lienzo;
        numPortatilLibre = 2;
        esperandoPracticas = 0;
        ProfesoresConPortatilLibre = true;
        ProfesorSinPortatilLibre = true;
        lock = new ReentrantLock(true);

        teoria = lock.newCondition();
        practicas = lock.newCondition();

    }

    @Override
    public char entraTeoria() throws InterruptedException, RemoteException {
        lock.lock();

        try {
            if (!ProfesorSinPortatilLibre && (numPortatilLibre == 0)) {
                //System.out.println("El estudiante esta esperando teoria");
                    lienzo.pintaEsperaTeoria(0);
                    teoria.await(); //Si estan todos los profesores ocupados espero
                    lienzo.borraEsperaTeoria(0);
            }

            if (ProfesorSinPortatilLibre) {
                //atiende el que no tiene portatil
                lienzo.pintaRevisionNoPortatil(0);
                ProfesorSinPortatilLibre = false;
                quien = 's';
            } else {
                //atiende uno de los que tiene portatil
                lienzo.pintaRevisionPortatil(0, alumnoTeoria);
                numPortatilLibre--;
                quien = 'p';
            }

        } finally {
            lock.unlock();
        }

        return quien;
    }

    @Override
    public void entraPracticas() throws InterruptedException, RemoteException {
        lock.lock();
        try {
            esperandoPracticas++;
            if (numPortatilLibre == 0) {
                //System.out.println("El estudiante esta esperando practicas");
                lienzo.pintaEsperaPracticas(5);
                practicas.await();
                lienzo.borraEsperaPracticas(5);
            }
            esperandoPracticas--;
            numPortatilLibre--;

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void saleTeoria(char quien) throws RemoteException {
        lock.lock();
        try {
            if (quien == 's') { //Si le ha atendido el sin portatil
                ProfesorSinPortatilLibre = true;
                // y aviso de que he salido 
                teoria.signal();
                lienzo.borraRevisionNoPortatil(0);
            } else {
                numPortatilLibre++;
                //aviso a la cola de practicas de que he salido
                if (esperandoPracticas > 0) {
                    practicas.signal();
                } else {
                    teoria.signal();
                }
                lienzo.borraRevisionPortatil(0,alumnoTeoria);
            }
        } finally {
            lock.unlock();
        }

        //System.out.println("El estudiante sale de teoria");
    }

    @Override
    public void salePracticas() throws RemoteException {

        lock.lock();
        try {
            numPortatilLibre++;
            if (esperandoPracticas > 0) {
                practicas.signal();
            } else {
                teoria.signal();
            }
        } finally {
            lock.unlock();
        }
        
        lienzo.borraRevisionPortatil(5, alumnoPracticas);
        
        //System.out.println("El estudiante sale de practicas");
        //Siempre va a salir de uno con portatil
    }

        private ImageIcon getIcono(String ruta, int x, int y) {

        ImageIcon img = new ImageIcon(ruta);
        Image imagen = img.getImage();
        Image newimg = imagen.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);

        return icono;
    }
    
}
