package practica.pkg6.semáforos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Con objeto de visualizar la correcta evolución de los hilos, se deberá realizar la presentación gráfica en pantalla de su estado.
 * El canvas deberá mostrar en cada momento el estado de la mesa de grapado así como la actividad de los robots.
 * @author Alberto Fernández
 */
public class CanvasP6 extends Canvas {

    private int hojasEnMesa;
    private final int NUMROBOTSA;
    private int hojasRestantes[] = {10, 10, 10, 10};
    private Generador g;
    private boolean RobotAconHoja[] = {false, false, false, false};
    private boolean pintaEnCinta = false;

    public CanvasP6(Generador generador) {
        hojasEnMesa = 0;
        NUMROBOTSA = generador.getNumRobots();
        g = generador;
        setSize(generador.getWidth(), generador.getHeight());
        setBackground(Color.gray);

    }
/**
 * Vuelve a pintar el canvas.
 * @param g 
 */
    @Override
    public void update(Graphics g) {
        paint(g);
    }
/**
 * Pinta en el canvas.
 * @param g 
 */
    @Override
    public void paint(Graphics g) {
        Image img = createImage(this.getWidth(), this.getHeight());
        Graphics of = img.getGraphics();


//        
//        for (int i = 0; i < NUMROBOTSA; i++) {
//            for (int j = 0; j < hojasRestantes[i]; j++) {
//                of.drawImage(getIcono("src\\practica\\pkg6\\semáforos\\papel.png", 50, 70).getImage(), 100 + 250 * i, 50 + 5 * j, this); //Pinta monton de hojas
//            }
//        }

        for (int i = 0; i < NUMROBOTSA; i++) {
            of.drawString("Hojas restantes: " + hojasRestantes[i], 50 + 250 * i, 50);
        }

        for (int i = 0; i < NUMROBOTSA; i++) {
            of.drawImage(getIcono("src\\practica\\pkg6\\semáforos\\brazo.png", 580 / 3, 379 / 3).getImage(), 250 * i, 100 + 50, this); // RobotsA
        }

        for (int i = 0; i < RobotAconHoja.length; i++) {
            if (RobotAconHoja[i]) {
                of.drawImage(getIcono("src\\practica\\pkg6\\semáforos\\papel.png", 50, 70).getImage(), 250 * i, 100 + 50, this); // Hojas en robots
            }
        }

        of.fillRect(0, 750, getWidth(), 20);
        of.setColor(Color.darkGray);
        of.fillRect(0, 770, getWidth(), 50);
        of.setColor(Color.black);
        of.fillRect(0, 800, getWidth(), 20);

//        for (int i = 0; i < 8; i++) {
//            of.drawImage(getIcono("src\\practica\\pkg6\\semáforos\\cinta.png", 250, 150).getImage(), 0 + 145 * i, 700 + 12 * i, this); //Cinta Transportadora
//        }

        for (int i = 0; i < hojasEnMesa; i++) {
            of.drawImage(getIcono("src\\practica\\pkg6\\semáforos\\papel.png", 50, 70).getImage(), (getWidth() / 2) - 50, 400 + 5 * i, this); //Papeles apilados
        }

        if (pintaEnCinta) {
            for (int k = 0; k < 4; k++) {
                of.drawImage(getIcono("src\\practica\\pkg6\\semáforos\\papel.png", 50, 70).getImage(), ((getWidth() / 2) - 50), (700 + 5 * k), this); //Papeles apilados

            }
        }

        of.drawImage(getIcono("src\\practica\\pkg6\\semáforos\\brazo.png", 580 / 3, 379 / 3).getImage(), (getWidth() / 2) - 50, 500, this); // RobotG

        g.drawImage(img, 0, 0, null);

    }
/**
 * Metodo auxiliar para obtener una imagen pasándole los parametros:
 * @param ruta String que contiene la ruta de la imagen que se quiere convertir a icono.
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
/**
 * Disminuye una hoja del número restante de hojas, actualiza el estado del robot
 * y vuelve a pintar el canvas.
 * @param id 
 */
    public void pintaEnRobot(int id) {
        hojasRestantes[id]--;
        RobotAconHoja[id] = true;
        repaint();
    }
/**
 * Aumenta el numero de hojas apiladas y vuelve a pintar el canvas.
 * @param id 
 */
    public void pintaEnMesa(int id) {

        hojasEnMesa++;
        repaint();
    }
/**
 * Actualiza el estado del robot y vuelve a pintar el canvas.
 * @param id 
 */
    public void borraEnRobot(int id) {
        RobotAconHoja[id] = false;
        repaint();
    }
/**
 * Establece el numero de hojas apiladas a 0 y vuelve a pintar el canvas.
 */
    public void borraEnMesa() {
        hojasEnMesa = 0;
        repaint();
    }
/**
 * Cambia la variable pintaEnCinta a verdadero para que pinte las hojas grapadas en
 * la cinta. Además, vuelve a pintar el canvas.
 */
    public void pintaEnCinta() {
        pintaEnCinta = true;
        repaint();
    }
/**
 * Establece la variable pintaEnCinta a falso para que no pinte las hojas grapadas en
 * la cinta transportadora. Por último, actualiza el canvas.
 */
    public void borraEnCinta() {
        pintaEnCinta = false;
        repaint();
    }

}
