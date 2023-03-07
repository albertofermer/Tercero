package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Alberto Fernández
 */
public class Mapa {

    private Integer[][] mapa = {
        {0, 0, 0, 0},
        {3, 0, 3, 0},
        {0, 3, 3, 0},
        {0, 0, 3, 0},
        {3, 3, 3, 3}
    };

    private Nodo[][] mapaNodos;
    private int ancho, alto;

    private Nodo objetivo;
    private ArrayList<Nodo> abierta = new ArrayList();
    private ArrayList<Nodo> cerrada = new ArrayList();
    private ArrayList<Nodo> ruta = new ArrayList();

    public int inicioX = 1;
    public int inicioY = 3;

    public Mapa(int inicioX, int inicioY, int pobjetivoX, int pobjetivoY, Integer[][] mapa) {
        this.mapa = mapa;
        ancho = mapa[1].length;
        alto = mapa.length;

        mapaNodos = new Nodo[alto][ancho];

        //Posición Inicial
        this.inicioX = inicioX;
        this.inicioY = inicioY;

        //Posicion Objetivo
        int objetivoX = pobjetivoX;
        int objetivoY = pobjetivoY;

        objetivo = new Nodo(objetivoX, objetivoY, 0, null);

        for (int fila = 0; fila < alto; fila++) {
            for (int columna = 0; columna < ancho; columna++) {
                mapaNodos[fila][columna] = new Nodo(fila, columna, mapa[fila][columna], objetivo);
            }
        }

        mapaNodos[objetivoX][objetivoY] = objetivo;

        AStar();

        MuestraRuta();

//        for (int i = 0; i < alto; i++) {
//            for (int j = 0; j < ancho; j++) {
//                System.out.print(" " + mapa[i][j] + " ");
//            }
//            System.out.println("");
//        }
//        for (int i = 0; i < alto; i++) {
//            for (int j = 0; j < ancho; j++) {
//                System.out.print(" " + mapaNodos[i][j] + " ");
//            }
//            System.out.println("");
//        }
    }

    public void AStar() {

        Nodo actual = null;

        abierta.add(mapaNodos[inicioX][inicioY]);
        while (!abierta.isEmpty()) {
            abierta.sort(new Comparator<Nodo>() {
                @Override
                public int compare(Nodo n1, Nodo n2) {
                    return n1.compararNodo(n2);
                }
            });
            actual = abierta.get(0);
            if (actual.getX() == objetivo.getX() && actual.getY() == objetivo.getY()) {
                //System.out.println("Llegamos al objetivo");
                break;
            } else {
                abierta.remove(0);
                cerrada.add(actual);
                
                if (!abierta.contains(mapaNodos[trataFila(actual.getX() - 1)][trataColumna(actual.getY())])
                        && !cerrada.contains(mapaNodos[trataFila(actual.getX()-1)][trataColumna(actual.getY())])
                        && mapaNodos[trataFila(actual.getX() - 1)][trataColumna(actual.getY())].getTipo() != 3) {

                    mapaNodos[trataFila(actual.getX() - 1)][trataColumna(actual.getY())].setAnterior(actual);
                    mapaNodos[trataFila(actual.getX() - 1)][trataColumna(actual.getY())].calculaCoste();
                    abierta.add(mapaNodos[trataFila(actual.getX() - 1)][trataColumna(actual.getY())]);
                }
                
                for (int columna = actual.getY() - 1; columna <= actual.getY() + 1; columna++) {

                    if (!abierta.contains(mapaNodos[trataFila(actual.getX())][trataColumna(columna)])
                            && !cerrada.contains(mapaNodos[trataFila(actual.getX())][trataColumna(columna)])
                            && mapaNodos[trataFila(actual.getX())][trataColumna(columna)].getTipo() != 3) {

                        mapaNodos[trataFila(actual.getX())][trataColumna(columna)].setAnterior(actual);
                        mapaNodos[trataFila(actual.getX())][trataColumna(columna)].calculaCoste();
                        abierta.add(mapaNodos[trataFila(actual.getX())][trataColumna(columna)]);
                    }
                }
                
                if (!abierta.contains(mapaNodos[trataFila(actual.getX() + 1)][trataColumna(actual.getY())])
                        && !cerrada.contains(mapaNodos[trataFila(actual.getX() + 1)][trataColumna(actual.getY())])
                        && mapaNodos[trataFila(actual.getX() + 1)][trataColumna(actual.getY())].getTipo() != 3) {

                    mapaNodos[trataFila(actual.getX() + 1)][trataColumna(actual.getY())].setAnterior(actual);
                    mapaNodos[trataFila(actual.getX() + 1)][trataColumna(actual.getY())].calculaCoste();
                    abierta.add(mapaNodos[trataFila(actual.getX() + 1)][trataColumna(actual.getY())]);
                }

//                for (int fila = actual.getX() - 1; fila <= actual.getX() + 1; fila++) {
//                    for (int columna = actual.getY() - 1; columna <= actual.getY() + 1; columna++) {
//                        if (!abierta.contains(mapaNodos[trataFila(fila)][trataColumna(columna)])
//                                && !cerrada.contains(mapaNodos[trataFila(fila)][trataColumna(columna)])
//                                && mapaNodos[trataFila(fila)][trataColumna(columna)].getTipo() != 3) {
//
//                            mapaNodos[trataFila(fila)][trataColumna(columna)].setAnterior(actual);
//                            mapaNodos[trataFila(fila)][trataColumna(columna)].calculaCoste();
//
//                            abierta.add(mapaNodos[trataFila(fila)][trataColumna(columna)]);
//                        }
//                    }
//                }
            }
        }
    }

    public void MuestraRuta() {
        Nodo trabajo = objetivo;
        while (trabajo.getAnterior() != null) {

            ruta.add(trabajo);
            mapaNodos[trabajo.getX()][trabajo.getY()].setTipo(2);
            trabajo = trabajo.getAnterior();

        }

        Collections.reverse(ruta);
        // System.out.println(ruta);
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    private int trataFila(int fila) {
        if (fila < 0) {
            return 0;
        } else if (fila > alto - 1) {
            return fila - 1;
        }

        return fila;

    }

    private int trataColumna(int columna) {
        if (columna < 0) {
            return 0;
        } else if (columna > ancho - 1) {
            return columna - 1;
        }

        return columna;

    }

    public Nodo[][] getMapaNodos() {
        return mapaNodos;
    }

    public ArrayList<Nodo> getRuta() {
        return ruta;
    }
};
