addpath('DatosGenerados')
addpath('../../Funciones')
load('espacio_ccas_circ_cuad.mat')
load('nombresProblema.mat')

[d1,d2,d12,coef_d12] = funcion_calcula_hiperplanoLDA_separacion(X,Y)
 funcion_representa_hiperplanoLDA_separacion(coef_d12,X)
nombresProblema = [];
nombresProblema.descriptores = nombresProblemaIO.descriptores(espacioCcas);
nombresProblema.clases = nombresProblemaIO.clases;
nombresProblema.simbolos = nombresProblemaIO.simbolos;

save('./DatosGenerados/Hiperplano_circ_cuad','espacioCcas', 'd12', 'coef_d12', 'nombresProblema');