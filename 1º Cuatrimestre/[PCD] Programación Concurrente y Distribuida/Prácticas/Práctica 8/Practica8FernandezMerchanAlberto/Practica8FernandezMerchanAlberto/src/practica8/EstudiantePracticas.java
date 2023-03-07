package practica8;

import java.awt.Image;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author usuario
 */
public class EstudiantePracticas implements Callable<Integer> {

    CanvasP7 lienzo;
    Revision r;
    int id;
    Random ran;
    Image alumnoImg;

    public EstudiantePracticas(Revision r, int id, CanvasP7 l) {
        this.alumnoImg = getIcono("src\\practica8\\AlumnoPractica.png", 51*3, 81*3).getImage();
        lienzo = l;
        this.ran = new Random();
        this.r = r;
        this.id = id;
    }

    @Override
    public Integer call() {
        int espera =0;
        try {

            lienzo.pintaEsperaPracticas(id);

            r.entraPracticas();

            lienzo.borraEsperaPracticas(id);
            lienzo.pintaRevisionPortatil(id, alumnoImg); //Siempre va a revisar uno con portatil

            System.out.println("Estudiante " + id + " revisa prácticas.");
            espera = abs(ran.nextInt() % 10000) + 5000;
            sleep(espera);

            lienzo.borraRevisionPortatil(id,alumnoImg);

            r.salePracticas();

        } catch (InterruptedException ex) {
            Logger.getLogger(EstudiantePracticas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return espera;
    }

    private ImageIcon getIcono(String ruta, int x, int y) {

        ImageIcon img = new ImageIcon(ruta);
        Image imagen = img.getImage();
        Image newimg = imagen.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);

        return icono;
    }

}
