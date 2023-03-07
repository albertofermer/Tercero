package pkg2021.pkg2022;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Alberto Fernández
 */
public class Lienzo extends Canvas {

    private ArrayList<Integer> colaEsperaCoche;
    private ArrayList<Integer> colaEsperaFurgo;
    private int[] idLavado = {-1, -1, -1};
    private char[] tipoLavado = {'n', 'n', 'n'};

    public Lienzo() {
        setSize(1000, 1000);
        colaEsperaCoche = new ArrayList();
        colaEsperaFurgo = new ArrayList();

    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Image img = createImage(this.getWidth(), this.getHeight());
        Graphics of = img.getGraphics();

        for (int i = 0; i < 3; i++) {
            of.setColor(Color.gray);
            of.fillRect(50 + i * 200, 200, 100, this.getHeight() / 2);

        }

        for (int i = 0; i < colaEsperaFurgo.size(); i++) {
            of.setColor(Color.blue);
            of.drawString("" + colaEsperaFurgo.get(i), 50 + 50 * i, 10);
            of.fillOval(50 + 50 * i, 10, 20, 20);
        }

        for (int i = 0; i < colaEsperaCoche.size(); i++) {
            of.setColor(Color.red);
            of.drawString("" + colaEsperaCoche.get(i), 75+ 50 * i, 10);
            of.fillOval(75 + 50 * i, 10, 20, 20);
        }

        for (int i = 0; i < idLavado.length; i++) {
            if (idLavado[i] != -1) {
                if (tipoLavado[i] == 'f') {
                    of.setColor(Color.blue);
                } else {
                    of.setColor(Color.red);
                }
                of.drawString("" + idLavado[i], 50 + 200 * i, 200);
                of.fillOval(50 + 200 * i, 200, 20, 20);
            } else {
                of.setColor(Color.green);
                of.drawString("Libre", 50 + 200 * i, 200);
            }
        }
        of.setColor(Color.gray);

        of.drawString("Total esperando: " + (colaEsperaCoche.size() + colaEsperaFurgo.size()), 50, 50);
        g.drawImage(img, 0, 0, this);
    }

    public void dibujaEntrada(int id, char c) {
        
        if (c == 'f') {
            colaEsperaFurgo.add(id);
        } else {
            colaEsperaCoche.add(id);
        }
        repaint();
    }

    public void borraEntrada(int id, char c) {
        if (c == 'f') {
            colaEsperaFurgo.remove((Integer.valueOf(id)));
        } else {
            colaEsperaCoche.remove(Integer.valueOf(id));
        }
        repaint();
    }

    public void dibujaLavado(int tunel, int id, char c) {
        idLavado[tunel] = id;
        tipoLavado[tunel] = c;
        repaint();
    }

    public void borraLavado(int tunel, int id, char c) {
        idLavado[tunel] = -1;
        tipoLavado[tunel] = 'n';
        repaint();
    }

}
