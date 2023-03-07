package Modelo;

/**
 *
 * @author Alberto Fernández
 */
public class Nodo {

    private int distancia = 0;
    private int heuristica = 0;
    private int puntuacion = 0;
    private Nodo anterior = null;
    private int x;
    private int y;
    private int tipo;

    public Nodo(int px, int py, int ptipo, Nodo pObjetivo) {

        x = px;
        y = py;
        tipo = ptipo;
        if (pObjetivo != null) {
            heuristica = Math.abs(x - pObjetivo.getX()) + Math.abs(y - pObjetivo.getY());
        }

    }

    public void calculaCoste() {

        if (anterior == null) {
            distancia = 0;
        } else {
            distancia = 1 + anterior.getDistancia();
            puntuacion = distancia + heuristica;
        }
    }

    public int compararNodo(Nodo o) {
        int resultado = 0;

        if (puntuacion > o.getPuntuacion()) {
            resultado = 1;
        }
        if (puntuacion < o.getPuntuacion()) {
            resultado = -1;
        }
        
        return resultado;
    }
    
    @Override
    public String toString(){
        return "(" + x + " , " + y + ")" + ": " + tipo;
    }
    public int getDistancia() {
        return distancia;
    }

    public int getHeuristica() {
        return heuristica;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTipo() {
        return tipo;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
