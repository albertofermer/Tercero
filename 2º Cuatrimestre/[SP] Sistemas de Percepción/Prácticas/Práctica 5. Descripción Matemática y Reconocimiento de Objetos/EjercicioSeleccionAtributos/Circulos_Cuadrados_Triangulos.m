
addpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")
addpath("./Funciones/")

load("../1. Generación de Patrones de Reconocimiento/Datos Generados/conjunto_datos_estandarizados.mat")
load("../1. Generación de Patrones de Reconocimiento/Datos Generados/nombresProblema.mat")

XoI = Z;
YoI = Y;

[espacioCcas,JespacioCcas] = funcion_selecciona_vector_ccas(XoI,YoI,3);
funcion_representa_datos(XoI,YoI,espacioCcas,nombresProblema)

save("./Variables_Generadas/espacioCcasCCT.mat","espacioCcas")

rmpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")
rmpath("./Funciones/")