/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica9;

import Remoto.Revision;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class MainWindow extends javax.swing.JFrame {

    private static final int NUMESTUDIANTES = 10;
    private static final int ESPERAMIN = 1000; //1 segundo
    private static final int ESPERAMAX = 5000; // 5 segundos
    private static Random r;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        r = new Random();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 789, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {

        try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            MainWindow main = new MainWindow();

            CanvasP7 lienzo = new CanvasP7();
            Registry registro = LocateRegistry.createRegistry(2015);
            Revision rev = new Revision(lienzo);
            registro.rebind("revisionRemota", rev);
            System.out.println("Servidor Funcionando ....");
            main.add(lienzo);

            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    main.setVisible(true);
                }
            });

//            Thread[] estudiantes = new Thread[NUMESTUDIANTES];
//            for (int i = 0; i < NUMESTUDIANTES; i++) {
//                if ((abs(r.nextInt()) % 10) < 6) {//60% Teoria
//                    estudiantes[i] = new EstudianteTeoria(rev, i, lienzo);
//                    estudiantes[i].start();
//
//                } else { //40% Practicas
//                    estudiantes[i] = new Thread(new EstudiantePracticas(rev, i, lienzo));
//                    estudiantes[i].start();
//
//                }
//
//                try {
//                    sleep((abs(r.nextInt()) % (ESPERAMAX - ESPERAMIN)) + ESPERAMIN);
//
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//
//            for (int i = 0; i < NUMESTUDIANTES; i++) { //Termina cuando todos acaben
//                estudiantes[i].join();
//            }
            System.in.read();
            System.out.println("Saliendo del servidor ...");
            System.exit(0);
            System.out.println("Termina la ejecucion del programa");
            sleep(1000);
            System.exit(0);
        } catch (RemoteException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
