addpath('Datos Generados')
addpath('../../Funciones')
load('espacio_ccas.mat')
load('nombresProblema.mat')


nombresProblema = [];
nombresProblema.descriptores = nombresProblemaOI.descriptores(espacioCcas);
nombresProblema.clases = nombresProblemaOI.clases;
nombresProblema.simbolos = nombresProblemaOI.simbolos;
XTrain = XOI;
YTrain = YOI;
save('./Datos Generados/EntrenamientoKNN_circuloTriangulo-Cuadrado','XTrain','YTrain','nombresProblema');