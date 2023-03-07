addpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")
addpath("./Funciones/")

load("../1. Generación de Patrones de Reconocimiento/Datos Generados/conjunto_datos_estandarizados.mat")
load("../1. Generación de Patrones de Reconocimiento/Datos Generados/nombresProblema.mat")

% La codificación de los cuadrados es 2, la de los triangulos es 3
XoI = Z;
YoI = Y;
XoI = XoI((YoI==2 | YoI == 3),:)
YoI = YoI(YoI==2 | YoI == 3)

nombresProblema.clases = {'Cuadrado', 'Triangulo'}
[espacioCcas,JespacioCcas] = funcion_selecciona_vector_ccas(XoI,YoI,3);
funcion_representa_datos(XoI,YoI,espacioCcas,nombresProblema)

rmpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")
rmpath("./Funciones/")