addpath('../../../1. Generación de Patrones de Reconocimiento/Datos Generados/')
addpath('../../Funciones/')
addpath('../../../1. Generación de Patrones de Reconocimiento/Funciones/')

clear;clc;

load('conjunto_datos_estandarizados.mat')
load('nombresProblema.mat')

%% Seleccionamos las clases involucradas (Circulos (1) y Cuadrados (2) y Triángulos (3)).
clasesOI = [1 2 3];
XOI = Z((Y == clasesOI(1) | Y == clasesOI(2) | Y == clasesOI(3)),:);
YOI = Y(Y == clasesOI(1) | Y == clasesOI(2) | Y == clasesOI(3));

%% Seleccionamos los tres mejores descriptores para discriminar entre 
   % las muestras de circulos y cuadrados. 
 
[espacioCcas, JespacioCcas]=funcion_selecciona_vector_ccas(XOI,YOI,5);
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