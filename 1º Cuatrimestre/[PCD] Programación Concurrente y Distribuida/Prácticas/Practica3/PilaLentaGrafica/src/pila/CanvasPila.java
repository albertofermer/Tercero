/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pila;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto Fernández
 */
public class CanvasPila extends Canvas {

    private int cima;
    private int capacidad;
    private int numelementos;
    private Object elementos[];
    private String mensaje;
    
    private int elementosProducidos = 0;
    private int elementosConsumidos = 0;
    

    public CanvasPila(int capacidad) {
        this.capacidad = capacidad;
        this.mensaje = "";
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        //Utilizar una imagen a modo de buffer..

        Image img = createImage(this.getWidth(), this.getHeight());
        Graphics of = img.getGraphics();

        of.setColor(Color.decode("#0B2611"));

        of.drawRect(100, 100, 250, 40 * capacidad); //¿Como hacerlo más ancho?

        for (int i = 1; i < capacidad; i++) {
            of.drawLine(100, 100 + 40 * i, 350, 100 + 40 * i); //Dibuja los separadores de la pila
        }


        of.setFont(new Font("Felix Titling", Font.BOLD, 25));
        of.setColor(Color.white);
        
        of.drawString("PILA VACÍA", 50, (capacidad+2)*40+100);
        of.drawString("PILA LLENA", 80 * 4, (capacidad+2)*40+100);
        
        new Font("Felix Titling", Font.BOLD, 15);
        
        of.drawString("Elementos: \t" + numelementos, 150, 30);
        of.setFont(new Font("Times New Romans", Font.BOLD, 12));
        of.drawString("Elementos Producidos: \t" + elementosProducidos, 100, 60);
        of.drawString("Elementos Consumidos: \t" + elementosConsumidos, 100, 80);
        
        of.setFont(new Font("Felix Titling", Font.BOLD, 25));
        
        if (mensaje.equals("PILA VACÍA")) {
            of.setColor(Color.red);
            of.drawString(mensaje, 50, (capacidad+2)*40+100);
            of.setColor(Color.white);

        } else if (mensaje.equals("PILA LLENA")) {

            of.setColor(Color.red);
            of.drawString(mensaje, 80 * 4, (capacidad+2)*40+100);
            of.setColor(Color.white);
        }

        of.setFont(new Font("Arial", Font.BOLD, 15));
        for (int i = 0; i < numelementos; i++) {
            of.drawString(elementos[i].toString(), 200, ((100 - 10) + 40 * capacidad) - 40 * i); //Dibuja los elementos de la pila

        }
        of.setColor(Color.red);
        ImageIcon flecha = new ImageIcon("src\\flecha.png");
        Image imagen = flecha.getImage();
        Image newimg = imagen.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);
        
        of.drawImage(icono.getImage(), 50, (cima + (40 * capacidad) + 25) - 40 * (numelementos - 1), null);
        //of.fillOval(50, (cima + (40 * capacidad) + 25) - 40 * (numelementos - 1), 25, 25);

        g.drawImage(img, 0, 0, null);

    }

    public void avisa(String mensaje) {
        this.mensaje = mensaje;

        repaint();
    }

    public void representa(Object elementos[], int cima, int numelementos) {

        if(this.cima > cima) //Se ha quitado un elemento
        {
            elementosConsumidos++;
        }
        else if (this.cima < cima) //Se ha añadido un elemento
        {
            elementosProducidos++;
        }
        
        
        this.cima = cima;
        
        this.numelementos = numelementos;
        this.elementos = elementos;
        this.mensaje = "";
        repaint();
    }

}
