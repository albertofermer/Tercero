/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploframe;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author usuario
 */
public class Lienzo extends Canvas {

   private int[] contadores = {0,0};
    
    public Lienzo(int ancho, int alto) {
        this.setSize(ancho, alto);
        this.setBackground(Color.green);
        
        
    }

    @Override
    public void paint(Graphics g) {
        
        Font fuente1 = new Font("Times New Roman", Font.BOLD, 25);
        Font fuente2 = new Font("Comic Sans", Font.BOLD | Font.ITALIC, 25);
        
        Image img = createImage(this.getWidth(), this.getHeight());//Imagen de copia
        Graphics og = img.getGraphics(); //Offline Graphics (og)

//Pinto en la imagen.

        og.setColor(Color.white);
        og.setFont(fuente1);
        og.fillRect(40, 25, 25, 25);
        og.drawString("Contador 0 = " + contadores[0], 70, 50);
        
        
        og.fillOval(40, 75, 25, 25);
        og.setFont(fuente2);
        og.drawString("Contador 1 = " + contadores[1], 70, 100);
        
        g.drawImage(img, 0, 0, null);
    }
    
    public void actualiza(int[] contadores){
        this.contadores = contadores;
        repaint();
        
        
    }
    
   @Override
    public void update(Graphics g){
        paint(g);
    }

}
