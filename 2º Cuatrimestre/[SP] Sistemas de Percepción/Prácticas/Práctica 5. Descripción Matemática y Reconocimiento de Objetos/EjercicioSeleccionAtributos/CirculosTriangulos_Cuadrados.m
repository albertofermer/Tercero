addpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")
addpath("./Funciones/")

load("../1. Generación de Patrones de Reconocimiento/Datos Generados/conjunto_datos_estandarizados.mat")
load("../1. Generación de Patrones de Reconocimiento/Datos Generados/nombresProblema.mat")

% La codificación de los circulos es 1, la de los cuadrados es 2 la de los triangulos es 3
XoI = Z;
YoI = Y;
YoI(YoI==3) = 1;

nombresProblema.clases = {'Circulo-Triangulo', 'Cuadrado'};
[espacioCcas,JespacioCcas] = funcion_selecciona_vector_ccas(XoI,YoI,3);
funcion_representa_datos(XoI,YoI,espacioCcas,nombresProblema)

save("./Variables_Generadas/espacioCcasCircTrian_C.mat","espacioCcas")

rmpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")
rmpath("./Funciones/")