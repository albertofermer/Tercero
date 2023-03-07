addpath('DatosGenerados')
addpath('../../Funciones')
load('espacio_ccas_cuadrado_triang.mat')
load('nombresProblema.mat')


nombresProblema = [];
nombresProblema.descriptores = nombresProblemaIO.descriptores(espacioCcas);
nombresProblema.clases = nombresProblemaIO.clases;
nombresProblema.simbolos = nombresProblemaIO.simbolos;
XTrain = X;
YTrain = Y;
save('./DatosGenerados/EntrenamientoKNN_circuloTriangulo-Cuadrado','XTrain','YTrain','nombresProblema');