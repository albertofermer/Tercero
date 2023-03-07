/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aparcamientos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *  Se usa para representar el problema del parking de los coches y autobuses
 * @author pedro
 */
public class CanvasParking extends Canvas {

    Image cochesimg, cochesimg2, busimg, puentegif, barcoimg;
    List colacoche = new LinkedList();
    List colabus = new LinkedList();
    List cochesparking = new ArrayList<Integer>(3);
    List cochesparkingbus = new ArrayList<Integer>(2);

    int tipo = 0, busaparcado=-1;
    MediaTracker dibu;
    boolean subepuente = false;

    /**
     * 
     * @param ancho es el ancho del canvas
     * @param alto es el alto del canvas
     */
    public CanvasParking(int ancho, int alto) {
        this.setSize(ancho, alto);
        this.setBackground(Color.white);

        cochesimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/cochec1.gif"));
        cochesimg2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/cochec0.gif"));        
        busimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/bus2.png"));

        dibu = new MediaTracker(this);
        try {
            dibu.addImage(cochesimg, 1);
            dibu.waitForID(1);
            dibu.addImage(busimg, 2);
            dibu.waitForID(2);

        } catch (java.lang.InterruptedException e) {
            System.out.println("Couldn't load one of the images");
        }

        for (int i = 0; i < 3; i++) {
            cochesparking.add(i, -1);
        }
        for (int i = 0; i < 2; i++) {
            cochesparkingbus.add(i, -1);
        }
    }

    /**
     * Inserta un vehiculo en la cola y lo visualiza
     * @param donde es la cola en la que se inserta 1=cola de coches 2=cola del bus
     * @param id es el identificador del vehículo que se inserta
     */
    public synchronized void inserta(int donde, int id) {
        if (donde == 1) {
            colacoche.add(id);
        } else {
            colabus.add(id);
        }
        this.repaint();
    }

    /**
     * Elimina y borra un vehíuclo de la cola
     * @param donde es la cola en la que se inserta 1=cola de coches 2=cola del bus
     * @param id es el identificador del vehículo que se inserta
     */
    public synchronized void quita(int donde, int id) {
        
        if (donde == 1) {
            colacoche.remove((Object) id);
        } else {
            colabus.remove((Object) id);
        }
        this.repaint();
    }
    
    /**
     * Visualiza un autobus aparcado en el parking de autouses
     * @param id  es el identificador del bus aparcado
     */
    public synchronized  void aparcabus(int id){
        busaparcado=id;
        this.repaint();
    }
    
    /**
     * Borra el bus que está aparcado en el parking de autobuses
     */
    public synchronized void salebus(){
        busaparcado=-1;
        this.repaint();
    }
    
    /**
     * Visualiza un coche aparcado en alguno de los huecos del parking
     * @param id es el identificador del coche que se va a visualizar aparcado
     * @param donde es el parking en el que aparca 1=parking de coches 2=parking de buses
     */
    public synchronized void aparcacoche(int id, int donde) {
        if (donde == 1) {
            cochesparking.set(cochesparking.indexOf(-1), id);
        }
        if (donde == 2) {
            cochesparkingbus.set(cochesparkingbus.indexOf(-1), id);
        }
        System.out.println("Entra " + cochesparking.get(0) + " " + cochesparking.get(1) + " " + cochesparking.get(2) + " ");
        System.out.println("Entra2 " + cochesparkingbus.get(0) + " " + cochesparkingbus.get(1));

        this.repaint();
    }

    /**
     * Borra un coche del parking en el que está aparcado
     * @param id es el identificador del coche que se va a borrar
     * @param donde es el parking del que se borra: 1=parking de coches 2=parking de buses
     */
    public synchronized void salecoche(int id, int donde) {
        if (donde == 1) {
            cochesparking.set(cochesparking.indexOf(id), -1);
        }
        if (donde == 2) {
            cochesparkingbus.set(cochesparkingbus.indexOf(id), -1);

        }
        System.out.println("Sale " + cochesparking.get(0) + " " + cochesparking.get(1) + " " + cochesparking.get(2) + " ");
        System.out.println("Sale2 " + cochesparkingbus.get(0) + " " + cochesparkingbus.get(1));

        this.repaint();
    }

    /**
     * Metodo update del canvas, invocado por repaint()
     * @param g 
     */
    @Override
    public  void update(Graphics g) {
        
        paint(g);
  
    }

    /**
     * Método paint del canvas, incovado por update
     * @param g 
     */
    @Override
    public  void paint(Graphics g) {
        BufferedImage imagen = new BufferedImage(getWidth(), getHeight(), ColorModel.OPAQUE);
        Graphics gbuf = imagen.getGraphics();
        gbuf.setColor(Color.green);
        gbuf.fillRect(0, 0, getWidth(), getHeight());

        gbuf.setColor(Color.white);
        gbuf.fillRect(92, 70, getWidth(), 80);
        gbuf.fillRect(92, 180, getWidth(), 80);
        gbuf.fillRect(90, 10, 40, 270);

        gbuf.setColor(Color.CYAN);
        for (int i = 0; i < 3; i++) {
            gbuf.fillRect(10, 80 * i + 10, 80, 70);

        }
        gbuf.setColor(Color.PINK);
        gbuf.fillRect(10, 270, 180, 70);

        gbuf.setColor(Color.black);

        Iterator iter = colacoche.iterator();
        int i = 0;
        while (iter.hasNext()) {
            gbuf.drawString("   " + iter.next() + "   ", 100 + 50 * i, 100);
            gbuf.drawImage(cochesimg, 100 + 50 * i, 100, null);
            i++;
        }

        iter = colabus.iterator();
        i = 0;
        while (iter.hasNext()) {
            gbuf.drawString("   " + iter.next() + "   ", 170 + 110 * i, 200);
            gbuf.drawImage(busimg, 170 + 110 * i, 200, null);
            i++;

        }

        for (int j = 0; j < 3; j++) {
            if ((int) cochesparking.get(j) != -1) {
                gbuf.drawString("   " + cochesparking.get(j) + "   ", 20, 80 * j + 25);
                gbuf.drawImage(cochesimg, 20, 80 * j + 25, null);
            }
        }

        if ((int) cochesparkingbus.get(0) != -1) {
                gbuf.drawString("   " + cochesparkingbus.get(0) + "   ", 15, 285 );
                gbuf.drawImage(cochesimg, 15, 285, null);
            }
        if ((int) cochesparkingbus.get(1) != -1) {
                gbuf.drawString("   " + cochesparkingbus.get(1) + "   ", 125, 285 );
                gbuf.drawImage(cochesimg2, 125, 285, null);
            }
        
        
        if(busaparcado!=-1){
                gbuf.drawString("   " + busaparcado + "   ", 15, 285 );
                gbuf.drawImage(busimg, 15, 285, null);
            
        }

        g.drawImage(imagen, 0, 0, this);       

    }
}
