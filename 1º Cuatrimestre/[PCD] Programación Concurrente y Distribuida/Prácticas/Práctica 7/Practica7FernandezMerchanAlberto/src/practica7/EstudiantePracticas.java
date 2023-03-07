package practica7;

import java.awt.Image;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author usuario
 */
public class EstudiantePracticas implements Runnable {

    CanvasP7 lienzo;
    Revision r;
    int id;
    Random ran;
    Image alumnoImg;

    public EstudiantePracticas(Revision r, int id, CanvasP7 l) {
        this.alumnoImg = getIcono("src\\practica7\\AlumnoPractica.png", 51*3, 81*3).getImage();
        lienzo = l;
        this.ran = new Random();
        this.r = r;
        this.id = id;
    }

    @Override
    public void run() {
        try {

            lienzo.pintaEsperaPracticas(id);

            r.entraPracticas();

            lienzo.borraEsperaPracticas(id);
            lienzo.pintaRevisionPortatil(id, alumnoImg); //Siempre va a revisar uno con portatil

            System.out.println("Estudiante " + id + " revisa prácticas.");
            sleep(abs(ran.nextInt() % 10000) + 5000);

            lienzo.borraRevisionPortatil(id,alumnoImg);

            r.salePracticas();

        } catch (InterruptedException ex) {
            Logger.getLogger(EstudiantePracticas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ImageIcon getIcono(String ruta, int x, int y) {

        ImageIcon img = new ImageIcon(ruta);
        Image imagen = img.getImage();
        Image newimg = imagen.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);

        return icono;
    }

}
