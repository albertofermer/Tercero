package practica9;

import Remoto.Revision;
import java.awt.Image;
import static java.lang.Math.abs;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author usuario
 */
public class EstudianteTeoria extends Thread {

    CanvasP7 lienzo;
    Revision r;
    int id;
    char q;
    Random ran;
    Image alumnoImg;

    public EstudianteTeoria(Revision r, int id, CanvasP7 l) {
        this.alumnoImg = getIcono("src\\practica7\\AlumnoTeoria.png", 250, 250).getImage();
        lienzo = l;
        this.ran = new Random();
        this.r = r;
        this.id = id;
    }

    @Override
    public void run() {
        try {

            //lienzo.pintaEsperaTeoria(id);
            q = r.entraTeoria();
            //lienzo.borraEsperaTeoria(id);
            if (q == 's') { //Si es sin portatil
                //lienzo.pintaRevisionNoPortatil(id);
            } else { //Si es con portatil
                //lienzo.pintaRevisionPortatil(id,alumnoImg);
            }

            System.out.println("Estudiante " + id + " revisa teoría.");

            sleep(abs(ran.nextInt() % 10000) + 5000);

            r.saleTeoria(q);

            if (q == 's') { //Si es sin portatil
                //lienzo.borraRevisionNoPortatil(id);
            } else { //Si es con portatil
                //lienzo.borraRevisionPortatil(id,alumnoImg);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(EstudianteTeoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(EstudianteTeoria.class.getName()).log(Level.SEVERE, null, ex);
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
