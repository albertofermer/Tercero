package proyecto1;

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

    private ArrayList<Integer> colaEspera;
    private ArrayList<Integer> colaPiscina;
    private ArrayList<Integer> colaMaterial;

    public Lienzo() {
        colaEspera = new ArrayList();
        colaPiscina = new ArrayList();
        colaMaterial = new ArrayList();
        setSize(1000, 1000);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Image img = createImage(this.getWidth(), this.getHeight());
        Graphics of = img.getGraphics();
        of.setColor(Color.CYAN);
        of.fillRect(50, 50, 600, 800);
        of.setColor(Color.blue);
        of.drawRect(50, 50, 600, 800);

        of.setColor(Color.GREEN);
        of.fillRect(650, 50, 100, 250);
        of.setColor(Color.BLACK);
        of.drawRect(650, 50, 100, 250);

        //Espera para entrar a la piscina
        for (int i = 0; i < colaEspera.size(); i++) {
            of.drawString("" + colaEspera.get(i), 700, 450 + 30*i);
            of.fillRect(700, 450 + i * 20, 20, 30);
        }

        for (int i = 0; i < colaMaterial.size(); i++) {
            of.drawString("" + colaMaterial.get(i), 650, 50 + i*30);
            of.fillRect(650, 50 + i * 20, 20, 30);
        }

        for (int i = 0; i < colaPiscina.size(); i++) {
            of.drawString("" + colaPiscina.get(i), 50 + i * (600/5), 50 );
            of.fillRect(50 + i * (600 / 5), 50, 20, 20);
        }

        g.drawImage(img, 0, 0, this);
    }

    void borraMaterial(int id) {
        colaMaterial.remove((Integer) id);
        repaint();
    }

    void pintaMaterial(int id) {
        colaMaterial.add(id);
        repaint();
    }

    void borraPiscina(int id) {
        colaPiscina.remove((Integer) id);
        repaint();
    }

    void pintaPiscina(int id) {
        colaPiscina.add(id);
        repaint();
    }

    void borraEspera(int id) {
        colaEspera.remove((Integer) id);
        repaint();
    }

    void pintaEspera(int id) {
        colaEspera.add(id);
        repaint();
    }
}
