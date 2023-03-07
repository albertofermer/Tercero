
addpath('Datos Generados\');
addpath('../../Funciones/');

clc;clear;

%% Primero cargamos el espacio de características obtenido 
    % en la etapa de selección de descriptores

load('espacio_ccas_cuadrado_triang.mat')
load('nombresProblema.mat');
%% Calculamos la función de decisión cuadrática.
[vectorMedias,mCovar,Ppriori] = funcion_ajusta_QDA(XOI,YOI);

[YQDA,d] = funcion_aplica_QDA(XOI,vectorMedias,mCovar,Ppriori,unique(YOI));

nombresProblema = [];
nombresProblema.descriptores = nombresProblemaOI.descriptores(espacioCcas);
nombresProblema.clases = nombresProblemaOI.clases;
nombresProblema.simbolos = nombresProblemaOI.simbolos;

save('./Datos Generados/descriptores_atributos.mat','nombresProblema');
save('./Datos Generados/funcion_decision.mat','d');

rmpath('Datos Generados\');
rmpath('../../Funciones');


