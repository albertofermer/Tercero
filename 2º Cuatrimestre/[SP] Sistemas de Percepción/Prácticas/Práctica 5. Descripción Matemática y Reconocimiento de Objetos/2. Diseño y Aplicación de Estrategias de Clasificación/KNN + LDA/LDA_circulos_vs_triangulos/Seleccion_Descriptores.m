addpath('../../../1. Generación de Patrones de Reconocimiento/Datos Generados/')
addpath('../../Funciones/')
addpath('../../../1. Generación de Patrones de Reconocimiento/Funciones/')

clear;clc;

load('conjunto_datos_estandarizados.mat')
load('nombresProblema.mat')

%% Seleccionamos las clases involucradas
clasesOI = [1 3];
XOI = Z((Y==1 | Y==3),:);
YOI = Y(Y==1 | Y==3);



%% Seleccionamos los cuatro mejores descriptores para discriminar entre 
   % las muestras.
 
[espacioCcas, JespacioCcas]=funcion_selecciona_vector_ccas(XOI,YOI,3);
XOI = XOI(:,espacioCcas);

%% Por último, guardamos el nombre del problema de interés en este caso.
nombresProblemaOI = [];
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = nombresProblema.clases(clasesOI);
nombresProblemaOI.simbolos = nombresProblema.simbolos;

save('Datos Generados/espacio_ccas.mat','espacioCcas','XOI','YOI');
save('Datos Generados/nombresProblema.mat','nombresProblemaOI');

rmpath('../../../1. Generación de Patrones de Reconocimiento/Datos Generados/')
rmpath('../../Funciones/');
rmpath('../../../1. Generación de Patrones de Reconocimiento/Funciones/');