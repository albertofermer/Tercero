addpath('Datos Generados\');
addpath('../../Funciones/');

clc;clear;

load('espacio_ccas_cuadrado_triang.mat');
load('nombresProblema.mat')


nombresProblema = [];
nombresProblema.descriptores = nombresProblemaOI.descriptores(espacioCcas);
nombresProblema.clases = nombresProblemaOI.clases;
nombresProblema.simbolos = nombresProblemaOI.simbolos;
XTrain = XOI;
YTrain = YOI;
save('./Datos Generados/EntrenamientoKNN','XTrain','YTrain','nombresProblema');