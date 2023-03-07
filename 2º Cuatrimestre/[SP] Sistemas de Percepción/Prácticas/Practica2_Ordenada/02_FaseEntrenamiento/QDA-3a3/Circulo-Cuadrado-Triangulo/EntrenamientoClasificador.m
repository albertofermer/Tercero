addpath('DatosGenerados')
addpath('../../Funciones')
load('espacio_ccas_circulo_cuadrado_triang.mat')
load('nombresProblema.mat')

[vectorMedias,mCovar,Ppriori] = funcion_ajusta_QDA(X,Y)
[YQDA,d] = funcion_calcula_QDA(X,vectorMedias,mCovar,Ppriori,unique(Y));

nombresProblema = [];
nombresProblema.descriptores = nombresProblemaIO.descriptores(espacioCcas);
nombresProblema.clases = nombresProblemaIO.clases;
nombresProblema.simbolos = nombresProblemaIO.simbolos;

save('./DatosGenerados/Entrenamiento_Circulo_Cuadrado_Triangulo','espacioCcas', 'd', 'nombresProblema');