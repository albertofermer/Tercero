
addpath("./Funciones/")
addpath("./Variables_Generadas/")
addpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")

load("Datos Generados/conjunto_datos_estandarizados.mat")
load("Datos Generados/nombresProblema.mat")
XoI = Z;
YoI = Y;

[espacioCcas,JespacioCcas] = funcion_selecciona_vector_ccas(XoI,YoI,3);
funcion_representa_datos(XoI,YoI,espacioCcas,nombresProblema)

[vectorMedias,matrizCovarianzas, probabilidadPriori] = funcion_ajusta_LDA(XoI,YoI);
[YLDA,dLDA] = funcion_aplica_LDA(XoI(5,:),vectorMedias,matrizCovarianzas,probabilidadPriori,unique(Y));

[vectorMedias,matrizCovarianzas, probabilidadPriori] = funcion_ajusta_QDA(XoI,YoI);
[YQDA,dQDA] = funcion_aplica_QDA(XoI(5,:),vectorMedias,matrizCovarianzas,probabilidadPriori,unique(Y));

Y_circtriang_cuadrados = YoI;
Y_circtriang_cuadrados(YoI == 3) = 1;

[d1,d2,d12,coef_d12] = funcion_calcula_hiperplanoLDA_2Clases_2_3_Dim(XoI(:,1:2),Y_circtriang_cuadrados);

funcion_representa_hiperplano_separacion_2_3_Dim(coef_d12,XoI(:,1:2))

rmpath("./Funciones/")
rmpath("./Variables_Generadas/")
rmpath("../1. Generación de Patrones de Reconocimiento/Datos Generados/")