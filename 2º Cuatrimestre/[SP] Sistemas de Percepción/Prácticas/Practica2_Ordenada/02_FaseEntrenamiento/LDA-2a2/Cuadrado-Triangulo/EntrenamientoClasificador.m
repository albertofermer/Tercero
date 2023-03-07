addpath('DatosGenerados')
addpath('../../Funciones')
load('espacio_ccas_cuadrado_triang.mat')
load('nombresProblema.mat')

[d1,d2,d12,coef_d12] = funcion_calcula_hiperplanoLDA_separacion(X,Y)
 funcion_representa_hiperplanoLDA_separacion(coef_d12,X)
nombresProblema = [];
nombresProblema.descriptores = nombresProblemaIO.descriptores(espacioCcas);
nombresProblema.clases = nombresProblemaIO.clases;
nombresProblema.simbolos = nombresProblemaIO.simbolos;

save('./DatosGenerados/Hiperplano_cuadrado_triang','espacioCcas', 'd12', 'coef_d12', 'nombresProblema');