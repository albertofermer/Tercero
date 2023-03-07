addpath("../../MaterialFacilitado/MaterialFacilitado/Funciones/");
addpath("../../DatosGenerados/");
addpath("../../Estandarizacion/");
load("conjunto_Datos_estandarizados.mat");
load("nombresProblema.mat");

X = Z;

%dim = 4;
%XoI = X; YoI = Y;
%[espacioCcas,JespacioCcas] =  funcion_selecciona_vector_ccas(XoI,YoI,dim);
espacioCcas = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22];
save("Espacio_Caracteristicas_QDA","espacioCcas");

X(Y==1,:)=[];
Y(Y==1) = [];
X(Y==7,:)=[];
Y(Y==7) = [];
X(Y==8,:)=[];
Y(Y==8)=[];


[vectorMedias, matrizCovarianza, probabilidadPriori] = funcion_ajusta_QDA(X,Y);

valoresClases = [2,3,4,5,6];
Y_QDA = funcion_aplica_QDA(X,vectorMedias,matrizCovarianza,probabilidadPriori,valoresClases);


figure,
coeficiente = [a,b,c,d];
representa_muestras_clasificacion_binaria(X,Y_QDA,nombresProblema)
xlabel("Compacticidad")
ylabel("Excentricidad")
zlabel("Solidez")


