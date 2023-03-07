/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class Sala extends UnicastRemoteObject implements ISala{

    private boolean libre = true;
    int esperandoconcita = 0;
    int esperandosincita = 0;
    int entraconcita=0;
    ReentrantLock mutex = new ReentrantLock();
    Condition colaconcita = mutex.newCondition();
    Condition colasincita = mutex.newCondition();

     public Sala() throws RemoteException{
        super();
    }
    
    @Override
    public void ConcitaIN() throws Exception {
        mutex.lock();
        try {
            esperandoconcita++;
            while (!libre) {
                colaconcita.await();
            }
            esperandoconcita--;
            if(esperandosincita>0) entraconcita++;
            libre = false;
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void ConcitaOUT() throws Exception{
        mutex.lock();
        try {
            libre = true;
            if ((esperandosincita > 0 && entraconcita>=2) || esperandoconcita ==0) {
                colasincita.signal();
            } else {
                colaconcita.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void SincitaIN() throws Exception {
        mutex.lock();
        try {
            esperandosincita++;
            while (!libre) {
                colasincita.await();
            }
            esperandosincita--;
            entraconcita=0;
            libre = false;
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public synchronized void SincitaOUT() throws Exception{
        mutex.lock();
        try {
            libre = true;
            if (esperandoconcita > 0) {
                colaconcita.signal();
            } else {
                colasincita.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
