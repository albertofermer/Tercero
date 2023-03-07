package practica.pkg5.tienda;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto Fernández
 */
public class CanvasTienda extends Canvas {

    private final Dimension dimensionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<Long> esperando = new ArrayList();
    private ArrayList<ImageIcon> Imagenesperando = new ArrayList();
    private Graphics of;
    long idComprando;
    boolean hayComprando;
    boolean hayReparando;
    long idReparando;
    ImageIcon imagenComprando;
    ImageIcon imagenReparando;

    public CanvasTienda() {

    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Image img = createImage(this.getWidth(), this.getHeight());
        of = img.getGraphics();
        of.setColor(Color.decode("#0B2611"));
        //Crea cuadros para pintar
            //of.fillRect(50, 20, (dimensionPantalla.width / 3), (dimensionPantalla.height) / 4); //Cuadro de comprar
            //of.fillRect((dimensionPantalla.width - dimensionPantalla.width / 3 - 50), 20, (dimensionPantalla.width / 3), (dimensionPantalla.height) / 4); // Cuadro de reparar
            //of.fillRect(50, dimensionPantalla.height - dimensionPantalla.width / 5,(dimensionPantalla.width / 3), (dimensionPantalla.height) / 4); //Cola de espera
        //of.fillRect(dimensionPantalla.width / 2 - 250, dimensionPantalla.height / 2 - 200, 500, 300); //Mensajes de informacion
        //Crea las imagenes
        ImageIcon tecnico = getIcono("src\\tecnico.jpg");
        ImageIcon tienda = getIcono("src\\tienda.jpg");
        ImageIcon vendedor = getIcono("src\\vendedor.jpg");
        of.drawImage(tecnico.getImage(), ((dimensionPantalla.width - tienda.getIconWidth())-50), 20, this);
        //of.drawImage(tienda.getImage(), dimensionPantalla.width - tienda.getIconWidth(), dimensionPantalla.height - dimensionPantalla.width / 5, this);
        of.drawImage(tienda.getImage(),  (dimensionPantalla.width/2 - tienda.getIconWidth())*2, (dimensionPantalla.height - tienda.getIconHeight()*2), this);
        of.drawImage(vendedor.getImage(), 50, 20, this);

        of.setColor(Color.red);
        of.setFont(new Font("Calibri", Font.BOLD, 20));
        for (int i = 0; i < esperando.size(); i++) {
            of.drawString(Long.toString(esperando.get(i)), (dimensionPantalla.width/2 - tienda.getIconWidth())*2 + (100 + i * 100), (dimensionPantalla.height - tienda.getIconHeight()) - Imagenesperando.get(i).getIconHeight());
            of.drawImage(Imagenesperando.get(i).getImage(),(dimensionPantalla.width/2 - tienda.getIconWidth())*2 + (100 + i * 100), (dimensionPantalla.height - tienda.getIconHeight())  - Imagenesperando.get(i).getIconHeight(), this);
        }

        if (hayComprando) {
            of.drawString(Long.toString(idComprando), 50, 150);
            of.drawImage(imagenComprando.getImage(),50,150, this);
        }

        if (hayReparando) {
            of.drawString(Long.toString(idReparando),(dimensionPantalla.width - dimensionPantalla.width / 3 - 50), 150);
            of.drawImage(imagenReparando.getImage(),(dimensionPantalla.width - dimensionPantalla.width / 3 - 50), 150, this);
        }

        g.drawImage(img, 0, 0, null);
    }

    private ImageIcon getIcono(String ruta) {

        ImageIcon img = new ImageIcon(ruta);
        Image imagen = img.getImage();
        Image newimg = imagen.getScaledInstance((dimensionPantalla.width / 3), (dimensionPantalla.height) / 4, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);

        return icono;
    }

    void pintaEspera(long id, ImageIcon imagen) {

        esperando.add(id);
        Imagenesperando.add(imagen);
        repaint();
    }

    void pintaComprar(long id, ImageIcon imagen) {
        idComprando = id;
        imagenComprando = imagen;
        hayComprando = true;
        repaint();
    }

    void pintaReparar(long id, ImageIcon imagen) {
        idReparando = id;
        imagenReparando = imagen;
        hayReparando = true;
        repaint();
    }

    void borraEspera(long id, ImageIcon imagen) {
        esperando.remove(id);
        Imagenesperando.remove(imagen);
        repaint();
    }

    void borraComprar(long id, ImageIcon imagen) {
        hayComprando = false;
        repaint();
    }

    void borraReparar(long id, ImageIcon imagen) {
        hayReparando = false;
        repaint();
    }

}
