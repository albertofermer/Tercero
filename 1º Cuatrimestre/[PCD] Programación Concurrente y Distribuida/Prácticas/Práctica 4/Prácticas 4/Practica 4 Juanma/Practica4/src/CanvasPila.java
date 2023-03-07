import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author theen
 */
public class CanvasPila extends Canvas{
    
    private int capacidad;
    private int cima;
    private int numelementos;
    private Object[] datos;
    private String mensaje;
    private final static Font fMensaje = new Font("Courier New", Font.BOLD, 30);
    private final static Font fNumero = new Font("Consolas", Font.BOLD, 30);
    
    public CanvasPila(int capacidad){
        this.capacidad = capacidad;
        cima = numelementos = 0;
        datos = new Object[capacidad];
        mensaje = "";
    }
    
    @Override
    public void update(Graphics g){
        paint(g);
    }
    
    @Override
    public void paint(Graphics g){
        
        Image i = createImage(getWidth(), getHeight());
        Graphics gr = i.getGraphics();
        
        gr.fillRect(75, 75, 350, 800);
        gr.setColor(Color.white);
        for (int j = 1; j <= 10; j++) {
            gr.drawLine(75, 75+j*81, 500, 75+j*81);
        }
        
        FontMetrics fm = g.getFontMetrics(fNumero);
        gr.setFont(fNumero);
        
        for (int j = 0; j < capacidad; j++) {
            if(datos[j]!=null)
            {
                gr.setColor(Color.white);
                gr.drawString(datos[j].toString(), 240, 850-j*81);
            } 
        }
        
        gr.setFont(fMensaje);
        gr.setColor(mensaje.equals("PILA LLENA")?Color.red:Color.GRAY);
        gr.drawString("PILA LLENA", 170, 50);
        gr.setColor(mensaje.equals("PILA VACIA")?Color.red:Color.GRAY);
        gr.drawString("PILA VACIA", 167, 925);
        
        g.drawImage(i, 0, 0, null);
    }
    
    public void avisa(String mensaje){
        this.mensaje = mensaje;
        repaint();
    }
    
    public void representa(Object[] buf, int cima, int numelementos){
        this.cima = cima;
        this.numelementos = numelementos;
        System.arraycopy(buf, 0, datos, 0, capacidad);
        this.mensaje = "";
        repaint();
    }
}
