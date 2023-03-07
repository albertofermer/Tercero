
addpath('Datos Generados\');
addpath('../../Funciones/');

clc;clear;

%% Primero cargamos el espacio de características obtenido 
    % en la etapa de selección de descriptores

load('espacio_ccas_circ_trian.mat')

%% Calculamos la función de decisión lineal.
[d1,d2,d12,coef_d12] = funcion_calcula_hiperplanoLDA_2Clases_2_3_Dim(XOI,YOI);

%% La representamos junto a las muestras para valorar la eficiencia del 
% clasificador.
funcion_representa_hiperplano_separacion_2_3_Dim(coef_d12,XOI);



