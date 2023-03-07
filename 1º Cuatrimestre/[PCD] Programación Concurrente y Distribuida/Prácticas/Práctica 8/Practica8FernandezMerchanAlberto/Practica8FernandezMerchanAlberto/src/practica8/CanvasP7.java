package practica8;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author usuario
 */
public class CanvasP7 extends Canvas {

   private ArrayList<Integer> colaTeoria;
   private ArrayList<Integer> colaPracticas;
   private ArrayList<Integer> EstudiantesEnRevPortatil; //Contiene el id de los dos estudiantes que estarán en revision con portátil.
   private int EstudianteEnRevSinPortatil; //Contiene el id del unico estudiante que estará en revisión sin portátil.
   private Image AlumnoPracticas;
   private Image AlumnoTeoria;
   
   private ArrayList<Image> imagenesEstudiantesEnRevPortatil;

    public CanvasP7() {
        setSize(1000, 1000);
        setBackground(Color.decode("#428046"));

        EstudianteEnRevSinPortatil = -1;
        colaTeoria = new ArrayList<>();
        colaPracticas = new ArrayList<>();
        EstudiantesEnRevPortatil = new ArrayList<>();
        
        AlumnoPracticas = getIcono("src\\practica8\\AlumnoPractica.png", 51, 84).getImage();
        AlumnoTeoria = getIcono("src\\practica8\\AlumnoTeoria.png", 70, 70).getImage();
        imagenesEstudiantesEnRevPortatil = new ArrayList<>();
        

    }

   @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Image img = createImage(this.getWidth(), this.getHeight());
        Graphics of = img.getGraphics();

        of.setColor(Color.decode("#65a25d"));
        of.fillRect(10, 50, this.getWidth() - 40, 100); // Cola de espera teoria
        of.fillRect(10, this.getHeight() - 200, this.getWidth() - 40, 100); // Cola de espera practicas
        of.fillRect(10, this.getHeight() / 2 - 250, this.getWidth() / 2 - 50, 300); // Revision con portatil
            of.drawImage(getIcono("src\\practica8\\ProfesorPortatil.png", 1280/6, 770/5).getImage(), 20, this.getHeight() / 2 - 250, this);
            of.drawImage(getIcono("src\\practica8\\ProfesorPortatil.png", 1280/6, 770/5).getImage(), 20+1280/6, this.getHeight() / 2 - 250, this);
        of.fillRect(this.getWidth() / 2, this.getHeight() / 2 - 250, this.getWidth() / 2 - 35, 300); //Revision sin portatil
            of.drawImage(getIcono("src\\practica8\\profesorSinPortatil.png", 397/2, 500/2).getImage(), 20+ this.getWidth() / 2, this.getHeight() / 2 - 250, this);

        of.setColor(Color.black); //Sombra de las letras
        of.setFont(new Font("Times New Roman", 3, 25));
        of.drawString("Cola Teoría", 15, 45);
        of.drawString("Cola Prácticas", 15, this.getHeight() - 205);
        of.drawString("Revisión con Portátil", 15, this.getHeight() / 2 - 255);
        of.drawString("Revisión sin Portátil", this.getWidth() / 2 + 5, this.getHeight() / 2 - 255);

        of.setColor(Color.decode("#FF0080")); //Etiquetas de las cajas
        of.setFont(new Font("Times New Roman", 3, 25));
        of.drawString("Cola Teoría", 10, 45);
        of.drawString("Cola Prácticas", 10, this.getHeight() - 205);
        of.drawString("Revisión con Portátil", 10, this.getHeight() / 2 - 255);
        of.drawString("Revisión sin Portátil", this.getWidth() / 2, this.getHeight() / 2 - 255);

        for (int i = 0; i < colaPracticas.size(); i++) { // Pinta en la cola de espera de practicas
            of.drawString("" + colaPracticas.get(i), 20 + 100 * i, this.getHeight() - 180);
            of.drawImage(AlumnoPracticas,20 + 100 * i, this.getHeight() - 180, this);
        }
        for (int i = 0; i < colaTeoria.size(); i++) { // Pinta en la cola de espera de practicas
            of.drawString("" + colaTeoria.get(i), 20 + 100 * i, 80);
            of.drawImage(AlumnoTeoria, 20 + 100 * i, 80, this);
        }

        for (int i = 0; i < EstudiantesEnRevPortatil.size(); i++) { //Pinta en la revision con portatil
            of.drawString("" + EstudiantesEnRevPortatil.get(i), 200 + 120 * i, this.getHeight() / 2 - 200);
            of.drawImage(imagenesEstudiantesEnRevPortatil.get(i), 150 + 120 * i, this.getHeight() / 2 - 200, this);
            
        }

        if (EstudianteEnRevSinPortatil != -1) { //Pinta en la revision sin portatil
            of.drawString("" + EstudianteEnRevSinPortatil, 300 + this.getWidth() / 2, this.getHeight() / 2 - 200);
            of.drawImage(getIcono("src\\practica8\\AlumnoTeoria.png", 250, 250).getImage(),  250 + this.getWidth() / 2, this.getHeight() / 2 - 200, this); //Siempre sera alumno de teoria
        }

        g.drawImage(img, 0, 0, this);
    }

    /**
     * Metodo auxiliar para obtener una imagen pasándole los parametros:
     *
     * @param ruta String que contiene la ruta de la imagen que se quiere
     * convertir a icono.
     * @param x Nuevo ancho de la imagen para reescalarla.
     * @param y Nueva altura de la imagen para reescalarla.
     * @return ImageIcon reescalado y con la imagen pasada en ruta.
     */
    private ImageIcon getIcono(String ruta, int x, int y) {

        ImageIcon img = new ImageIcon(ruta);
        Image imagen = img.getImage();
        Image newimg = imagen.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);

        return icono;
    }

    public void pintaEsperaTeoria(int id) {

        colaTeoria.add(id);
        repaint();

    }

    public void pintaEsperaPracticas(int id) {

        colaPracticas.add(id);
        repaint();
    }

    public void borraEsperaTeoria(int id) {

        colaTeoria.remove(colaTeoria.indexOf(id));
        repaint();
    }

    public void borraEsperaPracticas(int id) {
        colaPracticas.remove(colaPracticas.indexOf(id));
        repaint();
    }

    public void pintaRevisionPortatil(int id, Image img) {
        EstudiantesEnRevPortatil.add(id);
        imagenesEstudiantesEnRevPortatil.add(img);
        repaint();
    }

    public void pintaRevisionNoPortatil(int id) {
        EstudianteEnRevSinPortatil = id;
        repaint();
    }

    public void borraRevisionPortatil(int id, Image img) {
        EstudiantesEnRevPortatil.remove(EstudiantesEnRevPortatil.indexOf(id));
        imagenesEstudiantesEnRevPortatil.remove(img);
        repaint();
    }

    public void borraRevisionNoPortatil(int id) {
        EstudianteEnRevSinPortatil = -1;
        repaint();
    }

}
