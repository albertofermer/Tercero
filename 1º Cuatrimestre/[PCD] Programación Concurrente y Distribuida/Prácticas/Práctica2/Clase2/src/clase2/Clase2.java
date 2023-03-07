package clase2;

import static java.lang.Thread.sleep;

/**
 *
 * @author usuario
 */
public class Clase2 {

//    public static void main(String[] args) throws InterruptedException {//main tambien es considerada un hilo.
//
////        Thread h1 = new HiloThread(1); //Se puede crear un objeto de la superclase
////        HiloThread h2 = new HiloThread(2);
////        Thread m = Thread.currentThread(); //guardamos current thread en una variable.
//
//        //HiloRunnable r1 = new HiloRunnable(3); //Creamos un runnable r1 con un método run().
//        
//        HiloRunnable r3 = new HiloRunnable(3);
//        Thread h3 = new Thread(r3); // A un Thread le puedo pasar un objeto que tenga un metodo run(). 
//        //Thread h4 = new Thread(new HiloRunnable(4)); //Podemos instanciarlo dentro del Thread().
//        Thread h4 = new Thread(r3); //Puedo crear dos hilos con el mismo run()
//                                    //como es el mismo objeto, las variables son las mismas...
//        h4.start();         
//        h3.start(); //Llama al hilo nuevo que hemos creado.
//
//        
////        m.setPriority(Thread.MAX_PRIORITY);
////        
////        h1.start(); //No usar h1.run(); start() pertenece a Thread() -> crea un hilo con el codigo que hay dentro de run()
////        h2.start(); //No usar h2.run();
//////        
//////        //Para esperar que un hilo termine:
////        h2.join(); //Hasta que no finalice el hilo 1, no sigue ejecutando el programa.
////        h1.join();
//        
//        h3.join();
//        h4.join();
//
//        r3.getSuma(); //Como corren 2 hilos por el mismo objeto, ambos hilos incrementan la misma variable.
//                        //El resultado puede no ser el esperado..
//        
//        
////        
////        for (int i = 0; i < 10; i++) { //En principio no podemos conocer los atributos del hilo main porque no hereda de Thread.
////            //Para ello debemos acceder a un metodo que devuelve el hilo (Thread.currentThread()).
////            System.out.println("Soy el main "
////                    + " Con identificador: " + m.getId()
////                    + " Con nombre: " + m.getName()
////                    + " Con estado: " + m.getState()
////                    + " Con prioridad: " + m.getPriority());
////
////            sleep(100);
////        }
//
//    }

    public static void main(String[] args) throws InterruptedException { //utilizacion de hilos mediante recursos.
        
        Recurso r = new Recurso();
        
        r.incrementa();
        r.versuma();
        
        HiloThread h1 = new HiloThread(1,r);
        HiloRunnable r1 = new HiloRunnable(1,r);
        Thread h2 = new Thread(r1);
        
        
        h1.start();
        h2.start();
        h1.join();
        h2.join();
        
        r.versuma();
        
    }
}
